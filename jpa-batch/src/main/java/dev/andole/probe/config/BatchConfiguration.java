package dev.andole.probe.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
        // omitted for MapJobRepository
    }
}
