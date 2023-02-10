package de.hhn.rz.fop;

import de.hhn.rz.crypt.Encryptor;
import de.hhn.rz.db.entities.AccountCredential;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class XslTransformerTest {

    @Autowired
    private XslTransformerService sut;

    @Autowired
    private Encryptor encryptor;

    @Test
    public void testPdfGeneration() {

        final byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        final String salt = "test";
        final String pw = "testtest";
        final String seq = "testtesttest";


        final AccountCredential ac = new AccountCredential();
        ac.setPassword(encryptor.encrypt(pw, salt, iv));
        ac.setIv(iv);
        ac.setSalt(salt);
        ac.setSeq(seq);

        final byte[] result = sut.process(List.of(ac));

        assertNotNull(result);
        assertTrue(result.length > 0);

    }

    @Test
    public void testPdfGenerationEmpty() {
        assertThrows(IllegalArgumentException.class, () -> sut.process(List.of()));
    }

    @Test
    public void testPdfGenerationNull() {
        assertThrows(IllegalArgumentException.class, () -> sut.process(null));
    }
}
