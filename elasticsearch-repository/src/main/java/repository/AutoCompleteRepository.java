package repository;

import domain.AutoComplete;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AutoCompleteRepository extends ElasticsearchRepository<AutoComplete, String > {
    boolean existsAutoCompleteByName(String name);
}
