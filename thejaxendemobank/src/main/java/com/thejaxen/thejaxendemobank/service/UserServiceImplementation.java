package com.thejaxen.thejaxendemobank.service;

import com.thejaxen.thejaxendemobank.DTO.AccountInfo;
import com.thejaxen.thejaxendemobank.DTO.BankResponse;
import com.thejaxen.thejaxendemobank.DTO.EmailDetails;
import com.thejaxen.thejaxendemobank.DTO.UserRequest;
import com.thejaxen.thejaxendemobank.entity.User;
import com.thejaxen.thejaxendemobank.repository.UserRepository;
import com.thejaxen.thejaxendemobank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

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
}
