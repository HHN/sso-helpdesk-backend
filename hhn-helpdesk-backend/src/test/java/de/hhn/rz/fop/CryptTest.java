package de.hhn.rz.fop;

import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.crypt.Encryptor;

import java.io.IOException;
import java.security.SecureRandom;

public class CryptTest {


    public static void main(String[] args) throws IOException {

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        String salt =  "test";

        Encryptor encryptor = new Encryptor("abc");
        Decryptor decryptor = new Decryptor("abc");

        String c = encryptor.encrypt("test", salt, iv);

        System.out.println(c);

        System.out.println(decryptor.decrypt(c, salt, iv));
    }
}
