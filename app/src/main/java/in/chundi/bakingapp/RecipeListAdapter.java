package in.chundi.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by userhk on 01/07/17.
 * Code used and modified from https://guides.codepath.com/android/using-the-recyclerview
 * This adapter ties the recycler view (fragment_recipe_master_listto recipe images
 * each of which will fill up the image view in fragment_recipe_master_list_item.xml to the adapter
 * The adpater shall take a json object and retrieve images/thumbnails to show the recipe
 *
 * using PICASSO Library to load images
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

        View view = inflater.inflate(R.layout.fragment_recipe_master_list_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        //Log.d(TAG, "# " + position);
        r = recipeArrayList.get(position);
        //Log.d(TAG, r.getRecipeName() + " and Its Image is :" + r.getRecipeImg());
        ImageView imageView = holder.listRecipeImageView;
        if (r.getRecipeImg().equals(""))
            imageView.setImageResource(R.drawable.welcome);
            // we use picasso library to load images from the image url
        else {
            Picasso.with(getContext()).load(r.getRecipeImg()).fit().into(imageView);
        }

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
