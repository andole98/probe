package repository;

import com.github.javafaker.Faker;
import config.ElasticConfiguration;
import container.TestContainer;
import domain.AutoComplete;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = ElasticConfiguration.class)
class AutoCompleteRepositoryTest extends TestContainer {

    @Autowired
    private AutoCompleteRepository repository;

    @Autowired
    private AutoCompleteCrudRepository crudRepository;

    private Faker faker = new Faker(new Locale("ko"));


    @Test
    @DisplayName("save")
    void save() {
        List<AutoComplete> autoCompletes = generateAutocomplete();

        // 25467
        stopWatch(() -> autoCompletes.forEach(repository::save));
    }

    @Test
    @DisplayName("saveAll")
    void saveAll() {
        List<AutoComplete> autoCompletes = generateAutocomplete();

        // 455
        stopWatch(() -> repository.saveAll(autoCompletes));
    }

    @Test
    @DisplayName("crud - save")
    void crudSave() {
        List<AutoComplete> autoCompletes = generateAutocomplete();

        // 25467
        stopWatch(() -> autoCompletes.forEach(crudRepository::save));
    }

    @Test
    @DisplayName("crud - saveAll")
    void crudSaveAll() {
        List<AutoComplete> autoCompletes = generateAutocomplete();

        // 455
        stopWatch(() -> crudRepository.saveAll(autoCompletes));
    }

    private void stopWatch(Runnable task) {
        long before = System.currentTimeMillis();

        task.run();

        long after = System.currentTimeMillis();

        System.err.println(after - before);
    }

    private List<AutoComplete> generateAutocomplete() {
        return IntStream.range(0, 1000)
            .mapToObj(i -> new AutoComplete(faker.name().name(), faker.address().city()))
            .collect(Collectors.toList());
    }
}