package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;

@Service
public class CredentialService extends AbstractService {

    public CredentialRepresentation getCredentials(String seq) {
        checkParameter(seq);

        final CredentialRepresentation cr = new CredentialRepresentation();
        cr.setTemporary(true);
        //TODO getFromDB
        cr.setValue("bananenbrot1337!!Test");

        return cr;
    }
}
