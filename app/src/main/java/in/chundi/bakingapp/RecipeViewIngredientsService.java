package in.chundi.bakingapp;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;


/**
 * Created by userhk on 14/07/17.
 * Modified codee from
 * http://www.worldbestlearningcenter.com/answers/1524/using-listview-in-home-screen-widget-in-android
 */

public class RecipeViewIngredientsService extends RemoteViewsService {
    public String TAG = RecipeViewIngredientsRemoteViewsFactory.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "Inside " + TAG);
        return new RecipeViewIngredientsRemoteViewsFactory(getApplicationContext(), intent);
    }
}
