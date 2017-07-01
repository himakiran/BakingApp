package in.chundi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.R.id.message;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * Called when the user taps the "Welcome to Baking Nirvana button button
     */
    public void showBakeRecipes() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}
