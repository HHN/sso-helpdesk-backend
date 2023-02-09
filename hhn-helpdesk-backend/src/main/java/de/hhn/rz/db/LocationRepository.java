package de.hhn.rz.db;

import de.hhn.rz.db.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query("SELECT l FROM Location l")
    List<Location> getLocations();

    @Query("SELECT COUNT(c) FROM Location l, AccountCredential c WHERE c.location = l AND c.used is NULL AND l.id = (?1)")
    int getFree(Long id);

    @Query("SELECT COUNT(c) FROM Location l, AccountCredential c WHERE c.location = l AND l.id = (?1)")
    int getTotal(Long id);

}
