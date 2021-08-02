import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Application {
    private static final String VALID_TRANSACTIONS_TOPIC = "valid-transactions";
    private static final String SUSPICIOUS_TRANSACTIONS_TOPIC = "suspicious-transactions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        String consumerGroup = "notification-service";

        System.out.println("Consumer is part of consumer group " + consumerGroup);

        Consumer<String, Transaction> kafkaConsumer = createKafkaConsumer(BOOTSTRAP_SERVERS, consumerGroup);

        consumeMessages(Collections.unmodifiableList(Arrays.asList(SUSPICIOUS_TRANSACTIONS_TOPIC, VALID_TRANSACTIONS_TOPIC)), kafkaConsumer);
    }


    public static void consumeMessages(List<String> topics, Consumer<String, Transaction> kafkaConsumer) {
        kafkaConsumer.subscribe(topics);

        while(true) {
            ConsumerRecords<String, Transaction> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

            if(consumerRecords.isEmpty()) {
                continue;
            }

            for(ConsumerRecord<String, Transaction> consumerRecord : consumerRecords) {
                System.out.println(String.format("Received record (key: %s, value:%s, partition: %d, offset: %d from  topic: %s" +
                        consumerRecord.key(), consumerRecord.value(), consumerRecord.partition(), consumerRecord.offset(), consumerRecord.topic()));
                reportTransaction(consumerRecord.topic(), consumerRecord.value());
            }

            kafkaConsumer.commitAsync();
        }
    }


    public static Consumer<String, Transaction> createKafkaConsumer (String bootstrapServers, String consumerGroup) {
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Transaction.TransactionSerializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new KafkaConsumer<String, Transaction>(properties);
    }


    private static void reportTransaction(String topic, Transaction transaction) {
        if(topic.equals(SUSPICIOUS_TRANSACTIONS_TOPIC)) {
            System.out.println(String.format("Sending User notification about suspicious transaction for user %s, amount of $%.2f from %s"
                    , transaction.getUser(), transaction.getAmount(), transaction.getTransactionLocation()));
        } else if(topic.equals(VALID_TRANSACTIONS_TOPIC)) {
            System.out.println(String.format("Sending user-notification for valid transaction for user %s, amount of $%.2f from %s"
                    , transaction.getUser(), transaction.getAmount(), transaction.getTransactionLocation()));
        }
    }
}
