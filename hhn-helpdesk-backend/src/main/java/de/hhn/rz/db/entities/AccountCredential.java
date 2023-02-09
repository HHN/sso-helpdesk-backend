package de.hhn.rz.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AccountCredential {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 255, unique = true, nullable = false)
    private String seq;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime used;

    @Column(nullable = false)
    private String salt = UUID.randomUUID().toString();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUsed() {
        return used;
    }

    public void setUsed(LocalDateTime used) {
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public String getSalt() {
        return salt;
    }
}
