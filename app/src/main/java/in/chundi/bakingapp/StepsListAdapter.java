package in.chundi.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by userhk on 01/07/17.
 * Code used and modified from https://guides.codepath.com/android/using-the-recyclerview
 * This adapter ties the recycler view (fragment_recipe_master_listto recipe images
 * each of which will fill up the image view in fragment_recipe_master_list_item.xml to the adapter
 * The adpater shall take a json object and retrieve images/thumbnails to show the recipe
 * <p>
 * using PICASSO Library to load images
 */

public class StepsListAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    private static final String TAG = StepsListAdapter.class.getSimpleName();
    private ArrayList<String> stepsList;
    private HashMap<String, List<String>> childStepsList;
    private Context mContext;


    public StepsListAdapter(Context context, ArrayList<String> steps, HashMap<String, List<String>> childSteps) {
        mContext = context;
        stepsList = steps;
        childStepsList = childSteps;

    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_recipe_show_steps_list, parent, false);
        StepsViewHolder stepsViewHolder = new StepsViewHolder(view);

        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {


        TextView textView = holder.stepsShortDescTextView;
        textView.setText(stepsList.get(position));

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private Context getContext() {
        return mContext;
    }

//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public ImageView imgName;
//        public TextView textTitle;
//        private Context context;
//
//        public ViewHolder(Context context, View itemView) {
//            super(itemView);
//            this.imgName = (ImageView) itemView.findViewById(R.id.recipe_item_image);
//            this.textTitle = (TextView) itemView.findViewById(R.id.recipe_item_title);
//            // Store the context
//            this.context = context;
//            // Attach a click listener to the entire row view
//            itemView.setOnClickListener(this);
//        }
//
//        // Handles the row being being clicked
//        @Override
//        public void onClick(View view) {
//            int position = getAdapterPosition(); // gets item position
//            if (position != RecyclerView.NO_POSITION) {
//
//                Toast.makeText(context, R.string.RcpNotAvail, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

}
