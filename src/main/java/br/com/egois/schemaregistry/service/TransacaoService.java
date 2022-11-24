package br.com.egois.schemaregistry.service;


import br.com.egois.schemaregistry.avro.Transacao;
import br.com.egois.schemaregistry.config.MessagingPort;
import br.com.egois.schemaregistry.dto.TransacaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransacaoService implements MessagingPort<Transacao> {

	@Autowired
	@Qualifier("transacaoProducer")
	private KafkaProducer<String, Transacao> producer;

	@Override
	public String topic() {
		return "transacao-avro";
	}
		
	@Override
	public ProducerRecord<String, Transacao> createProducerRecord(Transacao transacao) {

		return new ProducerRecord<String, Transacao>(this.topic(), transacao);
		
	}

	@Override
	public void send(TransacaoDTO transacaoDTO) {

		Transacao transacao = Transacao.newBuilder()
				.setKey(transacaoDTO.getKey())
				.setNome(transacaoDTO.getNome())
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
		producer.close();

	}

}
