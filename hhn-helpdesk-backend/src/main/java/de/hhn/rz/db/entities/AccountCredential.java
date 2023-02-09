package de.hhn.rz.db.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

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
    private String salt;

    @Column(nullable = false)
    private byte[] iv;

    @ManyToOne(targetEntity = Location.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private Location location;

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

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
