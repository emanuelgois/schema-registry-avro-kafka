package br.com.egois.schemaregistry.config;

import br.com.egois.schemaregistry.dto.TransacaoDTO;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;


public interface MessagingPort<T extends SpecificRecordBase> {
	
	ProducerRecord<String, T> createProducerRecord(T type);
	
	void send(TransacaoDTO transacaoDTO);
}
