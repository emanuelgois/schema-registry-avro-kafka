package br.com.egois.schemaregistry.config;

import br.com.egois.schemaregistry.avro.Transacao;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;


@Configuration
public class MessagingConfigTransacao implements MessagingConfigPort<Transacao> {
	
	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean(name = "transacaoProducer")
	@Override
	public KafkaProducer<String, Transacao> configureProducer() {

		Properties properties = new Properties();
		
        properties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(ACKS_CONFIG, kafkaProperties.getAcksConfig());
        properties.put(RETRIES_CONFIG, kafkaProperties.getRetriesConfig());
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer());
        properties.put(SCHEMA_REGISTRY_URL_CONFIG, kafkaProperties.getSchemaRegistryUrl());
		
		return new KafkaProducer<String, Transacao>(properties);
		
	}

	@Value("${kafka.bootstrapServers}")
	private String bootstrapServers;

/*	@Bean
	public ConsumerFactory<String, Transacao> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-transacao-avro");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Transacao.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Transacao> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, Transacao> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setMessageConverter(new JsonMessageConverter());
		return factory;
	}*/



	ConsumerFactory<String, Transacao> userConsumerFactory() {
		final Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-transacao-avro");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Transacao.class));
	}

	Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-transacao-avro");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		//props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
		return props;
	}

	ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Transacao> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Transacao> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaJsonListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setMessageConverter(new StringJsonMessageConverter());
		return factory;
	}

}
