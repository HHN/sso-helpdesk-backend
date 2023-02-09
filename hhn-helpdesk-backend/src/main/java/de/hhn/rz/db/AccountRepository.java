package de.hhn.rz.db;

import de.hhn.rz.db.entities.AccountCredential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountCredential, Long> {

    @Query("SELECT a FROM AccountCredential a WHERE a.seq = (?1)")
    AccountCredential getBySeq(String seq);

    @Query("SELECT EXISTS (SELECT a FROM AccountCredential  a WHERE a.seq = (?1))")
    boolean existsBySeq(String seq);

}
