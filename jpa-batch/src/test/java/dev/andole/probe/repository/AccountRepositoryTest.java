package dev.andole.probe.repository;

import com.github.javafaker.Faker;
import dev.andole.probe.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AccountRepositoryTest {
    @Autowired
    private AccountRepository repository;

    private Faker faker = new Faker(new Locale("ko"));

    @Test
    @Commit
    void name() {
        List<Account> accounts = IntStream.range(0, 100)
            .mapToObj(i -> new Account(faker.name().name()))
            .collect(Collectors.toList());

        new Random().ints(0, 100)
            .limit(100)
            .mapToObj(accounts::get)
            .forEach(account -> accounts.get(0).withdraw(account, BigDecimal.valueOf(10_000)));

        repository.saveAll(accounts);
    }
}