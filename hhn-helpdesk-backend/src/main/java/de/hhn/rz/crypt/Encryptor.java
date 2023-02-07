package de.hhn.rz.crypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Encryptor extends AbstractCrypt {

    public Encryptor(@Value("${hhn.credentials.secret") String secret) {
        super(secret);
    }

    public String encrypt(String toCrypt, String salt) {
        checkParameter(salt);
        checkParameter(toCrypt);
        try {
            return encryptPasswordBased(toCrypt, getKeyFromPassword(secret, salt), generateIv());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
