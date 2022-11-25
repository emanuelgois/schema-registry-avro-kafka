package br.com.egois.schemaregistry.config;

import br.com.egois.schemaregistry.avro.Transacao;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;


@Configuration
public class MessagingConfigTransacao implements MessagingConfigPort<Transacao> {
	
	@Bean(name = "transacaoProducer")
	@Override
	public KafkaProducer<String, Transacao> configureProducer() {

		Properties properties = new Properties();
		
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "http://127.0.0.1:9092");
        properties.put(ACKS_CONFIG, "all" );
        properties.put(RETRIES_CONFIG,"10");
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");
		
		return new KafkaProducer<String, Transacao>(properties);
		
	}

}
