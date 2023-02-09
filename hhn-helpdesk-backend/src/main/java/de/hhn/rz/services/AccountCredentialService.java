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

        final Location location = locationRepository.getByLabel(accountCreate.location());

        if (location == null) {
            throw new IllegalArgumentException("Given location does NOT exist: " + location);
        }

        final List<AccountCredential> list = new ArrayList<>();

        for (int i = 0; i < accountCreate.amount(); i++) {
            list.add(accountCredentialCreationService.createCredential(location));
        }

        return xslTransformerService.process(list);
    }
}
