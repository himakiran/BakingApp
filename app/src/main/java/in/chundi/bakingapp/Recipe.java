package in.chundi.bakingapp;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONArray;

/**
 * Created by userhk on 02/07/17.
 * This class implements a recipe object
 */

public class Recipe {

    private String TAG = Recipe.class.getSimpleName();

    private int recipeId;
    private String recipeName;
    private int recipeNoOfIngredients;
    private JSONArray recipeIngredients;
    private int recipeNoOfSteps;
    private JSONArray recipeSteps;
    private int recipeServings;
    @Nullable
    private String recipeImg;


    private Context mContext;

    public Recipe(Context context) {
        mContext = context;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeNoOfIngredients() {
        return recipeNoOfIngredients;
    }

    public void setRecipeNoOfIngredients(int recipeNoOfIngredients) {
        this.recipeNoOfIngredients = recipeNoOfIngredients;
    }

    public JSONArray getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(JSONArray recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public int getRecipeNoOfSteps() {
        return recipeNoOfSteps;
    }

    public void setRecipeNoOfSteps(int recipeNoOfSteps) {
        this.recipeNoOfSteps = recipeNoOfSteps;
    }

    public JSONArray getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(JSONArray recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public int getRecipeServings() {
        return recipeServings;
    }

    public void setRecipeServings(int recipeServings) {
        this.recipeServings = recipeServings;
    }

    @Nullable
    public String getRecipeImg() {
        return recipeImg;
    }

    public void setRecipeImg(@Nullable String recipeImg) {
        this.recipeImg = recipeImg;
    }
}
