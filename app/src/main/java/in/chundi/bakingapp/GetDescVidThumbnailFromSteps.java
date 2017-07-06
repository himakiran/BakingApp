package in.chundi.bakingapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by userhk on 05/07/17.
 */

public class GetDescVidThumbnailFromSteps {
    private ArrayList<String> mString;
    private String TAG = "GetDescVidTSteps";

    public GetDescVidThumbnailFromSteps(ArrayList<String> s) {

        mString = s;
        Log.d(TAG, "M STRING ARRAY IS " + mString.toString());


    }

    public String getLongDesc() {
        return mString.get(0);
    }

    public String getVideoUrl() {
        return mString.get(1);
    }

    public String getThumbnai() {
        return mString.get(2);
    }
}
