package model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Laptop {
    @Id
    @Getter @Setter private int serialNo;
    @Getter @Setter private String model;

    @ManyToOne
    @Getter @Setter private Student student;
}
