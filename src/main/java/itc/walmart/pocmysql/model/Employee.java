package itc.walmart.pocmysql.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate joinDate;
    @NotNull(message = "Department can not be null or empty")
    @ManyToOne
    Department department;

    public Employee() {
    }
}
