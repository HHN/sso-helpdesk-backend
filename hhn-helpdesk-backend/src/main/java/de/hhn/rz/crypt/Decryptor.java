package de.hhn.rz.crypt;

import de.hhn.rz.AbstractService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
