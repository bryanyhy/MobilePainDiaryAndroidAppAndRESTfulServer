package bryan.mobilepaindiary.API;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Bryanyhy on 26/4/16.
 */

// Get the real time weather information JSON based on input latitude and longitude

public class WeatherAPI {

    public static final String BASE_URI = "http://api.openweathermap.org/data/2.5/weather?lat=";
    public static final String BASE_URI2 = "&lon=";
    public static final String PRIVATEKEY = "&appid=88317a82cc2e4ebce4f29e21daa61651";

    public String findWeatherInfo(String lat, String lon) {
        //path of the method
        final String methodPath = BASE_URI + lat + BASE_URI2 + lon + PRIVATEKEY;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Log.i("System.out", "start");
            url = new URL(methodPath);
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.i("System.out", textResult);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
}
