package de.hhn.rz.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomCredentialService {

    private final char[] allowedChars;
    private final SecureRandom random;
    private final int passwordLength;

    public RandomCredentialService(
            @Value("${hhn.random.credentials.chars}") String allowedChars,
            @Value("${hhn.random.credentials.length}") int passwordLength) {
        this.allowedChars = allowedChars.toCharArray();
        this.random = new SecureRandom();
        this.passwordLength = passwordLength;
    }

    public String getRandomPassword() {
        return RandomStringUtils.random(passwordLength, 0, allowedChars.length - 1, false, false, allowedChars, random);
    }
}
