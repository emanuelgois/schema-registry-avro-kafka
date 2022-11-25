package br.com.egois.schemaregistry.service;


import br.com.egois.schemaregistry.avro.Transacao;
import br.com.egois.schemaregistry.config.MessagingPort;
import br.com.egois.schemaregistry.dto.TransacaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class ProducerService implements MessagingPort<Transacao> {


	@Autowired
	@Qualifier("transacaoProducer")
	private KafkaProducer<String, Transacao> producer;

	@Value("${app.topic}")
	private String topic;

	public ProducerService(KafkaProducer<String, Transacao> transacaoKafkaTemplate) {
		this.producer = transacaoKafkaTemplate;
	}

	public ProducerRecord<String, Transacao> createProducerRecord(Transacao transacao) {
		String key = String.valueOf(((int)(Math.random()*(91)))+System.currentTimeMillis());
		return new ProducerRecord<String, Transacao>(this.topic, key , transacao);
	}

	public void send(TransacaoDTO transacaoDTO) {

		Transacao transacao = Transacao.newBuilder()
				.setCodigo(transacaoDTO.getKey())
				.setNome(transacaoDTO.getNome())
				.setApelido("")
				.setDocumento(transacaoDTO.getDocumento())
				.build();

		producer.send(this.createProducerRecord(transacao), (rm, ex) -> {
			if (ex == null) {
				log.info("Transação enviada com sucesso!!!");
			} else {
				log.error("Falha ao enviar transação", ex);
			}
		});

		producer.flush();
		//producer.close();

	}

}
