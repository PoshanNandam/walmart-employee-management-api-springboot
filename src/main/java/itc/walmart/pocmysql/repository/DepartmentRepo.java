package itc.walmart.pocmysql.repository;

import itc.walmart.pocmysql.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepo extends CrudRepository<Department,Long> {
}
