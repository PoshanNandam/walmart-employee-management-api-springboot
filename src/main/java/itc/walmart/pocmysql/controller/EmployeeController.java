package itc.walmart.pocmysql.controller;

import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("employee")
    public Employee createEmployee(@RequestBody Employee employee){
        Employee createdEmployee = employeeServiceImpl.createEmployee(employee);
        return createdEmployee;
    }

    @GetMapping("employee")
    public List<Employee> getAllEmployees(){
        return employeeServiceImpl.getAllEmployees();
    }

    @GetMapping("employee/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId){
        Employee employee;
        try{
            employee = employeeServiceImpl.getEmployeeById(employeeId);
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("employee")
    public Employee updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeServiceImpl.updateEmployee(employee);
        return updatedEmployee;
    }

    @DeleteMapping("employee/{employeeId}")
    public ResponseEntity deleteEmployeeById(@PathVariable Long employeeId){
        try{
            employeeServiceImpl.deleteEmployee(employeeId);
            ResponseEntity.ok(200);
        } catch (IllegalArgumentException e){

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
