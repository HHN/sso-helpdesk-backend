package de.hhn.rz.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Location {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 255, nullable = false, unique = true)
    private String label;
    public Long getId() {
        return id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
}
