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
import de.hhn.rz.db.LocationRepository;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.db.entities.Location;
import de.hhn.rz.dto.AccountCreate;
import de.hhn.rz.fop.XslTransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountCredentialService extends AbstractService {

    private final AccountCredentialCreationService accountCredentialCreationService;
    private final XslTransformerService xslTransformerService;
    private final LocationRepository locationRepository;

    public AccountCredentialService(@Autowired AccountCredentialCreationService accountCredentialCreationService,
                                    @Autowired XslTransformerService xslTransformerService,
                                    @Autowired LocationRepository locationRepository) {
        this.accountCredentialCreationService = accountCredentialCreationService;
        this.xslTransformerService = xslTransformerService;
        this.locationRepository = locationRepository;
    }

    public byte[] createCredentials(AccountCreate accountCreate) {
        checkParameter(accountCreate);

        final Optional<Location> location = locationRepository.findById(accountCreate.location());
        final List<AccountCredential> list = new ArrayList<>();

        for (int i = 0; i < accountCreate.amount(); i++) {
            list.add(accountCredentialCreationService.createCredential(location.orElseThrow()));
        }

        return xslTransformerService.process(list);
    }
}
