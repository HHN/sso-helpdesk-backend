package de.hhn.rz.crypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.IvParameterSpec;

@Service
public class Decryptor extends AbstractCrypt {

    public Decryptor(@Value("${hhn.credentials.secret") String secret) {
        super(secret);
    }
    public String decrypt(String toDecrypt, String salt, byte[] iv) {
        checkParameter(toDecrypt);
        checkParameter(salt);
        checkParameter(iv);
        try {
            return decryptPasswordBased(toDecrypt, getKeyFromPassword(secret, salt), new IvParameterSpec(iv));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
