package de.hhn.rz.services;

import de.hhn.rz.dto.Account;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class KeycloakService {

    private static final Pattern PATTERN_EMPLOYEE_ID = Pattern.compile("^[0-9]*$");
    private final RealmResource client;

    public KeycloakService(@Autowired RealmResource client) {
        this.client = client;
    }

    public List<Account> findAccounts(Integer first, Integer max, String searchParameter) {
        if (isEmployeeId(searchParameter)) {
            return client.users().searchByAttributes("employeeID:" + searchParameter).stream().map(Account::new).toList();
        }
        return client.users().search(searchParameter, first, max).stream().map(Account::new).toList();
    }

    private boolean isEmployeeId(String id) {
        if (id == null) {
            return false;
        }
        return PATTERN_EMPLOYEE_ID.matcher(id).find();
    }
}
