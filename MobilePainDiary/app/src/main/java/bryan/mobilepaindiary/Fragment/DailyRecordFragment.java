package bryan.mobilepaindiary.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import bryan.mobilepaindiary.API.WeatherAPI;
import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 25/4/16.
 */
public class DailyRecordFragment extends Fragment implements View.OnClickListener {

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    View view;

    private SeekBar painSeekbar;
    private TextView painLevelText;
    private Spinner painLocationSpinner;
    private Spinner painTriggerSpinner;
    private Spinner moodSpinner;
    private Button submitButton;

    ArrayList<String> painLocations = new ArrayList<String>();
    ArrayList<String> painTriggers = new ArrayList<String>();
    ArrayList<String> moods = new ArrayList<String>();

    private WeatherAPI weatherAPI;

    private int painLevel = -1;
    private String painLocation;
    private String painTrigger;
    private String mood;

    private int userId;
    private String latitude;
    private String longitude;

    private String patientJSON;
    private String currentDate;
    private String currentTime;

    private String temperature;
    private String humidity;
    private String windSpeed;
    private String pressure;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_daily_record, container, false);
        userId = getArguments().getInt("userId");
        latitude = getArguments().getString("latitude");
        longitude = getArguments().getString("longitude");
        Log.i("System.out", String.valueOf(userId));
        Log.i("System.out", latitude);
        Log.i("System.out", longitude);

        // we have to use the weatherAPI to grab weather information
        weatherAPI = new WeatherAPI();

        // setup buttons, spinners and seekbar once the view is created
        setupPainLevel();
        setupPainLocation();
        setupPainTrigger();
        setupMood();
        submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        return view;
    }

    // setting up the pain level seekbar, which range is from 0 to 11
    public void setupPainLevel() {
        painSeekbar = (SeekBar) view.findViewById(R.id.painSeekbar);
        painLevelText = (TextView) view.findViewById(R.id.painLevelText);
        painSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                painLevel = progress;
                painLevelText.setText("Pain Level: " + progress + " / " + painSeekbar.getMax());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                painLevelText.setText("Pain Level: " + painLevel + " / " + painSeekbar.getMax());
                Log.i("System.out", String.valueOf(painLevel));
            }
        });
    }

    //setup the pain location spinner
    public void setupPainLocation() {
        painLocationSpinner = (Spinner) view.findViewById(R.id.painLocationSpinner);

        //options
        painLocations.add("<Please select>");
        painLocations.add("back");
        painLocations.add("neck");
        painLocations.add("head");
        painLocations.add("knees");
        painLocations.add("hips");
        painLocations.add("abdomen");
        painLocations.add("elbows");
        painLocations.add("shoulders");
        painLocations.add("shins");
        painLocations.add("jaws");
        painLocations.add("facial");

        final ArrayAdapter<String> painLocationAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, painLocations);
        painLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        painLocationSpinner.setAdapter(painLocationAdapter);
        painLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                painLocation = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    // setting up pain trigger spinner
    public void setupPainTrigger() {
        painTriggerSpinner = (Spinner) view.findViewById(R.id.painTriggerSpinner);

        // options
        painTriggers.add("<Please select>");
        painTriggers.add("running");
        painTriggers.add("walking");
        painTriggers.add("reading");
        painTriggers.add("lifting");
        painTriggers.add("standing");
        painTriggers.add("jumping");
        painTriggers.add("working");

        final ArrayAdapter<String> painTriggerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, painTriggers);
        painTriggerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        painTriggerSpinner.setAdapter(painTriggerAdapter);
        painTriggerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                painTrigger = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    // setting up mood level spinner
    public void setupMood() {
        moodSpinner = (Spinner) view.findViewById(R.id.moodSpinner);

        // options
        moods.add("<Please select>");
        moods.add("very low");
        moods.add("low");
        moods.add("average");
        moods.add("good");
        moods.add("very good");

        final ArrayAdapter<String> moodAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, moods);
        moodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(moodAdapter);
        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                mood = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                mood = "empty";
            }
        });
    }

    // if the submit button is clicked
    @Override
    public void onClick(View v)  {
        // check if pain level has input
        if (painLevel == -1 )
        {
            alertBox("Pain level should be selected.");
        }
        // check if pain location is seleceted
        if (painLocation.equals("<Please select>"))
        {
            alertBox("Pain location should be selected.");
        }
        // check if pain trigger is seleceted
        if (painTrigger.equals("<Please select>"))
        {
            alertBox("Pain trigger should be selected.");
        }
        // check if mood is seleceted
        if (mood.equals("<Please select>"))
        {
            alertBox("Mood should be selected.");
        }
        // if all are selected
        if (painLevel != -1 && !painLocation.equals("<Please select>") && !painTrigger.equals("<Please select>") && !mood.equals("<Please select>"))
        {
            // get the user info first
            getCurrentPatient();
        }
    }

    // get info of current user, for building up the JSON on POST later on
    public void getCurrentPatient() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // getting the result in a JSON format that can POST easily
                String result = getCurrentPatientJSON().replace("[", "");
                patientJSON = result.replace("]", "");
                return result;
            }
            @Override
            protected void onPostExecute(String response) {
                // get the date part from current datetime
                currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                currentDate = currentDate + "T00:00:00+10:00";
                // get the time part from current datetime
                currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                currentTime = "1970-01-01T" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) +"+10:00";
                // get weather attribute
                getWeatherAttribute();
            }
        }.execute();
    }

    // get the JSON reutrn of current user info by user id
    public String getCurrentPatientJSON() {
        final String methodPath = "/restclient.patient/findByUserId/" + userId;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
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
            Log.i("System.out", "error");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // getting weather attribute from api
    public void getWeatherAttribute() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // get JSON response from API
                return weatherAPI.findWeatherInfo(latitude, longitude);
            }
            @Override
            protected void onPostExecute(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject main = jsonObj.getJSONObject("main");
                    // get change the temperature from K to celsius, with one decimal point only
                    double temperatureInDouble = (double)Math.round((main.getDouble("temp") - 274.15) * 10d) / 10d;
                    temperature = String.valueOf(temperatureInDouble);
                    // get humidity
                    humidity = main.getString("humidity");
                    // get pressure
                    pressure = main.getString("pressure");

                    JSONObject wind = jsonObj.getJSONObject("wind");
                    // get wind speed
                    windSpeed = wind.getString("speed");
                    // build up and add the record
                    addRecord();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    // add the record into the rest server
    public void addRecord() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return addNewRecord();
            }
            @Override
            protected void onPostExecute(String response) {
                // remind user that the record is created
                alertBox("The record is created.");
            }
        }.execute();
    }

    // POST the new record into the rest server
    public String addNewRecord() {
        final String methodPath = "/restclient.dailyrecord";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            String json = "{\"atmosphericPressure\":" + pressure + ",\"humidity\":" + humidity + ",\"latitude\":\"" + latitude +
                    "\",\"longitude\":\"" + longitude + "\",\"moodLevel\":\"" + mood + "\",\"painLevel\":" + painLevel +
                    ",\"painLocation\":\"" + painLocation + "\",\"painTrigger\":\"" + painTrigger + "\",\"recordDate\":\"" + currentDate +
                    "\",\"recordId\":" + 1 + ",\"recordTime\":\"" + currentTime + "\",\"temperature\":" + temperature +
                    ",\"userId\":" + patientJSON + ",\"windSpeed\":" + windSpeed + "}";
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(json.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(json);
            out.close();
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("System.out", "Add registration error");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // alertbox for reminding user
    public void alertBox(String output) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Reminder");
        alertDialog.setMessage(output);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
