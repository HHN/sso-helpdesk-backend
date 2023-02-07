package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import de.hhn.rz.crypt.Encryptor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomCredentialService extends AbstractService {

    private final char[] allowedChars;
    private final SecureRandom random;
    private final int passwordLength;
    private final Encryptor encryptor;

    public RandomCredentialService(
            @Autowired Encryptor encryptor,
            @Value("${hhn.random.credentials.chars}") String allowedChars,
            @Value("${hhn.random.credentials.length}") int passwordLength) {
        this.allowedChars = allowedChars.toCharArray();
        this.random = new SecureRandom();
        this.passwordLength = passwordLength;
        this.encryptor = encryptor;
    }

    public String getPassword() {
        return encryptor.encrypt(RandomStringUtils.random(passwordLength, 0, allowedChars.length - 1, false, false, allowedChars, random));
    }
}
