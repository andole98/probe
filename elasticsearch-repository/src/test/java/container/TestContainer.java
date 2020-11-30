package container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainer {

    @Container
    private static ElasticsearchContainer searchContainer = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.9.2")
            .withEnv("discovery.type", "single-node")
            .withReuse(true);

    static {
        searchContainer.start();
    }

    @DynamicPropertySource
    private static void elasticSearchProperties(DynamicPropertyRegistry registry) {
        registry.add("elasticsearch.address", searchContainer::getHttpHostAddress);
    }
}
