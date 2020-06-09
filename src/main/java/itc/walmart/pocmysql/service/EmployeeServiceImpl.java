package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.repository.DepartmentRepo;
import itc.walmart.pocmysql.repository.EmployeeRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public Employee createEmployee(@NotNull Employee employee) throws DuplicateKeyException, NoSuchElementException {
        if(employeeRepo.findByFirstName(employee.getFirstName()) == null) {
            Optional<Department> department = departmentRepo.findById(employee.getDepartment().getId());
            if(department.get() == null){
                throw new NoSuchElementException("Department not found. First need to create department: " + employee.getDepartment());
            }
            employee.setDepartment(department.get());
            Employee newEmployee = employeeRepo.save(employee);
            return newEmployee;
        }
        else{
            throw new DuplicateKeyException("Employee already exits with first name : "+ employee.getFirstName());
        }
    }

    @Override
    public List<Employee> getAllEmployees(){
        List<Employee> employeeDemoList = (List<Employee>) employeeRepo.findAll();
        return employeeDemoList;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);
        return employee.get();
    }

    @Override
    public Employee updateEmployee(@NotNull Employee employee) {
        Employee employeeFromDb = getEmployeeById(employee.getId());
        employeeFromDb.setFirstName(employee.getFirstName());
        employeeFromDb.setLastName(employee.getLastName());
        employeeFromDb.setAge(employee.getAge());
        employeeFromDb.setSalary(employee.getSalary());
        employeeFromDb.setJoinDate(employee.getJoinDate());
        Optional<Department> departmentFromDb = departmentRepo.findById(employee.getDepartment().getId());
        employeeFromDb.setDepartment(departmentFromDb.get());
        Employee updatedEmployee = employeeRepo.save(employee);
        return updatedEmployee;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepo.deleteById(employeeId);
    }
}
