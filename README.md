Validação de mensagens utilizando Schema Registry e Apache Avro
------

**Pré-requisitos**
- Java 8
- Maven
- Docker
- Docker-compose

## Build
```bash
mvn clean package
```
## Generate Avro Schema
```bash
mvn generate-sources
```

**Execução dos container kafka**
```bash
docker-compose up
```

**Referências**
https://docs.confluent.io/platform/current/schema-registry/index.html#sr-overview
https://avro.apache.org/docs/1.11.1/
