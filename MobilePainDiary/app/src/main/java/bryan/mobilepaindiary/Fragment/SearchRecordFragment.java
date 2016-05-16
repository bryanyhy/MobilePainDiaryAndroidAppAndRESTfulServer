package bryan.mobilepaindiary.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import bryan.mobilepaindiary.ObjectClass.DailyRecord;
import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 27/4/16.
 */

// search all daily record on the user
public class SearchRecordFragment extends Fragment implements View.OnClickListener {

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    View view;
    private Spinner dateTimeSpinner;

    private int userId;
    private int indexPosition;
    private String latitude;
    private String longitude;
    private String dateTime;
    private Button searchButton;

    ArrayList<String> dateTimes = new ArrayList<String>();
    ArrayList<DailyRecord> dRecords = new ArrayList<>();
    ArrayList<JSONObject> jRecords = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_record, container, false);
        userId = getArguments().getInt("userId");
        dateTimeSpinner = (Spinner) view.findViewById(R.id.dateTimeSpinner);

        new SetupRecords().execute();

        searchButton = (Button) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);


        return view;
    }

    // get all daily records for the logged in user
    class SetupRecords extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... voids) {
            return findRecordsByUserId();
        }

        protected void onPostExecute(String result) {
            try {
                // save data into dailyrecord object, so we can pass it to fragment to edit or delete
                JSONArray jsonarray = new JSONArray(result);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    jRecords.add(jsonobject);
                    String moodLevel = jsonobject.getString("moodLevel");
                    int painLevel = jsonobject.getInt("painLevel");
                    String painLocation = jsonobject.getString("painLocation");
                    String painTrigger = jsonobject.getString("painTrigger");
                    String recordDate = jsonobject.getString("recordDate");
                    String recordTime = jsonobject.getString("recordTime");
                    int recordId = jsonobject.getInt("recordId");
                    DailyRecord d = new DailyRecord(recordId, recordDate, recordTime, painLevel, painLocation, painTrigger, moodLevel);
                    dRecords.add(d);
                }
                for (int i = 0; i < dRecords.size(); i++) {
                    // group date and time into one string for options in spinner
                    String part1 = dRecords.get(i).getRecordDate().substring(0, 10);
                    String part2 = dRecords.get(i).getRecordTime().substring(10, 19);
                    String dateTime = part1 + part2;
                    dateTimes.add(dateTime);
                    // setup date time spinner on daily records
                    setUpDateTimeSpinner();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // setup date time spinner, options are date time from daily records
    void setUpDateTimeSpinner() {
        final ArrayAdapter<String> dateTimeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, dateTimes);
        dateTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateTimeAdapter.notifyDataSetChanged();
        dateTimeSpinner.setAdapter(dateTimeAdapter);
        dateTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                dateTime = parent.getItemAtPosition(position).toString();
                indexPosition = position;
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    // find all user based on user id from rest server
    public String findRecordsByUserId() {
        //path of the method
        final String methodPath = "/restclient.dailyrecord/findByUserId/" + userId;
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

    // once the search button is clicked, pass the bundle with the selected daily record and the JSON response to view record fragment
    public void searchRecord(View v) {
        Fragment nextFragment = null;
        Bundle bundle = new Bundle();
        bundle.putSerializable("dRecord", dRecords.get(indexPosition));
        bundle.putString("jRecord", jRecords.get(indexPosition).toString());
        nextFragment = new ViewRecordFragment();
        nextFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                nextFragment).commit();
    }

    // clicking of search button
    @Override
    public void onClick(View v)  {
            searchRecord(v);
    }
}

