package bryan.mobilepaindiary.API;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Bryanyhy on 26/4/16.
 */

//For getting the JSON response on latitude and longitude of a input address

public class GeocodingAPI {

    public static final String BASE_URI = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String PRIVATEKEY = "&key=AIzaSyDfvMTdWaMFMAWKJRiAE0Ej5JS4Y10eais";

    public String findLocationInfo(String strAddress) {
        //path of the method
        String address = strAddress.replaceAll(" ", "+");
        Log.i("System.out", address);
        final String methodPath = BASE_URI + address + PRIVATEKEY;
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
