package dev.andole.datajdbc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryRepository extends CrudRepository<dev.andole.datajdbc.domain.Repository, Long> {
}
