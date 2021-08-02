import java.io.InputStream;
import java.util.*;

public class TransactionReader implements Iterator<Transaction>{
    private static final String INPUT_TRANSACTION_FILE = "sample-user-profile";
    private final List<Transaction> transactions;
    private final Iterator<Transaction> transactionIterator;

    public TransactionReader(List<Transaction> transactions, Iterator<Transaction> transactionIterator) {
        this.transactions = transactions;
        this.transactionIterator = transactionIterator;
    }

    private List<Transaction> loadTransactions() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_TRANSACTION_FILE);

        Scanner sc = new Scanner(inputStream);
        List<Transaction> transactions = new ArrayList<>();

        while(sc.hasNextLine()) {
            String[] transaction = sc.nextLine().split("");
            String user = transaction[0];
            String transactionLocation = transaction[1];
            double amount = Double.valueOf(transaction[2]);
            transactions.add(new Transaction(user, amount, transactionLocation));
        }

        return Collections.unmodifiableList(transactions);
    }

    public boolean hasNext() {
        return false;
    }

    public Transaction next() {
        return null;
    }


}
