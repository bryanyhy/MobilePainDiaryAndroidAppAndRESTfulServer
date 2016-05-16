package bryan.mobilepaindiary.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import bryan.mobilepaindiary.Fragment.LineGraph;
import bryan.mobilepaindiary.Fragment.PieChartFragment;
import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 25/4/16.
 */

// create and view report
public class ReportFragment extends Fragment {

    View view;

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    private int userId;

    private EditText startDateInput;
    private EditText endDateInput;
    private Spinner weatherSpinner;
    private Button lgButton;
    private Button pcButton;

    private String startDate;
    private String endDate;
    private String weather;

    private String correlation;
    private String pValue;

    // options for weather attribute
    String weatherType[] = {"<Please select>", "atmosphericPressure", "humidity", "temperature", "windSpeed"};

    ArrayList<String> dataPointXArrayList = new ArrayList<>();
    ArrayList<String> dataPointYArrayList = new ArrayList<>();
    ArrayList<String> dataPointY2ArrayList = new ArrayList<>();

    ArrayList<Integer> count = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();

    final Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_report, container, false);
        userId = getArguments().getInt("userId");
        Log.i("System.out", String.valueOf(userId));

        startDateInput = (EditText) view.findViewById(R.id.startDateInput);
        endDateInput = (EditText) view.findViewById(R.id.endDateInput);

        // setup spinner, line graph and pie chart buttons
        setupSpinner();
        setupLineGraph();
        setupPieChart();

        pcButton = (Button) view.findViewById(R.id.pcButton);
        return view;
    }

    // setup the weather attribute spinner
    public void setupSpinner() {

        weatherSpinner = (Spinner) view.findViewById(R.id.weatherSpinner);
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, weatherType);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(weatherAdapter);
        weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                weather = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    // setup the draw line graph button
    public void setupLineGraph() {
        lgButton = (Button) view.findViewById(R.id.lgButton);
        lgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (weather.equals("<Please select>"))
                    {
                        // if no weather is selected
                        alertBox("Please select a weather attribute.");
                    }
                    else {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        Date startD = formatter.parse(startDateInput.getText().toString());
                        Date endD = formatter.parse(endDateInput.getText().toString());
                        startDate = formatter.format(startD);
                        endDate = formatter.format(endD);
                        // get the data for line graph from rest server
                        new getDataForLineGraph().execute();
                    }
                } catch (Exception e) {
                    // if date input is not valid
                    alertBox("Date input should be in a correct format.");
                }
            }
        });
    }

    // setup the draw pie chart button
    public void setupPieChart() {
        pcButton = (Button) view.findViewById(R.id.pcButton);
        pcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date startD = formatter.parse(startDateInput.getText().toString());
                    Date endD = formatter.parse(endDateInput.getText().toString());
                    startDate = formatter.format(startD);
                    endDate = formatter.format(endD);
                    // get data for pie chart
                    new getDataForPieChart().execute();
                } catch (Exception e) {
                    // if date input is not valid
                    alertBox("Date input should be in a correct format.");
                }
            }
        });
    }

    // create fragment for line graph, and pass the bundle
    public void createLineGraph() {
        Fragment fragment = new LineGraph();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    // create fragment for pie chart, and pass the bundle
    public void createPieChart() {
        Fragment fragment = new PieChartFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    // async task to get data from rest server
    class getDataForLineGraph extends AsyncTask<Void, Void, String> {

        // get daily record data JSON response
        protected String doInBackground(Void... voids) {
            return getRecordData();
        }

        protected void onPostExecute(String result) {
            try {
                Log.i("System.out", result);
                JSONArray jsonarray = new JSONArray(result);
                for (int i = 0; i < jsonarray.length(); i++)
                {
                    // save data into arraylist, for passing to line graph fragment later
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    dataPointXArrayList.add(jsonobject.getString("recordDate"));
                    dataPointYArrayList.add(jsonobject.getString("painLevel"));
                    dataPointY2ArrayList.add(jsonobject.getString("attribute"));
                }
                // get correlation result also
                new getCorrelation().execute();

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // get record data based on user's input and their id
    public String getRecordData() {
        //path of the method
        final String methodPath = "/restclient.dailyrecord/q4b/" + userId + "/" + startDate + "/" + endDate + "/" + weather;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Log.i("System.out", "start");
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // get correlation from rest server
    class getCorrelation extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... voids) {
            return getCorrelationString();
        }

        protected void onPostExecute(String result) {
            try {
                Log.i("System.out", result);
                String[] parts = result.split("!");
                correlation = parts[1];
                pValue = parts[2];
                // pass all info we need for correlation and line graph into line grpah fragment
                bundle.putStringArrayList("X", dataPointXArrayList);
                bundle.putStringArrayList("Y1", dataPointYArrayList);
                bundle.putStringArrayList("Y2", dataPointY2ArrayList);
                bundle.putString("weather", weather);
                bundle.putString("correlation", correlation);
                bundle.putString("pValue", pValue);
                createLineGraph();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // get correlation result in JSON by the method we setup in assignment 1
    public String getCorrelationString() {
        //path of the method
        final String methodPath = "/restclient.dailyrecord/findCorrelationByStartAndEndDateAndWeatherVariable/" + startDate + "/" + endDate + "/" + weather;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Log.i("System.out", "start");
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // get data for pie chart from rest server
    class getDataForPieChart extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... voids) {
            return getRecordDataForPieChart();
        }

        protected void onPostExecute(String result) {
            try {
                Log.i("System.out", result);
                JSONArray jsonarray = new JSONArray(result);
                // get painlocation and its frequency
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    labels.add(jsonobject.getString("painLocation"));
                    count.add(jsonobject.getInt("frequency"));
                }
                // pass the data in bundle to pie chart fragment
                bundle.putIntegerArrayList("count", count);
                bundle.putStringArrayList("labels", labels);
                createPieChart();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // get record data through the method we develop in assignment 1
    public String getRecordDataForPieChart() {
        //path of the method
        final String methodPath = "/restclient.dailyrecord/q4c/" + userId + "/" + startDate + "/" + endDate;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Log.i("System.out", "start");
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
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // alert box for reminder
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

