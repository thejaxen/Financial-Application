package com.thejaxen.thejaxendemobank.service;
import com.thejaxen.thejaxendemobank.DTO.BankResponse;
import com.thejaxen.thejaxendemobank.DTO.EnquiryRequest;
import com.thejaxen.thejaxendemobank.DTO.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);
}
