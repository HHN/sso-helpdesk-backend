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
