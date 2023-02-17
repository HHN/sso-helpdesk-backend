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
