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
import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.db.AccountRepository;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.exception.SequenceAlreadyUsedException;
import de.hhn.rz.exception.InvalidSearchException;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CredentialService extends AbstractService {

    private final AccountRepository accountRepository;
    private final Decryptor decryptor;

    public CredentialService(@Autowired AccountRepository accountRepository,
                             @Autowired Decryptor decryptor) {
        this.accountRepository = accountRepository;
        this.decryptor = decryptor;
    }

    public CredentialRepresentation getCredentials(String seq) {
        checkParameter(seq);

        final AccountCredential ac = accountRepository.getBySeq(seq);

        if (ac == null) {
            throw new InvalidSearchException("Could not find credentials for given seq='" + seq + "'.");
        } else {
            if(ac.getUsed() != null) {
                throw new SequenceAlreadyUsedException("Sequence was already used: '" + seq +"'.");
            }

            ac.setUsed(LocalDateTime.now());

            final CredentialRepresentation cr = new CredentialRepresentation();
            cr.setTemporary(true);
            cr.setValue(decryptor.decrypt(ac.getPassword(), ac.getSalt(), ac.getIv()));

            accountRepository.save(ac);

            return cr;
        }
    }
}
