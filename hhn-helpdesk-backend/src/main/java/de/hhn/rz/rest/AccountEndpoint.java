package de.hhn.rz.rest;


import de.hhn.rz.AbstractService;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.dto.Account;
import de.hhn.rz.dto.AccountCreate;
import de.hhn.rz.dto.AccountReset;
import de.hhn.rz.exception.InvalidSearchException;
import de.hhn.rz.services.AccountCredentialService;
import de.hhn.rz.services.AuditLogService;
import de.hhn.rz.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

import static javax.ws.rs.core.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/admin/rest/")
public class AccountEndpoint extends AbstractService {

    private final KeycloakService service;
    private final AccountCredentialService accountCredentialService;
    private final AuditLogService auditLogService;

    public AccountEndpoint(@Autowired KeycloakService service,
                           @Autowired AccountCredentialService accountCredentialService,
                           @Autowired AuditLogService auditLogService) {
        this.service = service;
        this.accountCredentialService = accountCredentialService;
        this.auditLogService = auditLogService;
    }

    @GetMapping("users")
    public List<Account> users(
            @Nullable @RequestParam("first") Integer first,
            @Nullable @RequestParam("max") Integer max,
            @RequestParam("q") String q) {

        if (first == null) {
            first = 0;
        }
        if (max == null) {
            max = 10;
        }

        if (q == null || q.isBlank()) {
            throw new InvalidSearchException("'q' must not be NULL or empty.");
        }

        auditLogService.audit(AuditAction.SEARCH, "first=" + first, "max=" + max, "q=" + q);
        return service.findAccounts(first, max, q.trim());

    }

    @PostMapping("reset")
    public void reset(@RequestBody AccountReset accountReset) {
        checkParameter(accountReset);
        checkParameter(accountReset.id());
        checkParameter(accountReset.seq());

        auditLogService.audit(AuditAction.RESET_CREDENTIALS, "keycloak-id=" + accountReset.id(), "seq=" + accountReset.seq());
        service.resetCredentials(accountReset.id(), accountReset.seq());
    }

    @PostMapping("create")
    public ResponseEntity<StreamingResponseBody> create(@RequestBody AccountCreate accountCreate) {
        checkParameter(accountCreate);
        checkParameter(accountCreate.location());
        final int amount = (accountCreate.amount() == null || accountCreate.amount() <= 0) ? 10 : accountCreate.amount();
        auditLogService.audit(AuditAction.CREATE, "amount=" + accountCreate.amount(), "location=" + accountCreate.location());
        final byte[] pdf = accountCredentialService.createCredentials(new AccountCreate(accountCreate.location(), amount));

        StreamingResponseBody responseBody = response -> {
            response.write(pdf);
        };

        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"credentials.pdf\"")
                .contentType(MediaType.APPLICATION_PDF).body(responseBody);

    }
}
