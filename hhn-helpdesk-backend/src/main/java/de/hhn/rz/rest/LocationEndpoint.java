package de.hhn.rz.rest;


import de.hhn.rz.AbstractService;
import de.hhn.rz.db.LocationRepository;
import de.hhn.rz.db.entities.Location;
import de.hhn.rz.dto.LocationInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public List<LocationInformation> locations() {
        final List<LocationInformation> info = new ArrayList<>();
        for (Location location : service.getLocations()) {
            info.add(new LocationInformation(location.getId(), location.getLabel(), service.getFree(location.getId()), service.getTotal(location.getId())));
        }
        return info;
    }

}
