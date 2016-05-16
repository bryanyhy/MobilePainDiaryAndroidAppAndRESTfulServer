package bryan.mobilepaindiary.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import bryan.mobilepaindiary.API.GeocodingAPI;
import bryan.mobilepaindiary.SQLite.DatabaseHelper;
import bryan.mobilepaindiary.Utilities.HashPassword;
import bryan.mobilepaindiary.ObjectClass.Doctor;
import bryan.mobilepaindiary.ObjectClass.Patient;
import bryan.mobilepaindiary.ObjectClass.Record;
import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 24/4/16.
 */
// for registration of user
public class RegisterActivity extends AppCompatActivity {

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText firstnameInput;
    private EditText surnameInput;
    private EditText dobInput;
    private EditText addressInput;
    private EditText heightInput;
    private EditText weightInput;
    private EditText occupationInput;
    private Spinner genderSpinner;
    private Spinner doctorSpinner;
    private Spinner clinicSpinner;
    private Button submitButton;

    // options for gender spinner
    String genderType[] = {"M", "F"};
    ArrayList<String> doctorName;
    ArrayList<String> clinicName;

    private String username;
    private String password;
    private String firstname;
    private String surname;
    private Date dobAsDate;
    private String dobAsString;
    private String address;
    private Double height;
    private Double weight;
    private String occupation;
    private String gender;
    private String doctor;
    private String clinic;
    private int docId;
    private int userId;

    private String newlyAddedPatientJSON;
    private String currentDateTime;

    private int realUserId;
    private GeocodingAPI geocode;
    private String latitude;
    private String longitude;

    private ArrayList<Doctor> doctors;

    private HashPassword hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctors = new ArrayList<>();
        doctorName = new ArrayList<>();
        clinicName = new ArrayList<>();
        geocode = new GeocodingAPI();
        new SetupDoctor().execute();
        setContentView(R.layout.activity_register);

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        firstnameInput = (EditText) findViewById(R.id.firstnameInput);
        surnameInput = (EditText) findViewById(R.id.surnameInput);
        dobInput = (EditText) findViewById(R.id.dobInput);
        addressInput = (EditText) findViewById(R.id.addressInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        weightInput = (EditText) findViewById(R.id.weightInput);
        occupationInput = (EditText) findViewById(R.id.occupationInput);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);
        clinicSpinner = (Spinner) findViewById(R.id.clinicSpinner);
        submitButton = (Button) findViewById(R.id.submitButton);

