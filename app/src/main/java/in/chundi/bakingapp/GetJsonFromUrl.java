package in.chundi.bakingapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by userhk on 01/07/17.
 * This class implements getJson method which returns a JsonObject from an url
 */

public class GetJsonFromUrl {


    public static JSONObject getJson(String url) {

        String TAG = GetJsonFromUrl.class.getSimpleName();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        URL Url = null;
        JSONObject bakeJson = null;
        InputStream inputStream = null;
        StringBuffer buffer;

        try {
            Url = new URL(url);
            urlConnection = (HttpURLConnection) Url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            inputStream = urlConnection.getInputStream();
            buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                bakeJson = null;
                Log.e("TAG", "Nothing received");

            } else {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // To make json readable.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.
                    bakeJson = null;
                    Log.e("TAG", "buffer length is zero");
                }
                bakeJson = new JSONObject(buffer.toString());

            }
        } catch (IOException | JSONException i) {
            Log.e(TAG, i.toString());
        }
        // Check if Json string received correctly
        Log.i(TAG, bakeJson.toString());
        return bakeJson;
    }

}

