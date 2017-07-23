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
 * Created by userhk on 23/07/17.
 */

public class RecipeIngredientListAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private Context mContext;
    private ArrayList<String> listIngredientHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listIngredientChild;
    private String TAG = RecipeIngredientListAdapter.class.getSimpleName();
    private String ingredient;

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

        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        ingredient = listIngredientHeader.get(position);
        TextView textView = holder.recipeTitle;
        textView.setText(ingredient);


    }

    @Override
    public int getItemCount() {
        return listIngredientHeader.size();
    }
}
