package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.repository.DepartmentRepo;
import itc.walmart.pocmysql.repository.EmployeeRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @Override
    public Employee createEmployee(@NotNull Employee employee){
        Department department = departmentRepo.save(employee.getDepartment());
        employee.setDepartment(department);
        Employee newEmployee = employeeRepo.save(employee);
        return newEmployee;
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
        Employee employeeFromDb = employeeServiceImpl.getEmployeeById(employee.getId());
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
