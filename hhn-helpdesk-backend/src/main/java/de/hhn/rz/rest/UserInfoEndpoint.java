package de.hhn.rz.rest;

import de.hhn.rz.AbstractService;
import de.hhn.rz.db.entities.AuditAction;
import de.hhn.rz.dto.UserInfo;
import de.hhn.rz.services.AuditLogService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/admin/rest/")
public class UserInfoEndpoint extends AbstractService {

    private final AuditLogService service;

    public UserInfoEndpoint(@Autowired AuditLogService service) {
        this.service = service;
    }

    @GetMapping("user")
    public UserInfo user() {
        service.audit(AuditAction.VIEW_CURRENT_USER);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        if (principal instanceof KeycloakPrincipal<?> kp) {
            final String user = kp.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
            final Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            return new UserInfo(user, roles);
        } else {
            return new UserInfo("N/A", Collections.emptySet());
        }
    }

}
