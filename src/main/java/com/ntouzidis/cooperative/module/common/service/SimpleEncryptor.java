package com.ntouzidis.cooperative.module.common.service;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class SimpleEncryptor {

    private StandardPBEStringEncryptor encryptor;

    public SimpleEncryptor() {
        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setPassword("kobines");
    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String text) {
        return encryptor.decrypt(text);
    }
}
