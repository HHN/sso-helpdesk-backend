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
package de.hhn.rz.crypt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {Decryptor.class, Encryptor.class})
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
