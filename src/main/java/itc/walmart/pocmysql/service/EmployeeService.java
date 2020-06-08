package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee createEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(Long employeeId);
    public Employee updateEmployee(Employee employee);
    public void deleteEmployee(Long employeeId);
}
