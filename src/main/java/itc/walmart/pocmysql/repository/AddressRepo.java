package itc.walmart.pocmysql.repository;

import itc.walmart.pocmysql.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepo extends CrudRepository<Address,Long> {
}
