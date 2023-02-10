package de.hhn.rz.crypt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptTest {

    @Autowired
    private Decryptor decryptor;

    @Autowired
    private Encryptor encryptor;

    @Test
    public void testCrypt() {

        final byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        final String salt = "test";

        final String toCrypt = "test-crypt";

        final String c = encryptor.encrypt(toCrypt, salt, iv);

        assertNotNull(c);

        assertEquals(toCrypt, decryptor.decrypt(c, salt, iv), "Decrypted value does not match actual value.");

    }

    @Test
    public void testDecryptorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> decryptor.decrypt(null, "salt", new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> decryptor.decrypt("pw", null, new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> decryptor.decrypt("", "salt", new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> decryptor.decrypt("pw", "", new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> decryptor.decrypt("pw", "salt", null));
    }

    @Test
    public void testEncryptorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> encryptor.encrypt(null, "salt", new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> encryptor.encrypt("pw", null, new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> encryptor.encrypt("", "salt", new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> encryptor.encrypt("pw", "", new byte[16]));
        assertThrows(IllegalArgumentException.class, () -> encryptor.encrypt("pw", "salt", null));
    }
}