        // Creating adapter for gender spinner
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderType);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                gender = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    // when submit button is clicked
    public void submitRegistration(View v) throws ParseException {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        firstname = firstnameInput.getText().toString();
        surname = surnameInput.getText().toString();
        address = addressInput.getText().toString();
        occupation = occupationInput.getText().toString();
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            dobAsDate = formatter.parse(dobInput.getText().toString());
            dobAsString = formatter.format(dobAsDate) + "T00:00:00+10:00";
        } catch (Exception e) {
            // if date format is incorrect
            alertBox("Date input format should be \"yyyy-MM-dd\".");
        }
        try{
            height = Double.parseDouble(heightInput.getText().toString());
        } catch (Exception e) {
            // if height format is not correct
            alertBox("Height should be in number only.");
        }
        try{
            weight = Double.parseDouble(weightInput.getText().toString());
        } catch (Exception e) {
            // if weight format is not correct
            alertBox("Weight should be in number only.");
        }
        if (username.matches("") || password.matches("") || firstname.matches("") || surname.matches("") || address.matches("")
            || occupation.matches("") || dobInput.getText().toString().matches("") || height == null || weight == null) {
            // check if any empty field
            alertBox("No empty field is allowed.");
        } else
        {
            // spliting the doctor full name into first and last name
            String[] splited = doctor.split("\\s+");
            boolean valid = false;
            for (int i = 0; i < doctors.size(); i++) {
                // check if clinic match doctor first and last name
                if (splited[0].equals(doctors.get(i).getFirstname()) && splited[1].equals(doctors.get(i).getSurname())
                        && clinic.equals(doctors.get(i).getClinic()))
                {
                    valid = true;
                    docId = i + 1;
                    break;
                }
            }
            if (!valid)
            {
                // remind user if clicnic and doctor name is not matching
                alertBox("Doctor and clinic doesn't match.");
            } else
            {
                // if all correct, add data
                addData();
                // get the patient info we just created, as we have to know their user id and save it to SQLite
                getNewlyAddedPatient();
            }
        }
    }

    // setup doctor spinner
    class SetupDoctor extends AsyncTask<Void, Void, String> {

        // get every doctor name and clinic address in JSON
        protected String doInBackground(Void... voids) {
            return findDoctorAndClinic();
        }

        protected void onPostExecute(String result) {
            try {
                JSONArray jsonarray = new JSONArray(result);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String clinic = jsonobject.getString("clinicAddress");
                    int id = jsonobject.getInt("docId");
                    String firstname = jsonobject.getString("docFirstname");
                    String surname = jsonobject.getString("docSurname");
                    String phone = jsonobject.getString("docPhone");
                    String clinicPhone = jsonobject.getString("clinicPhone");
                    Doctor d = new Doctor(id, firstname, surname, phone, clinic, clinicPhone);
                    doctors.add(d);
                }
                // setup doctor and clinic arraylist from the JSON response
                for (int i = 0; i < doctors.size(); i++) {
                    String name = doctors.get(i).getFirstname() + " " + doctors.get(i).getSurname();
                    doctorName.add(name);
                    clinicName.add(doctors.get(i).getClinic());
                }

                // setup doctor spinner
                final ArrayAdapter<String> doctorAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, doctorName);
                doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorAdapter.notifyDataSetChanged();
                doctorSpinner.setAdapter(doctorAdapter);
                doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner item
                        doctor = parent.getItemAtPosition(position).toString();
                    }
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                // setup clinic spinner
                final ArrayAdapter<String> clinicAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, clinicName);
                clinicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clinicAdapter.notifyDataSetChanged();
                clinicSpinner.setAdapter(clinicAdapter);
                clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // On selecting a spinner item
                        clinic = parent.getItemAtPosition(position).toString();
                    }
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // get all doctor and clinic info from rest server
    public String findDoctorAndClinic() {
        //path of the method
        final String methodPath = "/restclient.doctor";
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

    // add the new user data to rest server
    public void addData() {
        // change the password into hased format
        password = hp.passwordToHash(password);
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // create a patient object
                Patient patient = new Patient(userId, firstname, surname, height, weight, gender, occupation, address, doctors.get(docId - 1));
                // pass the patient object to add patient method into the rest database
                return addPatient(patient);

            }
            @Override
            protected void onPostExecute(String response) {
                Log.i("System.out", response);
            }
        }.execute();
    }

    // add the patient into rest server
    public String addPatient(Patient patient) {
        //path of the method
        final String methodPath = "/restclient.patient";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Gson gson = new Gson();
            String jsonDoctor = gson.toJson(doctors.get(patient.getDocId().getId() - 1));
            String json = "{\"dob\":\"" + dobAsString + "\",\"docId\":" + jsonDoctor +",\"gender\":\"" + gender + "\"," +
                    "\"height\":" + height + ",\"occupation\":\"" + occupation + "\",\"userAddress\":\"" + address + "\"," +
                    "\"userFirstname\":\"" + firstname + "\",\"userId\":" + 1 + ",\"userSurname\":\"" + surname + "\",\"weight\":" + weight + "}";
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
            Log.i("System.out", textResult);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("System.out", "error");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // get the newly added patient, which we have to get its user id auto generated in rest server
    public void getNewlyAddedPatient() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = getNewlyAddedPatientJSON().replace("[", "");
                newlyAddedPatientJSON = result.replace("]", "");
                return result;
            }
            @Override
            protected void onPostExecute(String response) {
                Log.i("System.out", response);
                Log.i("System.out", "Got Newly added patient JSON.");
                // setup the new registration for the user
                currentDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
                currentDateTime = currentDateTime + "+10:00";
                addRegistration();
            }
        }.execute();
    }

    // JSON response, as we want to get the user id mainly from server
    public String getNewlyAddedPatientJSON() {
        final String methodPath = "/restclient.patient/findByFNameAndSNameAndHeightAndWeight/" + firstname + "/" + surname + "/" + height + "/" + weight;
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

    // add resgistration async task
    public void addRegistration() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return addNewRegistration();
            }
            @Override
            protected void onPostExecute(String response) {
                Log.i("System.out", response);
                Log.i("System.out", "Registration added");
                // find true user id and latitude and longitude, preparing for adding user to SQLite
                getTrueUserId();
                getLatAndLon();
            }
        }.execute();
    }

    // POST method to add registration to rest server
    public String addNewRegistration() {
        final String methodPath = "/restclient.registration";
        Log.i("System.out", currentDateTime);
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            String json = "{\"password\":\"" + password + "\",\"regDatetime\":\"" + currentDateTime +"\",\"regId\":" + 1 + "," +
                    "\"userId\":" + newlyAddedPatientJSON + ",\"username\":\"" + username+ "\"}";
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
            Log.i("System.out", textResult);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("System.out", "Add registration error");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // get the userid only from JSON response we called after adding the new patient
    public void getTrueUserId() {
        try
        {
            JSONObject jsonObj = new JSONObject(newlyAddedPatientJSON);
            realUserId = jsonObj.getInt("userId");
            Log.i("System.out", String.valueOf(realUserId));
        } catch (Exception e)
        {
        }
    }

    // get latitude and longitude from geocode API
    public void getLatAndLon() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return geocode.findLocationInfo(address);
            }
            @Override
            protected void onPostExecute(String response) {
                try {
                    Log.i("System.out", response);
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray results = jsonObj.getJSONArray("results");
                    JSONObject firstResult = results.getJSONObject(0);
                    JSONObject geometry = firstResult.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    latitude = location.getString("lat");
                    longitude = location.getString("lng");
                    // We got all info we need and write into SQLite
                    writeToSQLite();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    // write a new user to SQLite
    public void writeToSQLite() {
        Record r = new Record(realUserId, address, latitude, longitude, currentDateTime);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addRecord(r);
        alertBox("User is successfully added.");
        this.setResult(RESULT_OK);
    }

    // alertbox for reminding
    public void alertBox(String output) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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

