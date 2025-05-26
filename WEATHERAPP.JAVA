import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
   
    private static final String API_KEY = "696ea53ea7ab505bba3f85c182a0a2ee";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();

        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city +
                               "&appid=" + API_KEY + "&units=metric";
            URI uri = URI.create(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
            parseWeatherData(result.toString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    private static void parseWeatherData(String json) {
        String tempMarker = "\"temp\":";
        String descMarker = "\"description\":\"";

        int tempIndex = json.indexOf(tempMarker) + tempMarker.length();
        int tempEnd = json.indexOf(",", tempIndex);
        String temperature = json.substring(tempIndex, tempEnd);

        int descIndex = json.indexOf(descMarker) + descMarker.length();
        int descEnd = json.indexOf("\"", descIndex);
        String description = json.substring(descIndex, descEnd);

        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Condition: " + description);
    }
}
