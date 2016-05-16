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

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import bryan.mobilepaindiary.ObjectClass.DailyRecord;
import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 28/4/16.
 */
public class ViewRecordFragment extends Fragment {

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    View view;

    private SeekBar painSeekbar;
    private TextView painLevelText;
    private Spinner painLocationSpinner;
    private Spinner painTriggerSpinner;
    private Spinner moodSpinner;
    private Button editButton;
    private Button deleteButton;

    ArrayList<String> painLocations = new ArrayList<String>();
    ArrayList<String> painTriggers = new ArrayList<String>();
    ArrayList<String> moods = new ArrayList<String>();

    private int painLevel;
    private String painLocation;
    private String painTrigger;
    private String mood;

    private int recordId;
    private String latitude;
    private String longitude;

    private String patientJSON;
    private String currentDate;
    private String currentTime;

    private String temperature;
    private String humidity;
    private String windSpeed;
    private String pressure;

    private DailyRecord dRecord;
    private JSONObject jRecord;

    // grab the bundle from search record fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_view_record, container, false);
        Bundle bundle = getArguments();
        dRecord= (DailyRecord) bundle.getSerializable("dRecord");
        String jRecordInString = getArguments().getString("jRecord");
        Log.i("System.out", jRecordInString);
        try {
            jRecord = new JSONObject(jRecordInString);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        recordId = dRecord.getRecordId();
        // setup seekbar and spinner, same as what we did in add record fragment
        setupPainLevel();
        setupPainLocation();
        setupPainTrigger();
        setupMood();

        // setup edit and delete button
        setupEditButton();
        setupDeleteButton();

        return view;
    }

    // setupt pain level seekbar
    public void setupPainLevel() {
        painLevel = dRecord.getPainLevel();
        painSeekbar = (SeekBar) view.findViewById(R.id.painSeekbar);
        painLevelText = (TextView) view.findViewById(R.id.painLevelText);
        // set the default value to be daily record's one
        painSeekbar.setProgress(dRecord.getPainLevel());
        painLevelText.setText("Pain Level: " + dRecord.getPainLevel() + " / " + painSeekbar.getMax());
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

    // setup pain location spinner
    public void setupPainLocation() {
        painLocation = dRecord.getPainLocation();
        painLocationSpinner = (Spinner) view.findViewById(R.id.painLocationSpinner);

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

        // set the default value to be daily record's one
        int spinnerPosition = painLocationAdapter.getPosition(dRecord.getPainLocation());
        painLocationSpinner.setSelection(spinnerPosition);

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

    // setup pain trigger spinner
    public void setupPainTrigger() {
        painTrigger = dRecord.getPainTrigger();
        painTriggerSpinner = (Spinner) view.findViewById(R.id.painTriggerSpinner);

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

        // set the default value to be daily record's one
        int spinnerPosition = painTriggerAdapter.getPosition(dRecord.getPainTrigger());
        painTriggerSpinner.setSelection(spinnerPosition);

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

    // setup mood level spinner
    public void setupMood() {
        mood = dRecord.getMood();
        moodSpinner = (Spinner) view.findViewById(R.id.moodSpinner);

        moods.add("<Please select>");
        moods.add("very low");
        moods.add("low");
        moods.add("average");
        moods.add("good");
        moods.add("very good");

        final ArrayAdapter<String> moodAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, moods);
        moodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(moodAdapter);

        // set the default value to be daily record's one
        int spinnerPosition = moodAdapter.getPosition(dRecord.getMood());
        moodSpinner.setSelection(spinnerPosition);

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

    // setup edit button
    public void setupEditButton() {
        editButton = (Button) view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            // edit record wehn clicked
            @Override
            public void onClick(View v) {
                Log.i("System.out", "edit");
                new AsyncTask<Integer, Void, String>() {
                    @Override
                    protected String doInBackground(Integer... params) { return editExistingRecord();
                    }
                    @Override
                    protected void onPostExecute(String response) {
                        // reminder to user
                        alertBox("Record is updated.");
                    }
                }.execute();
            }
        });
    }

    // setup delete button
    public void setupDeleteButton() {
        deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete record when clicked
                Log.i("System.out", "delete");
                new AsyncTask<Integer, Void, String>() {
                    @Override
                    protected String doInBackground(Integer... params) { return deleteExistingRecord();
                    }
                    @Override
                    protected void onPostExecute(String response) {
                        // reminder to user
                        alertBox("Record is deleted.");
                    }
                }.execute();
            }
        });
    }

    // POST change to existing record
    public String editExistingRecord() {
        final String methodPath = "/restclient.dailyrecord/" + recordId;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            jRecord.put("painLevel",painLevel);
            jRecord.put("painLocation",painLocation);
            jRecord.put("painTrigger",painTrigger);
            jRecord.put("moodLevel", mood);
            String json = jRecord.toString();;
            Log.i("System.out", json);
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setFixedLengthStreamingMode(json.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(json);
            out.close();
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.i("System.out", textResult);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("System.out", "Add registration error");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // delete record by record id
    public String deleteExistingRecord() {
        final String methodPath = "/restclient.dailyrecord/" + recordId;

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            Log.i("System.out", textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("System.out", "Add registration error");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // alertbox for reminder
    public void alertBox(String output) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Alert");
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