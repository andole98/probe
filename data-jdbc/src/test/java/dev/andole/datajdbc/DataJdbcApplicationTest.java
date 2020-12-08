package dev.andole.datajdbc;

import dev.andole.datajdbc.repository.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DataJdbcApplicationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        RepositoryRepository bean = context.getBean(RepositoryRepository.class);
    }
}