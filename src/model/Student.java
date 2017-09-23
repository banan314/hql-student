package model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Student {
    @Id
    @Getter @Setter private int rollNo;
    @Getter @Setter private String name;
    @Getter @Setter private int marks;

    @OneToMany(mappedBy = "student") @Getter @Setter private List<Laptop> laptops;

    @Override
    public String toString() {
        return "Student {"+"rollNo="+rollNo+", name='"+name+'\''+", marks="+marks+'}';
    }
}
