package repository;

import domain.AutoComplete;
import org.springframework.data.repository.CrudRepository;

public interface AutoCompleteCrudRepository extends CrudRepository<AutoComplete,String > {
}
