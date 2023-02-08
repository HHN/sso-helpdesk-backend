package de.hhn.rz.crypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Decryptor extends AbstractCrypt {

    public Decryptor(@Value("${hhn.credentials.secret") String secret) {
        super(secret);
    }

    public String decrypt(String toDecrypt, String salt) {
        checkParameter(toDecrypt);
        checkParameter(salt);
        try {
            return decryptPasswordBased(toDecrypt, getKeyFromPassword(secret, salt), generateIv());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
