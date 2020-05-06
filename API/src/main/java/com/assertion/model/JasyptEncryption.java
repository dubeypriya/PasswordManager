package com.assertion.model;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;
public class JasyptEncryption {
	AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
	
	public JasyptEncryption() {
	textEncryptor.setPassword("PriyaPassword");
	}
	public String Encrypt(String plainText) {
		return textEncryptor.encrypt(plainText);
	}
	public String Decrypt(String incriptedText) {
		return textEncryptor.decrypt(incriptedText);
	}
}
