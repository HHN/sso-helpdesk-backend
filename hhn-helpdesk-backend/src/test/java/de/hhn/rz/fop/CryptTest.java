package de.hhn.rz.fop;

import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.crypt.Encryptor;

import java.io.IOException;

public class CryptTest {


    public static void main(String[] args) throws IOException {


        Encryptor encryptor = new Encryptor("abc");
        Decryptor decryptor = new Decryptor("abc");

        String c = encryptor.encrypt("test", "salt");

        System.out.println(c);

        System.out.println(decryptor.decrypt(c, "salt"));
    }
}
