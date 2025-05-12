package com.thejaxen.thejaxendemobank.service;

import com.thejaxen.thejaxendemobank.DTO.EmailDetails;

public interface EmailService {
    void sendEmail(EmailDetails emailDetails);
}
