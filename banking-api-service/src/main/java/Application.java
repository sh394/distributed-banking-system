import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Application {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";


    private static final String SUSPICIOUS_TRANSACTION_TOPIC = "suspicious-transactions";
    private static final String VALID_TRANSACTIONS_TOPIC = "valid-transactions";





    public static void processTransactions(TransactionReader transactionReader, UserReader userReader,
                                           Producer<String, Transaction> kafkaProducer) throws ExecutionException, InterruptedException {

        while(transactionReader.hasNext()) {

        }
    }


    public static Producer<String, Transaction> createKafkaProducer(String bootstrapServers) {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "banking-api-service");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Transaction.TransactionSerializer.class.getName());


        return new KafkaProducer<String, Transaction>(properties);
    }

}
