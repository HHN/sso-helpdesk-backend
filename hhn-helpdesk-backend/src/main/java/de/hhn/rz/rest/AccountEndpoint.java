package de.hhn.rz.rest;


import de.hhn.rz.dto.Account;
import de.hhn.rz.exception.InvalidSearchException;
import de.hhn.rz.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/rest/")
public class AccountEndpoint {

    private final KeycloakService service;

    public AccountEndpoint(@Autowired KeycloakService service) {
        this.service = service;
    }

    @GetMapping("users")
    public List<Account> users(
            @RequestParam("first") Integer first,
            @RequestParam("max") Integer max,
            @RequestParam("q") String q) {

        if(first == null) {
            first = 0;
        }
        if(max == null) {
            max = 10;
        }

        if(q == null || q.isBlank()) {
            throw new InvalidSearchException("'q' must not be NULL or empty.");
        }

        return service.findAccounts(first, max, q.trim());

    }
}
