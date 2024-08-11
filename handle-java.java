import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

// Example 1: Handling HTTP GET and POST requests
public class HandleJava {

    // Method to send an HTTP GET request
    public static String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    // Method to send an HTTP POST request
    public static String sendPostRequest(String urlString, String payload) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = payload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    // Example 2: Reading and writing configuration properties
    public static Properties loadProperties(String fileName) throws Exception {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                HandleJava.class.getClassLoader().getResourceAsStream(fileName)))) {
            properties.load(reader);
        }
        return properties;
    }

    public static void saveProperties(Properties properties, String fileName) throws Exception {
        try (OutputStream output = HandleJava.class.getClassLoader().getResourceAsStream(fileName)) {
            if (output != null) {
                properties.store(output, null);
            } else {
                throw new Exception("File not found: " + fileName);
            }
        }
    }

    // Example 3: Utility function to convert string to title case
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split(" ");
        StringBuilder titleCase = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return titleCase.toString().trim();
    }

    // Example 4: Simple logging utility
    public static void log(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void main(String[] args) {
        try {
            // Example usage of HTTP GET request
            String getResponse = sendGetRequest("https://jsonplaceholder.typicode.com/posts/1");
            log("GET Response: " + getResponse);

            // Example usage of HTTP POST request
            String postPayload = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
            String postResponse = sendPostRequest("https://jsonplaceholder.typicode.com/posts", postPayload);
            log("POST Response: " + postResponse);

            // Example usage of properties management
            Properties props = loadProperties("config.properties");
            log("Properties Loaded: " + props);

            props.setProperty("newKey", "newValue");
            saveProperties(props, "config.properties");
            log("Properties Saved");

            // Example usage of title case conversion
            String titleCase = toTitleCase("hello world example");
            log("Title Case: " + titleCase);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
