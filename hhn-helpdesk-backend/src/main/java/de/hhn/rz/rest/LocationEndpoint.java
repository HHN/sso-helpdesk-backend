package de.hhn.rz.rest;


import de.hhn.rz.AbstractService;
import de.hhn.rz.db.LocationRepository;
import de.hhn.rz.db.entities.Location;
import de.hhn.rz.dto.Account;
import de.hhn.rz.dto.AccountReset;
import de.hhn.rz.exception.InvalidSearchException;
import de.hhn.rz.services.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@RequestMapping("/admin/rest/")
public class LocationEndpoint extends AbstractService {

    private final LocationRepository service;

    public LocationEndpoint(@Autowired LocationRepository service) {
        this.service = service;
    }

    @GetMapping("locations")
    public List<Location> locations() {
        return service.getLocations();

    }

}
