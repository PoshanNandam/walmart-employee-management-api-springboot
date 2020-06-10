package itc.walmart.pocmysql.service;

import itc.walmart.pocmysql.model.Department;
import itc.walmart.pocmysql.repository.DepartmentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DepartmentRepo departmentRepo;

    @Async
    public CompletableFuture<Department> createDepartment(@NotNull Department department) throws DuplicateKeyException {
        Long start = System.currentTimeMillis();
        if(departmentRepo.findByName(department.getName()) == null) {
            Long stop = System.currentTimeMillis();
            logger.info("Saved the data using thread: "+ Thread.currentThread().getName());
            logger.info("Time taken to: " + (stop-start));
            return CompletableFuture.completedFuture(departmentRepo.save(department));
        } else{
            logger.error("Throwing duplicateKey Exception in Department create method");
            throw new DuplicateKeyException("Department already exits with name : "+ department.getName());
        }
    }

    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepo.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepo.findById(id);
    }

    public Department updateDepartment(Department department) {
        Department departmentFromDb = getDepartmentById(department.getId()).get();
        departmentFromDb.setName(department.getName());
        return departmentRepo.save(departmentFromDb);
    }

    public void deleteDepartmentById(Long id) {
        departmentRepo.deleteById(id);
    }
}
