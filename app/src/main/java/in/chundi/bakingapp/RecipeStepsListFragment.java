package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by userhk on 03/07/17.
 */

public class RecipeStepsListFragment extends Fragment {
    TextView textView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> childList;
    HashMap<String, List<String>> listDataChild;
    private Bundle bundle;
    private JSONArray j;
    private String TAG = RecipeStepsListFragment.class.getSimpleName();

    public RecipeStepsListFragment() {

    }

    // Inflates the details layout of all Recipe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        bundle = getArguments();


        String jsonString = bundle.getString("jsonStepsArray");

        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list_item, container, false);

        try {

            j = new JSONArray(jsonString);
            // Get the content of listDataHeader and listDatachild from the json array
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            JSONObject temp;
            List<String> tempS;
            for (int i = 0; i < j.length(); i++) {
                temp = j.getJSONObject(i);
                tempS = new ArrayList<String>();
                listDataHeader.add(i, temp.getString("shortDescription"));
                tempS.add(0, temp.getString("description"));
                tempS.add(1, temp.getString("videoURL"));
                tempS.add(2, temp.getString("thumbnailURL"));
                listDataChild.put(temp.getString("shortDescription"), tempS);

            }

            expListView = (ExpandableListView) rootView.findViewById(R.id.recipe_steps_list);
            listAdapter = new ExpandableIngredientListAdapter(getContext(), listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);

        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }

        // Return the root view
        return rootView;

    }


}
