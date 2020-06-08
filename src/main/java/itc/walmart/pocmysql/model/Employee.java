package itc.walmart.pocmysql.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String firstName;
    String lastName;
    int age;
    double salary;
    Date joinDate;

    @ManyToOne(cascade = CascadeType.ALL)
    Department department;

    public Employee() {
    }
}
