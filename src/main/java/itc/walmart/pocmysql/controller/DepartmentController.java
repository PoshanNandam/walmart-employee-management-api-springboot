package itc.walmart.pocmysql.controller;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.service.DepartmentServiceImpl;
import itc.walmart.pocmysql.service.IDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1")
@Validated
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDepartmentService departmentService;

    @PostMapping("department")
    public CompletableFuture<ResponseEntity> createDepartment(@RequestBody @Valid Department department){
        CompletableFuture<ResponseEntity> responseEntityCompletableFuture = departmentService.createDepartment(department)
                .<ResponseEntity>thenApply(department1 -> ResponseEntity.status(HttpStatus.CREATED).body(department1))
                .<ResponseEntity>exceptionally((e) -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
        return responseEntityCompletableFuture;
    }

    @GetMapping("department")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("department/{departmentId}")
    public ResponseEntity getDepartment(@PathVariable @Min(1) Long departmentId){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentService.getDepartmentById(departmentId).get());
        } catch (EmptyResultDataAccessException e){
            logger.error("EmptyResultDataAccessException at deleteEmployee of ID: "+ departmentId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e){
            logger.error("NoSuchElementException at deleteEmployee of ID: "+ departmentId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("department")
    public ResponseEntity updateDepartment(@RequestBody @Valid Department department){
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentService.updateDepartment(department));
        } catch (NullPointerException e){
            logger.error("NullPointerException at patchDepartment of ID: "+ department);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department not present to patch");
        } catch (EmptyResultDataAccessException e){
            logger.error("EmptyResultDataAccessException at patchEmployee of ID: "+ department);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e){
            logger.error("NoSuchElementException at patchEmployee of ID: "+ department);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("department/{departmentId}")
    public ResponseEntity deleteDepartment(@PathVariable @Min(1) Long departmentId){
        try{
            departmentService.deleteDepartmentById(departmentId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e){
            logger.error("IllegalArgumentException at deleteEmployee of ID: "+ departmentId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EmptyResultDataAccessException e){
            logger.error("EmptyResultDataAccessException at deleteEmployee of ID: "+ departmentId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            logger.error("DataIntegrityViolationException at deleteEmployee of ID: "+ departmentId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("departments")
    public  CompletableFuture<ResponseEntity> createMultipleDepartments(@RequestBody @Valid List<Department> departments){


        List<CompletableFuture> futures = new ArrayList();
        for (Department department:departments) {
            futures.add(departmentService.createDepartment(department));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRunAsync(() -> logger.info("Returning the response"))
                .<ResponseEntity>thenApply(ResponseEntity::ok)
                .<ResponseEntity>exceptionally((e) -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
    }
}
