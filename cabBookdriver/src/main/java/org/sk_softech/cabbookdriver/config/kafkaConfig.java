package org.sk_softech.cabbookdriver.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class kafkaConfig {

    @Bean
    public NewTopic Topic()
    {
        return TopicBuilder.name("cab-location").build();
    };

}
