package in.chundi.bakingapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by userhk on 01/07/17.
 * This class is used to run the GetJsonFromUrl as an AsyncTask away from the main thread.
 */

public class BakingRecipeAsynctask extends AsyncTask<String, String, JSONObject> {
    public String TAG = BakingRecipeAsynctask.class.getSimpleName();
    ProgressDialog progressDialog;
    private String resp;

    @Override
    protected JSONObject doInBackground(String... params) {

        String bakeUrl = params[0];
        JSONObject result = null;

        try {
            GetJsonFromUrl.getJson(bakeUrl);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        // dismiss progressDialog
        progressDialog.dismiss();

    }


}



