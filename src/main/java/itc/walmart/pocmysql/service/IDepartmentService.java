package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    public Department createDepartment(Department department);
    public List<Department> getAllDepartments();
    public Optional<Department> getDepartmentById(Long id);
    public Department updateDepartment(Department department);
    public void deleteDepartmentById(Long id);
}
