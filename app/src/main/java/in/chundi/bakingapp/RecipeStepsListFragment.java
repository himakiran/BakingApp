package in.chundi.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView mRecyclerView;
    StepsListAdapter listAdapter;
    RecyclerView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Bundle bundle;
    private JSONArray j;
    private String TAG = RecipeStepsListFragment.class.getSimpleName();
    private int scrollPosition;
    public RecipeStepsListFragment() {

    }

    // Inflates the details layout of all Recipe steps
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        bundle = getArguments();


        String jsonString = bundle.getString("jsonStepsArray");

        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_list_item, container, false);

        // Here we r displaying the recycler view in a linear layout fashion
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_list);

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
            /*
            Comments : Required :
            It is required that you restore the position of the recycler view post rotation.

            Note: I had already implemented for two cases below but forgot to do that here..
            now done.
             */
        scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .getPosition(mRecyclerView);

        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mRecyclerView.scrollToPosition(scrollPosition);


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
                //Log.d(TAG, "Position i = " + i + " :: " + tempS.toString());


            }

            expListView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_list);
            listAdapter = new StepsListAdapter(getContext(), listDataHeader, listDataChild);
            //Log.d(TAG,"LISTDATAHEADER :: " +listDataHeader.toString());
            //Log.d(TAG,"LISTDATACHILD :: " +listDataChild.toString());
            expListView.setAdapter(listAdapter);

        } catch (JSONException je) {
            Log.d(TAG, je.toString());
        }
        Log.d(TAG, getActivity().toString());
        // Return the root view
        return rootView;

    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }


        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
}
