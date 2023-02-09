package de.hhn.rz.services;

import de.hhn.rz.AbstractService;
import de.hhn.rz.crypt.Decryptor;
import de.hhn.rz.db.AccountRepository;
import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.exception.InvalidSearchException;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CredentialService extends AbstractService {

    private final AuditLogService auditLogService;
    private final AccountRepository accountRepository;
    private final Decryptor decryptor;

    public CredentialService(@Autowired AuditLogService auditLogService,
                             @Autowired AccountRepository accountRepository,
                             @Autowired Decryptor decryptor) {
        this.auditLogService = auditLogService;
        this.accountRepository = accountRepository;
        this.decryptor = decryptor;
    }

    public CredentialRepresentation getCredentials(String seq) {
        checkParameter(seq);

        final AccountCredential ac = accountRepository.getBySeq(seq);

        if (ac == null) {
            throw new InvalidSearchException("Could not find credentials for given seq='" + seq + "'.");
        } else {
            ac.setUsed(LocalDateTime.now());

            final CredentialRepresentation cr = new CredentialRepresentation();
            cr.setTemporary(true);
            cr.setValue(decryptor.decrypt(ac.getPassword(), ac.getSalt(), ac.getIv()));

            accountRepository.save(ac);

            return cr;
        }
    }
}
