package de.hhn.rz.dto;

import de.hhn.rz.db.entities.Location;

public record LocationInformation(Location location, int free, int total) {
}
