package de.hhn.rz.crypt;

import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.crypt.Encryptor;

import java.security.SecureRandom;

public class CryptTest {


    public static void main(String[] args) {

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        String salt =  "test";

        Encryptor encryptor = new Encryptor("abc");
        Decryptor decryptor = new Decryptor("abc");

        String c = encryptor.encrypt("password-test", salt, iv);

        System.out.println(c);

        System.out.println(decryptor.decrypt(c, salt, iv));
    }
}
