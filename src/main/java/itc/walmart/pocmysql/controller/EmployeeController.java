package itc.walmart.pocmysql.controller;

import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.service.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1")
@Validated
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("employee")
    public ResponseEntity createEmployee(@RequestBody @Valid Employee employee){
        logger.info("Received in the employee controller create method: "+ employee+ " at: "+ LocalDateTime.now());
        try {
            return ResponseEntity.ok(employeeServiceImpl.createEmployee(employee));
        } catch (DuplicateKeyException e){
            logger.error("Exception while creating the employee: "+ employee.getFirstName() + " at:  " + LocalDateTime.now() + " " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error("Exception while creating the employee: " + employee + " at: " + LocalDateTime.now() + " " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("employee")
    public List<Employee> getAllEmployees(){
        return employeeServiceImpl.getAllEmployees();
    }

    @GetMapping("employee/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable @Min(1) Long employeeId){
        Employee employee;
        try{
            employee = employeeServiceImpl.getEmployeeById(employeeId);
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("employee")
    public Employee updateEmployee(@RequestBody @Valid Employee employee){
        Employee updatedEmployee = employeeServiceImpl.updateEmployee(employee);
        return updatedEmployee;
    }

    @DeleteMapping("employee/{employeeId}")
    public ResponseEntity deleteEmployeeById(@PathVariable @Min(1) Long employeeId){
        try{
            employeeServiceImpl.deleteEmployee(employeeId);
            ResponseEntity.ok(200);
        } catch (IllegalArgumentException e){

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
