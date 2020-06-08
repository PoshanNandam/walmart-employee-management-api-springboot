package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepo.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepo.findAll();
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepo.findById(id);
    }

    @Override
    public Department updateDepartment(Department department) {
        Department departmentFromDb = getDepartmentById(department.getId()).get();
        departmentFromDb.setName(department.getName());
        return departmentRepo.save(departmentFromDb);
    }

    @Override
    public void deleteDepartmentById(Long id) {
        departmentRepo.deleteById(id);
    }
}
