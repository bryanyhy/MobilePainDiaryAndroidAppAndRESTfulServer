package bryan.mobilepaindiary.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import bryan.mobilepaindiary.API.GeocodingAPI;
import bryan.mobilepaindiary.SQLite.DatabaseHelper;
import bryan.mobilepaindiary.Fragment.DailyRecordFragment;
import bryan.mobilepaindiary.Fragment.MainFragment;
import bryan.mobilepaindiary.Fragment.MapFragment;
import bryan.mobilepaindiary.Fragment.ReportFragment;
import bryan.mobilepaindiary.Fragment.SearchRecordFragment;
import bryan.mobilepaindiary.ObjectClass.Record;
import bryan.mobilepaindiary.R;

// first view once user logged in
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int userId;
    private String firstname;
    private String address;
    private String latitude;
    private String longitude;
    private String regDateTime;
    private GeocodingAPI geocode;

    private Record userRecord;

    DatabaseHelper dbHelper;

    public static final String BASE_URI = "http://10.0.2.2:8080/PainDiaryDB/webresources";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // grab the bundle from previous login screen
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        firstname = bundle.getString("firstname");
        address = bundle.getString("address");
        regDateTime = bundle.getString("regDateTime");
        // setup gecoding API, for user that not saved in the phone's SQLite
        geocode = new GeocodingAPI();
        // for SQLite
        dbHelper = new DatabaseHelper(this);
        try
        {
            // try to grab user info, which is latitude and longitude from SQLite
            userRecord = dbHelper.getRecordByUserId(userId);
            latitude = userRecord.getLatitude();
            longitude = userRecord.getLongitude();
            Log.i("System.out", "SQLite data founded.");
        } catch (Exception e)
        {
            // if the user is not in SQLite, get latitude and longitude based on their address and save it into SQLite
            Log.i("System.out", "SQLite data not founded.");
            new getLatAndLng().execute();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setup drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // title of action bar
        getSupportActionBar().setTitle("Mobile Pain Diary");

        // pass the bundle to main fragment, as we have to display user's firstname in there
        Bundle fnameBundle = new Bundle();
        fnameBundle.putString("firstname", firstname);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment mainFragment = new MainFragment();
        mainFragment.setArguments(fnameBundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, mainFragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // navigation bar
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;
        // setup bundle to pass userid, latitude and longitude to other fragments
        Bundle userIdBundle = new Bundle();
        userIdBundle.putInt("userId", userId);
        userIdBundle.putString("latitude", latitude);
        userIdBundle.putString("longitude", longitude);
        // setup the fragment to show base on user choice
        switch (id) {
            case R.id.nav_drecords:
                nextFragment = new DailyRecordFragment();
                nextFragment.setArguments(userIdBundle);
                break;
            case R.id.nav_srecords:
                nextFragment = new SearchRecordFragment();
                nextFragment.setArguments(userIdBundle);
                break;
            case R.id.nav_reports:
                nextFragment = new ReportFragment();
                nextFragment.setArguments(userIdBundle);
                break;
            case R.id.nav_maps:
                nextFragment = new MapFragment();
                nextFragment.setArguments(userIdBundle);
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                nextFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // get latitude and longitude, only for user that is not setup in the SQLite
    class getLatAndLng extends AsyncTask<Void, Void, String> {

        // get JSON response based on address
        protected String doInBackground(Void... voids) {
            return geocode.findLocationInfo(address);
        }

        protected void onPostExecute(String result) {
            try {
                Log.i("System.out", result);
                JSONObject jsonObj = new JSONObject(result);
                JSONArray results = jsonObj.getJSONArray("results");
                JSONObject firstResult = results.getJSONObject(0);
                JSONObject geometry = firstResult.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                latitude = location.getString("lat");
                longitude = location.getString("lng");
                // write data to sql
                writeToSQLite();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    // write the user info to SQLite
    public void writeToSQLite() {
        Record r = new Record(userId, address, latitude, longitude, regDateTime);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addRecord(r);
        Log.i("System.out", "A new data is created.");
    }

}
