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
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("user/{id}")
    public Optional<Account> get(@PathVariable("id") String keycloakId) {
        checkParameter(keycloakId);
        return service.getAccountDetails(keycloakId);
    }

    @PostMapping("reset")
    public void reset(@RequestBody AccountReset accountReset) {
        checkParameter(accountReset);
        checkParameter(accountReset.id());
        checkParameter(accountReset.seq());

        try {
            auditLogService.audit(AuditAction.RESET_CREDENTIALS_TRY, "keycloak-id=" + accountReset.id(), "seq=" + accountReset.seq());
            service.resetCredentials(accountReset.id(), accountReset.seq(), accountReset.resetMfa());
        } catch (Exception e) {
            auditLogService.audit(AuditAction.RESET_CREDENTIALS_FAILED, "keycloak-id=" + accountReset.id(), "seq=" + accountReset.seq(), "ex=" + e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping("create")
    public @ResponseBody byte[] create(@RequestBody AccountCreate accountCreate) {
        checkParameter(accountCreate);
        checkParameter(accountCreate.location());
        try {
            final int amount = (accountCreate.amount() == null || accountCreate.amount() <= 0 || accountCreate.amount() >= 50) ? 50 : accountCreate.amount();
            auditLogService.audit(AuditAction.CREATE, "amount=" + accountCreate.amount(), "location=" + accountCreate.location());
            return accountCredentialService.createCredentials(new AccountCreate(accountCreate.location(), amount));
        }catch (Exception e) {
            auditLogService.audit(AuditAction.CREATE_FAILED, "amount=" + accountCreate.amount(), "location=" + accountCreate.location(), "ex=" + e.getLocalizedMessage());
            throw e;
        }
    }
}
