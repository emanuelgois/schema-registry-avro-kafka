package br.com.egois.schemaregistry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class Consumer {

    @KafkaListener(groupId = "grupo-transacao-avro",  topics = {"transacao-avro"})
    void consumerTransacao(final String  message) {
        log.info("Mensagem recebida:");
        //log.info("Topic: {}", message.getSchema());
        log.info("payload: {}", message);
        log.info("");
        log.info("-----------------------------------------------------");
        log.info("");

    }

    /*@KafkaListener(groupId = "grupo-transacao-avro",  topics = {"${app.topic}"})
    public void consumerTramsacao(SpecificRecordBase transacao){
        log.info("Mensagem recebida: {} ", transacao);
     *//*   log.info("Key {}", transacao.getKey());
        log.info("Nome {}", transacao.getNome());*//*
        log.info("-----------------------------------------------------");
    }*/

}
