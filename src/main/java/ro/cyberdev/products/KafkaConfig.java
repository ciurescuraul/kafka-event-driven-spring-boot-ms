package ro.cyberdev.products;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic createTopic() {
        return TopicBuilder.name("product-created-events-topic")
                .partitions(3)
                .replicas(3)
                // with this config, the producer will wait for 2 brokers to acknowledge the message
                // the right into the topic will be a little bit slower, but the data will be more safe
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}
