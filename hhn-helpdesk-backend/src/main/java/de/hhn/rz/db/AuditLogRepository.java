package de.hhn.rz.db;

import de.hhn.rz.db.entities.AuditLogEntry;
import de.hhn.rz.db.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLogEntry, Long> {

    //XXX Might be pageable one day, if we encounter perf issues
    @Query("SELECT l FROM AuditLogEntry l")
    List<AuditLogEntry> getAudits();
}
