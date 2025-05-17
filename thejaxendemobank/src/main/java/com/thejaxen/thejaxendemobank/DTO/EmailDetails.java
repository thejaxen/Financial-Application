package com.thejaxen.thejaxendemobank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmailDetails {

    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public String getAttachment() {
        return attachment;
    }
}
