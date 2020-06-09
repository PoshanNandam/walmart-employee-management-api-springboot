package itc.walmart.pocmysql.repository;

import itc.walmart.pocmysql.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee,Long> {
    Employee findByFirstName(String firstName);
}
