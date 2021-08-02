import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserReader {
    private static final String SAMPLE_USER_TRANSACTIONS = "sample-user-transactions";
    private final Map<String, String> userToLocationMap;

    public UserReader(Map<String, String> userToLocationMap) {
        this.userToLocationMap = userToLocationMap;
    }

    public String getUserLocation(String user) {
        if(!userToLocationMap.containsKey(user)) {
            throw new IllegalArgumentException("User Name: " + user + "doesn't exist");
        }

        return userToLocationMap.get(user);
    }

    private Map<String, String> loadUserLocations() {
        Map<String, String> userToLocation = new HashMap<>();

        InputStream ip = getClass().getClassLoader().getResourceAsStream(SAMPLE_USER_TRANSACTIONS);

        Scanner sc = new Scanner(ip);

        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] userLocationPair = line.split("");
            userToLocationMap.put(userLocationPair[0], userLocationPair[1]);
        }

        return Collections.unmodifiableMap(userToLocation);
    }

}
