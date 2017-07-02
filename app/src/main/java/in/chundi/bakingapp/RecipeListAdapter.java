package in.chundi.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by userhk on 01/07/17.
 * This adapter ties the recycler view (activity_recipe_list.xml ) to recipe images
 * each of which will fill up the image view in activity_recipe_list_item.xml to the adapter
 * The adpater shall take a json object and retrieve images/thumbnails to show the recipe
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();
    private ArrayList<Recipe> recipeArrayList;
    private Context mContext;
    private Recipe r;


    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes) {
        mContext = context;
        recipeArrayList = recipes;

    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_recipe_list_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        //Log.d(TAG, "# " + position);
        r = recipeArrayList.get(position);
        //Log.d(TAG,r.getRecipeName() + " and Its Image is :" + r.getRecipeImg());
        ImageView imageView = holder.listRecipeImageView;
        if (r.getRecipeImg() == "")
            imageView.setImageResource(R.drawable.welcome);
        // to implement else where the image url is taken and displayed
        TextView textView = holder.listRecipeTextView;
        textView.setText(r.getRecipeName());

    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private Context getContext() {
        return mContext;
    }

}
