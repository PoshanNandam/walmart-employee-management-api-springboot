package itc.walmart.pocmysql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    Long id;
    @NotBlank(message = "First name can not be null or empty")
    String firstName;
    String lastName;
    @NotNull(message = "Age can not be null or empty")
    Integer age;
    @NotNull(message = "Salary can not be null or empty")
    Double salary;
    Date joinDate;
    @NotNull(message = "Department can not be null or empty")
    @ManyToOne
    Department department;

    public Employee() {
    }
}
