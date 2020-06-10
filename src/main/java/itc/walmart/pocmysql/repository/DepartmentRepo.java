package itc.walmart.pocmysql.repository;

import itc.walmart.pocmysql.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends CrudRepository<Department,Long> {
    Department findByName(String name);
}
