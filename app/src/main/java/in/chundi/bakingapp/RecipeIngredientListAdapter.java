package in.chundi.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by userhk on 23/07/17.
 */

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<IngredientViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<String> listIngredientHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listIngredientChild;
    private String TAG = RecipeIngredientListAdapter.class.getSimpleName();
    private String ingredient;
    private int expandedPosition = -1;

    public RecipeIngredientListAdapter(Context context, ArrayList<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData) {
        mContext = context;
        listIngredientHeader = listDataHeader;
        listIngredientChild = listChildData;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_recipe_detail_ingredients_list_group_header, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);

        ingredientViewHolder.itemView.setOnClickListener(RecipeIngredientListAdapter.this);
        ingredientViewHolder.itemView.setTag(ingredientViewHolder);

        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        ingredient = listIngredientHeader.get(position);
        TextView textView = holder.ingredientTitle;
        textView.setText(ingredient);
        //Log.d(TAG,ingredient);
        textView = holder.childQty;
        textView.setText(listIngredientChild.get(ingredient).get(0));
        //Log.d(TAG,"Quantity : " +listIngredientChild.get(ingredient).get(0));
        textView = holder.childMeasure;
        textView.setText(listIngredientChild.get(ingredient).get(1));
        //Log.d(TAG,"Measure : "+listIngredientChild.get(ingredient).get(1));
        if (position == expandedPosition) {
            holder.lLExpandArea.setVisibility(View.VISIBLE);
        } else {
            holder.lLExpandArea.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return listIngredientHeader.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public void onClick(View view) {

        Log.d(TAG, "CLICKED");
        IngredientViewHolder holder = (IngredientViewHolder) view.getTag();
        // Check for an expanded view, collapse if you find one
        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            notifyItemChanged(prev);
        }
        // Set the current position to "expanded"
        expandedPosition = holder.getPosition();
        notifyItemChanged(expandedPosition);

    }



}
