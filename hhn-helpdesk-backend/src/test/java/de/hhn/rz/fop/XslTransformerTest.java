/*
 * Copyright © 2023 Hochschule Heilbronn (ticket@hs-heilbronn.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.hhn.rz.fop;

import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.crypt.Encryptor;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.fop.qr.QRGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Encryptor.class, Decryptor.class, XslTransformerService.class, QRGenerator.class, FopFactoryProducer.class})
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
