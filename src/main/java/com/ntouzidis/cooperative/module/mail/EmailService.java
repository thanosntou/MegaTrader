package com.ntouzidis.cooperative.module.mail;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
