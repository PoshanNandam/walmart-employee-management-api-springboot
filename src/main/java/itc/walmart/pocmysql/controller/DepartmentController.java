package itc.walmart.pocmysql.controller;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1")
@Validated
public class DepartmentController {

    @Autowired
    DepartmentServiceImpl departmentService;

    @PostMapping("department")
    public ResponseEntity<Department> createDepartment(@RequestBody @Valid Department department){
        try {
            return ResponseEntity.status(202).body(departmentService.createDepartment(department));
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("department")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("department/{departmentId}")
    public Department getDepartment(@PathVariable @Min(1) Long departmentId){
        return departmentService.getDepartmentById(departmentId).get();
    }

    @PatchMapping("department")
    public Department updateDepartment(@RequestBody @Valid Department department){
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("department/{departmentId}")
    public ResponseEntity deleteDepartment(@PathVariable @Min(1) Long departmentId){
        departmentService.deleteDepartmentById(departmentId);
        return ResponseEntity.ok().build();
    }
}
