package br.com.egois.schemaregistry.service;

import br.com.egois.schemaregistry.avro.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Consumer  {
    @KafkaListener(topics = {"transacao-avro"}, groupId = "grupo-transacao-avro")
    public void consume(ConsumerRecord<String, Transacao> record, Acknowledgment acknowledgment) {
        log.info("----------------Consumer-------------------------");
        log.info("Mensagem Recebida, key {} ", record.key());
        log.info("payload {}", record.value());
        log.info("-----------------------------------------");
        acknowledgment.acknowledge();
    }
}
