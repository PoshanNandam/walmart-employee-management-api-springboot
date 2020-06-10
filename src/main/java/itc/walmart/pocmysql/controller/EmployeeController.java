package itc.walmart.pocmysql.controller;

import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.service.IEmployeeService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1")
@Validated
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IEmployeeService employeeServiceImpl;

    @PostMapping("employee")
    public CompletableFuture<ResponseEntity> createEmployee(@RequestBody @Valid Employee employee){
        logger.info("Received the POST request in createEmployee method: "+ employee+ " at: "+ LocalDateTime.now());
        CompletableFuture<ResponseEntity> responseEntityCompletableFuture = employeeServiceImpl.createEmployee(employee)
                .<ResponseEntity>thenApply(employee1 -> ResponseEntity.status(HttpStatus.CREATED).body(employee1))
                .<ResponseEntity>exceptionally((e) -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
        return responseEntityCompletableFuture;
    }

    @GetMapping("employee")
    public List<Employee> getAllEmployees(){
        logger.info("Received the GET request in getAllEmployee method at: "+ LocalDateTime.now());
        return employeeServiceImpl.getAllEmployees();
    }

    @GetMapping("employee/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable @Min(1) Long employeeId){
        logger.info("Received the GET request in getEmployee method with employeeID as: "+ employeeId + " at: "+ LocalDateTime.now());
        Employee employee;
        try{
            employee = employeeServiceImpl.getEmployeeById(employeeId);
        } catch (NoSuchElementException e){
            logger.error("NoSuchElementException caught in getEmployeeByID of employeeID: "+ employeeId + " at: "+ LocalDateTime.now()+ " with message: "+ e.getMessage());
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

    @PostMapping("employees")
    public CompletableFuture<ResponseEntity> createMultipleEmployee(@RequestBody @Valid List<Employee> employees){
        logger.info("Received the POST request in createMultipleEmployee method with size: "+ employees.size()+ " at: "+ LocalDateTime.now());
        List<CompletableFuture> futures = new ArrayList();
        for (Employee employee:employees) {
            futures.add(employeeServiceImpl.createEmployee(employee));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRunAsync(() -> logger.info("Returning the response"))
                .<ResponseEntity>thenApply(ResponseEntity::ok)
                .<ResponseEntity>exceptionally((e) -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
    }
}
