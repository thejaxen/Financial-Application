package com.thejaxen.thejaxendemobank.service;

import com.thejaxen.thejaxendemobank.DTO.*;
import com.thejaxen.thejaxendemobank.entity.User;
import com.thejaxen.thejaxendemobank.repository.UserRepository;
import com.thejaxen.thejaxendemobank.utils.AccountUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service

public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        //Checking if user have already account by email and phone number.
        if (userRepository.existsByEmail(userRequest.getEmail()) || userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            //Creating an account of new user into db
            User newUser = User.builder()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .otherName(userRequest.getOtherName())
                    .gender(userRequest.getGender())
                    .address(userRequest.getAddress())
                    .stateOfOrigin(userRequest.getStateOfOrigin())
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                    .status("ACTIVE")
                    .build();

                User savedUser = userRepository.save(newUser);

                EmailDetails emailDetails = EmailDetails.builder()
                        .recipient(savedUser.getEmail())
                        .subject("Account created")
                        .messageBody("Congratulations! Your account has been created! \n Your account details: \n" +
                        "Account Name: " + savedUser.getFirstName() + " " + savedUser.getOtherName() + " " +savedUser.getLastName()+"\nAccount Number: " + savedUser.getAccountNumber())
                        .build();
                emailService.sendEmailAlert(emailDetails);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(savedUser.getAccountBalance())
                            .accountNumber(savedUser.getAccountNumber())
                            .accountName(savedUser.getFirstName()+" "+savedUser.getOtherName()+" "+savedUser.getLastName())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {

        //check if provided account number exists in the db
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getOtherName() + " " + foundUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist) {
            return AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();

    }

    @Transactional
    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {

        try{
            boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
            if(!isAccountExist){
                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                        .accountInfo(null)
                        .build();
            }



            User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
            userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));

            userRepository.save(userToCredit);

            //SaveTransaction

            TransactionDto transactionDto = TransactionDto.builder()
                    .transactionType("CREDIT")
                    .amount(request.getAmount())
                    .accountNumber(userToCredit.getAccountNumber())
                    .build();
            transactionService.saveTransaction(transactionDto);


            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToCredit.getFirstName() + " " + userToCredit.getOtherName() + " " + userToCredit.getLastName())
                            .accountBalance(userToCredit.getAccountBalance())
                            .accountNumber(request.getAccountNumber())
                            .build())
                    .build();
        }
        catch(Exception e){
            return BankResponse.builder()
                    .responseCode(AccountUtils.CREDIT_ERROR_CODE)
                    .responseMessage(AccountUtils.CREDIT_ERROR_MESSAGE)
                    .accountInfo(null)
            .build();
        }
    }

    @Transactional
    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {

        try
        {
            boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());

            if(!isAccountExists){
                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                        .accountInfo(null)
                        .build();
            }


            User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());

            BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
            BigInteger debitAmount = request.getAmount().toBigInteger();

            if(availableBalance.intValue() < debitAmount.intValue()){
                return BankResponse.builder()
                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                        .accountInfo(null)
                        .build();
            }else{
                userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
                userRepository.save(userToDebit);

                TransactionDto transactiondto = TransactionDto.builder()
                        .transactionType("DEBIT")
                        .amount(request.getAmount())
                        .accountNumber(userToDebit.getAccountNumber())
                        .build();

                transactionService.saveTransaction(transactiondto);


                return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                        .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                        .accountInfo(AccountInfo.builder()
                                .accountName(userToDebit.getFirstName() + " " + userToDebit.getOtherName() + " " + userToDebit.getLastName())
                                .accountBalance(userToDebit.getAccountBalance())
                                .accountNumber(request.getAccountNumber())
                                .build())
                        .build();
            }

        }
        catch(Exception e){

            return BankResponse.builder()
                    .responseCode(AccountUtils.DEBIT_ERROR_CODE)
                    .responseMessage(AccountUtils.DEBIT_ERROR_MESSAGE)
                    .accountInfo(null)
                    .build();

        }

    }


    @Transactional
    @Override
    public BankResponse transfer(TransferRequest request) {

        try {
            //get the account to debit (check if it exists)
            //check if the amount ı am debiting is not more than the current balance
            //debit the account
            //get the account to credit
            //credit the account

            boolean isSourceAccountExist = userRepository.existsByAccountNumber(request.getSourceAccountNumber());
            boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());

            if (!isDestinationAccountExist) {
                return BankResponse.builder()
                        .responseCode(AccountUtils.DESTINATION_ACCOUNT_NOT_EXISTS_CODE)
                        .responseMessage(AccountUtils.DESTINATION_ACCOUNT_NOT_EXISTS_MESSAGE)
                        .accountInfo(null)
                        .build();
            }

            User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());

            if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
                return BankResponse.builder()
                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                        .accountInfo(null)
                        .build();
            }

            sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
            String sourceUserName = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getOtherName() + " " + sourceAccountUser.getLastName();
            userRepository.save(sourceAccountUser);

            EmailDetails debitAlert = EmailDetails.builder()
                    .subject("Debit alert to: ")
                    .recipient(sourceAccountUser.getEmail())
                    .messageBody("The amount of " + request.getAmount() + " money has been send to  " + request.getDestinationAccountNumber() + " your current balance is " + sourceAccountUser.getAccountBalance())
                    .build();

            emailService.sendEmailAlert(debitAlert);

            TransactionDto debittransaction = TransactionDto.builder()
                    .amount(request.getAmount())
                    .accountNumber(request.getSourceAccountNumber())
                    .transactionType("DEBIT")
                    .build();

            transactionService.saveTransaction(debittransaction);

            User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
            String destinationUserName = destinationAccountUser.getFirstName() + " " + destinationAccountUser.getOtherName() + " " + destinationAccountUser.getLastName();
            destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
            userRepository.save(destinationAccountUser);

            EmailDetails creditAlert = EmailDetails.builder()
                    .subject("Credit alert from: " + sourceUserName)
                    .recipient(destinationAccountUser.getEmail())
                    .messageBody("The amount of " + request.getAmount() + " money has been came from " + sourceUserName + " your current balance is " + destinationAccountUser.getAccountBalance())
                    .build();
            emailService.sendEmailAlert(creditAlert);

            TransactionDto credittransaction = TransactionDto.builder()
                    .transactionType("CREDIT")
                    .amount(request.getAmount())
                    .accountNumber(destinationAccountUser.getAccountNumber())
                    .build();

            transactionService.saveTransaction(credittransaction);

            return BankResponse.builder()
                    .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                    .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                    .accountInfo(null)
                    .build();
        } catch (Exception e) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.TRANSFER_ERROR_CODE)
                    .responseMessage(AccountUtils.TRANSFER_ERROR_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
    }


}
