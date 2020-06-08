package itc.walmart.pocmysql.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "department")
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;

    public Department() {
    }
}
