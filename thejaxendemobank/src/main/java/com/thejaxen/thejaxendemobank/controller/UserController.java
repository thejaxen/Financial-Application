package com.thejaxen.thejaxendemobank.controller;


import com.thejaxen.thejaxendemobank.DTO.*;
import com.thejaxen.thejaxendemobank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User account management APIs")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(
            summary="Creating a new user account",
            description="Helps to create the new account with checking if the email or phone number already used to creating a new account."
    )

    @ApiResponse(
            responseCode = "201",
            description="HTTP status 201 created"
    )

    @PostMapping("/createAccount")
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @Operation(
            summary="Enquiring the balance",
            description="Takes the account number as parameter and checks how much balance that account number has."
    )

    @ApiResponse(
            responseCode = "200",
            description="HTTP status 200 success"
    )

    @GetMapping("/balanceEnquiry")
    public BankResponse BalanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }

    @GetMapping("/nameEnquiry")
    public String NameEnquiry(@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }
}
