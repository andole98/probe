package dev.andole.probe.domain;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AccountTest {
    @Autowired
    private TestEntityManager tm;

    private Faker faker = new Faker(new Locale("ko"));

    @Test
    @DisplayName("계좌이체시 두 계좌에서 Exchange")
    void withdraw() {
        Account from = new Account(faker.name().name());
        Account to = new Account(faker.name().name());

        Long fromId = tm.persist(from).getId();
        Long toId = tm.persist(to).getId();

        from.withdraw(to, BigDecimal.valueOf(10_000));

        tm.flush();
        tm.clear();

        assertThat(tm.find(Account.class, fromId).getWithdraws())
            .isNotEmpty();

        assertThat(tm.find(Account.class, toId).getDeposits())
            .isNotEmpty();
    }
}