package com.company.wordapp.controller;

import com.company.wordapp.config.KafkaCamelRouteBuilder;
import com.company.wordapp.model.Message;
import lombok.AllArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.PostConstruct;

@Controller
@ResponseBody
@RequestMapping("/word")
@AllArgsConstructor
public class WordController {

    private static final Logger LOG = LoggerFactory.getLogger(WordController.class);
    
    private static final String DIRECT_KAFKA_ROUTE = "direct:kafkaRoute";
    private static final String MESSAGE_RECEIVED = "Message received";
    private static final String ERROR_WHILE_SENDING_MESSAGE_TO_KAFKA = "Error while sending message to Kafka";
    public static final String SPACE = " ";

    private final CamelContext camelContext;

    private final KafkaCamelRouteBuilder kafkaCamelRouteBuilder;
    
    @EndpointInject(uri = DIRECT_KAFKA_ROUTE)
    ProducerTemplate kafkaProducer;


    @PostConstruct
    public void setup() {
        try {
            camelContext.addRoutes(kafkaCamelRouteBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> post(@RequestBody Message message) {
        try {
            String text = message.getBody();
            if (StringUtils.contains(text, SPACE)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The text should be a single word");
            }
            kafkaProducer.sendBody(DIRECT_KAFKA_ROUTE, message.getBody());
        } catch (Exception exception) {
            LOG.error(ERROR_WHILE_SENDING_MESSAGE_TO_KAFKA + ": " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_WHILE_SENDING_MESSAGE_TO_KAFKA);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(MESSAGE_RECEIVED);
    }
}
