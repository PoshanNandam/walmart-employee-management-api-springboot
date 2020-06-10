package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.model.Employee;
import itc.walmart.pocmysql.repository.DepartmentRepo;
import itc.walmart.pocmysql.repository.EmployeeRepo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    @Async
    public CompletableFuture<Employee> createEmployee(@NotNull Employee employee) throws DuplicateKeyException, NoSuchElementException {
        if(employeeRepo.findByFirstName(employee.getFirstName()) == null) {
            Long start = System.currentTimeMillis();
            Optional<Department> department = departmentRepo.findById(employee.getDepartment().getId());
            if(department.get() == null){
                logger.error("Throwing NoSuchElementException in Employee create method");
                throw new NoSuchElementException("Department not found. First need to create department: " + employee.getDepartment());
            }
            employee.setJoinDate(LocalDate.now());
            employee.setDepartment(department.get());
            Employee newEmployee = employeeRepo.save(employee);
            Long stop = System.currentTimeMillis();
            logger.info("Saved the data using thread: "+ Thread.currentThread().getName());
            logger.info("Time taken to: " + (stop-start));
            return CompletableFuture.completedFuture(newEmployee);
        }
        else{
            logger.error("Throwing duplicateKey Exception in Employee create method");
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
