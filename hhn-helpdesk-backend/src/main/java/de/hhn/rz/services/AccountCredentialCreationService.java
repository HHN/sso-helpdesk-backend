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
package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import de.hhn.rz.crypt.Encryptor;
import de.hhn.rz.db.AccountRepository;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.db.entities.Location;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountCredentialCreationService extends AbstractService {

    private final AccountRepository repository;
    private final char[] allowedChars;
    private final SecureRandom random;
    private final int passwordLength;
    private final Encryptor encryptor;

    public AccountCredentialCreationService(
            @Autowired AccountRepository repository,
            @Autowired Encryptor encryptor,
            @Value("${hhn.random.credentials.chars}") String allowedChars,
            @Value("${hhn.random.credentials.length}") int passwordLength) {
        this.allowedChars = allowedChars.toCharArray();
        this.random = new SecureRandom();
        this.passwordLength = passwordLength;
        this.encryptor = encryptor;
        this.repository = repository;
    }

    /**
     * Creates new {@link AccountCredential credentials} and saves it into the database
     *
     * @param location must not be {@code null}.
     * @return a persistent {@link AccountCredential}
     */
    public AccountCredential createCredential(Location location) {
        checkParameter(location);
        final AccountCredential ac = new AccountCredential();

        ac.setSalt(getRandomString());
        ac.setIv(getIv());
        ac.setPassword(encryptPassword(getRandomString(), ac.getSalt(), ac.getIv()));
        ac.setSeq(createSeq());
        ac.setLocation(location);

        return repository.save(ac);
    }

    private byte[] getIv() {
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }

    private String getRandomString() {
        return RandomStringUtils.random(passwordLength-5, 0, allowedChars.length - 1,
                false, false, allowedChars, random) + "aB!1#";
    }

    private String encryptPassword(String password, String salt, byte[] iv) {
        return encryptor.encrypt(password, salt, iv);
    }

    private String createSeq() {
        String seq = null;

        // 4.3980465e+12 possible identifiers (64*64*64*64*64*64*64) -> possibility for endless loop is ultra tiny.
        while (seq == null) {
            final byte[] randomBytes = new byte[256];
            ThreadLocalRandom.current().nextBytes(randomBytes);
            final byte[] base64 = Base64.getEncoder().encode(Arrays.toString(randomBytes).getBytes(StandardCharsets.UTF_8));
            final String candidate = new String(base64).substring(0, 7);

            if (!existsBySequence(candidate)) {
                seq = candidate;
            }
        }

        return seq;

    }

    private boolean existsBySequence(String seq) {
        return repository.existsBySeq(seq);
    }

}
