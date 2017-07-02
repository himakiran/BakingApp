package in.chundi.bakingapp;

import org.json.JSONArray;

/**
 * Created by userhk on 02/07/17.
 * this class returns the result of the Asynctask to MainActivity
 */

public interface AsyncResponse {

    void processFinish(JSONArray result);

}
