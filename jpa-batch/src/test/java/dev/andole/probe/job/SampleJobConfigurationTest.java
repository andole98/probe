package dev.andole.probe.job;

import com.github.javafaker.Faker;
import dev.andole.probe.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Locale;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBatchTest
@SpringBootTest(properties = "spring.job.name=SampleJob")
class SampleJobConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApplicationContext context;

    private Faker faker = new Faker(new Locale("ko"));

//    @Test
//    void setUp() {
//        List<Account> accounts = IntStream.range(0, 100)
//            .mapToObj(i -> new Account(faker.name().name()))
//            .collect(Collectors.toList());
//
//        new Random().ints(0, 100)
//            .limit(100)
//            .mapToObj(accounts::get)
//            .forEach(account -> accounts.get(0).withdraw(account, BigDecimal.valueOf(10_000)));
//
//        accountRepository.saveAll(accounts);
//    }

    @Test
    void name() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    }
}