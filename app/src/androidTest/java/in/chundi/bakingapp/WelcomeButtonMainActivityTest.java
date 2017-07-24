package in.chundi.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by userhk on 19/07/17.
 */
@RunWith(AndroidJUnit4.class)
public class WelcomeButtonMainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickWelcomeButonLoadsRecipes() {
        // check that welcome button exists
        onView((withId(R.id.button))).check(matches(withText(getInstrumentation().getTargetContext().getString(R.string.WelcomeMessage))));
        // clicking the button should launch recycler view of recipes
        onView((withId(R.id.button))).perform(click());
        // check if recycler view is loaded
        onView((withId(R.id.recipes))).check(matches(isDisplayed()));
    }
}
