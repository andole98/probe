package dev.andole.probe.job;

import com.github.javafaker.Faker;
import dev.andole.probe.domain.Account;
import dev.andole.probe.domain.Exchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

@Slf4j

@Configuration
@ConditionalOnProperty(value = "spring.job.name", havingValue = "SampleJob")
public class SampleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager jpaTransactionManager;

    public SampleJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory,
                                  EntityManagerFactory entityManagerFactory,
                                  @Qualifier("jpaTransactionManager") PlatformTransactionManager jpaTransactionManager) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.jpaTransactionManager = jpaTransactionManager;
    }

    @Bean
    public Job sampleJob(Step sampleStep) {
        return jobBuilderFactory.get("SampleJob")
            .start(sampleStep)
            .build();
    }

    @Bean
    @JobScope
    public Step sampleStep(JdbcPagingItemReader<Account> accountJdbcPagingItemReader,
                           FunctionItemProcessor<Account, Account> accountAccountFunctionItemProcessor,
                           JdbcBatchItemWriter<Account> jdbcBatchItemWriter) {
        Faker faker = new Faker(new Locale("ko"));
        return stepBuilderFactory.get("SampleStep")
            .transactionManager(jpaTransactionManager)
            .<Account, Account>chunk(10)
            .reader(accountJdbcPagingItemReader)
            .processor(accountAccountFunctionItemProcessor)
            .writer(jdbcBatchItemWriter)
            .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Account> accountJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Account>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT a " +
                         "FROM Account a " +
                         "ORDER BY id")
            .pageSize(10)
            .saveState(false)
            .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Account> accountJdbcPagingItemReader(DataSource dataSource) {
        return new JdbcPagingItemReaderBuilder<Account>()
            .saveState(false)
            .dataSource(dataSource)
            .selectClause("SELECT account.id, account.balance, exchange.id, exchange.from_id, exchange.to_id, exchange.money")
            .fromClause("FROM account INNER JOIN exchange ON account.id = exchange.from_id OR account.id = exchange.to_id")
            .sortKeys(Map.of("account.id", Order.ASCENDING))
            .beanRowMapper(Account.class)
            .build();
    }

    @Bean
    @StepScope
    public FunctionItemProcessor<Account, Account> accountBigDecimalFunctionItemProcessor() {
        return new FunctionItemProcessor<>(account -> {
            long withdraws = account.getWithdraws().stream()
                .map(Exchange::getMoney)
                .mapToLong(BigDecimal::longValue)
                .sum();

            long deposits = account.getDeposits().stream()
                .map(Exchange::getMoney)
                .mapToLong(BigDecimal::longValue)
                .sum();
            account.setBalance(BigDecimal.valueOf(deposits - withdraws));
            return account;
        });
    }

    @Bean
    @StepScope
    public JpaItemWriter<Account> jpaItemWriter() {
        return new JpaItemWriterBuilder<Account>()
            .entityManagerFactory(entityManagerFactory)
            .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Account> jdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Account>()
            .dataSource(dataSource)
            .assertUpdates(false)
            .sql("UPDATE account SET balance = :balance WHERE id = :id")
            .beanMapped()
            .build();
    }
}
