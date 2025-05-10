package com.thejaxen.thejaxendemobank.controller;


import com.thejaxen.thejaxendemobank.DTO.BankResponse;
import com.thejaxen.thejaxendemobank.DTO.UserRequest;
import com.thejaxen.thejaxendemobank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createAccount")
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }
}
