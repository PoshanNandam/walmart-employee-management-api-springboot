package itc.walmart.pocmysql.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "department")
@Entity
public class Department {

    @Id
    @GeneratedValue
    Long id;
    @NotBlank(message = "Name can not be null or empty")
    String name;

    public Department() {
    }
}
