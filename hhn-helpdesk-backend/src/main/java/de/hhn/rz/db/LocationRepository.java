package de.hhn.rz.db;

import de.hhn.rz.db.entities.AccountCredential;
import de.hhn.rz.db.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query("SELECT l FROM Location l")
    List<Location> getLocations();

    @Query("SELECT a FROM Location a WHERE a.label = (?1)")
    Location getByLabel(String label);

}