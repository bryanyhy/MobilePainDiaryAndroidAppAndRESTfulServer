package bryan.mobilepaindiary.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 27/4/16.
 */
// load url for the webview to display map. The html is setup in rest server side
public class MapFragment extends Fragment {

    View view;

    private String latitude;
    private String longitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        // get latitude and longitude from bundle from main activity
        latitude = getArguments().getString("latitude");
        longitude = getArguments().getString("longitude");

        // setup webview
        WebView wv1 = (WebView)view.findViewById(R.id.webView);
        wv1.setWebChromeClient(
                new WebChromeClient() {
                    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                        callback.invoke(origin, true, false);
                    }
                }
        );
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setAppCacheEnabled(true);
        wv1.getSettings().setDatabaseEnabled(true);
        wv1.getSettings().setDomStorageEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // connect to the server side html for displaying the map, by sending user latitude and longitude
        String file = "http://10.0.2.2:8080/PainDiaryDB/sample.html?data={lat:%20" + latitude +
                        ",%20lng:%20" + longitude + "}";
        wv1.loadUrl(file);
        return view;
    }
}

