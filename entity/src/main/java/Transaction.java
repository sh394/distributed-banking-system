import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class Transaction {

    private String user;
    private double amount;
    private String transactionLocation;

    public Transaction() {

    }

    public Transaction(String user, double amount, String transactionLocation) {
        this.user = user;
        this.amount = amount;
        this.transactionLocation = transactionLocation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionLocation() {
        return transactionLocation;
    }

    public void setTransactionLocation(String transactionLocation) {
        this.transactionLocation = transactionLocation;
    }


    public static class TransactionSerializer implements Serializer<Transaction> {

        public byte[] serialize(String s, Transaction transaction) {
            byte[] serializedData = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                serializedData = objectMapper.writeValueAsString(s).getBytes();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return serializedData;
        }
    }

    public static class TransactionDeserializer implements Deserializer<Transaction> {


        public Transaction deserialize(String s, byte[] bytes) {
            ObjectMapper mapper = new ObjectMapper();
            Transaction transaction = null;
            try {
                transaction = mapper.readValue(bytes, Transaction.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transaction;
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "user='" + user + '\'' +
                ", amount=" + amount +
                ", transactionLocation='" + transactionLocation + '\'' +
                '}';
    }
}
