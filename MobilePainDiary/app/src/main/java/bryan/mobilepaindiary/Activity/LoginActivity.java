package bryan.mobilepaindiary.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

import bryan.mobilepaindiary.Utilities.HashPassword;
import bryan.mobilepaindiary.R;

//correct id: grey421
//correct pw: qweasd

// for login
public class LoginActivity extends AppCompatActivity {

    public static final int REGISTER_REQUEST = 1;
    public static final int LOGIN_REQUEST = 2;

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    private EditText usernameInput;
    private EditText passwordInput;
    private String username;
    private String password;
    private int userId;
    private String firstname;
    private String address;
    private String regDateTime;

    private ProgressDialog prgDialog;

    private HashPassword hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        // setup new hash password object
        hp = new HashPassword();
        Log.i("System.out", hp.passwordToHash("12345"));
        Log.i("System.out", hp.passwordToHash("54321"));
        Log.i("System.out", hp.passwordToHash("qweasd"));
        Log.i("System.out", hp.passwordToHash("asdzxc"));
        Log.i("System.out", hp.passwordToHash("123qwe"));

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }

    // Create options menu, which is for register
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Once register is selected from the menu, system will bring show the register screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                Intent i = new Intent(this, RegisterActivity.class);
                startActivityForResult(i, REGISTER_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // when login button is clicked
    public void login(View v) throws ParseException {
        try {
            username = usernameInput.getText().toString();
            password = passwordInput.getText().toString();
            // Checking if username and password field is filled
            if (username.matches("") || password.matches(""))
            {
                // remind user if empty
                alertBox("No empty input is allowed!");
            } else
            {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params)
                    {
                        // get username and password from username input by user
                        return findRegistration(username);
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        String inputPassword = password;
                        try{
                            JSONArray jsonarray = new JSONArray(result);
                            // if the return JSONarray is empty, that mean the username is not exist
                            if (jsonarray.length() == 0)
                            {

                                Log.i("System.out", "No result");
                                alertBox("Your username is incorrect.");
                            } else
                            {
                                // if username exist
                                Log.i("System.out", result);
                                JSONObject jsonobject = jsonarray.getJSONObject(0);
                                String correctPassword = jsonobject.getString("password");
                                regDateTime = jsonobject.getString("regDatetime");
                                // compare the input pw with hash and the password in server side
                                if (hp.compareHash(inputPassword, correctPassword))
                                {
                                    // if password and username are match
                                    JSONObject user = jsonobject.getJSONObject("userId");
                                    userId = user.getInt("userId");
                                    firstname = user.getString("userFirstname");
                                    address = user.getString("userAddress");
                                    // setup new intent to main activity
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    // setup buddle to pass variables to main activity
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("userId", userId);
                                    bundle.putString("firstname", firstname);
                                    bundle.putString("address", address);
                                    bundle.putString("regDateTime", regDateTime);
                                    i.putExtras(bundle);
                                    startActivityForResult(i, LOGIN_REQUEST);
                                    finish();
                                } else
                                {
                                    // if password is not correct
                                    alertBox("Your password is incorrect.");
                                }
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get JSON response by username
    public String findRegistration(String username) {
        //path of the method
        final String methodPath = "/restclient.registration/findByUsername/" + username;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            Log.i("System.out", "start");
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
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
            alertBox("Wrong username!");
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    // alertbox for reminding user
    public void alertBox(String output) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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