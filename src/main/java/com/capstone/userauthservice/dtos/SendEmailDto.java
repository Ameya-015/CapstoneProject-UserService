package com.capstone.userauthservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    private String toEmail;
    private String subject;
    private String body;
}
