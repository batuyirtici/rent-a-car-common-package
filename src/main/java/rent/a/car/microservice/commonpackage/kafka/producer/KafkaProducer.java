package rent.a.car.microservice.commonpackage.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import rent.a.car.microservice.commonpackage.events.Event;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer{
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T extends Event> void sendMessage(T event, String topic) {
        log.info(String.format("%s event => %s", topic, event.toString()));
        Message<T> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(message);
    }
}
