package de.hhn.rz.crypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.IvParameterSpec;

@Service
public class Encryptor extends AbstractCrypt {

    public Encryptor(@Value("${hhn.credentials.secret") String secret) {
        super(secret);
    }
    public String encrypt(String toCrypt, String salt, byte[] iv) {
        checkParameter(salt);
        checkParameter(toCrypt);
        try {
            return encryptPasswordBased(toCrypt, getKeyFromPassword(secret, salt), new IvParameterSpec(iv));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
