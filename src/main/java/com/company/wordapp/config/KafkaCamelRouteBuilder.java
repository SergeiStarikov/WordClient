package com.company.wordapp.config;

import com.company.wordapp.service.SentenceService;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class KafkaCamelRouteBuilder extends RouteBuilder {

    private static final String DIRECT_KAFKA_ROUTE = "direct:kafkaRoute";
    private static final String KAFKA_WORD_TOPIC = "kafka:word?brokers=localhost:9092";
    private static final String KAFKA_SENTENCE_TOPIC = "kafka:sentence?brokers=localhost:9092";
    
    private final SentenceService sentenceService;
    
    @Override
    public void configure() {
        
        from(DIRECT_KAFKA_ROUTE)
            .to(KAFKA_WORD_TOPIC);

        from(KAFKA_SENTENCE_TOPIC)
            .process(exchange -> sentenceService.save(exchange));
    }
}