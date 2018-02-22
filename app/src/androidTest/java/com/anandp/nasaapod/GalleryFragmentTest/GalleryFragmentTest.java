package com.anandp.nasaapod.GalleryFragmentTest;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import com.anandp.nasaapod.R;
import com.anandp.nasaapod.data.model.GalleryItem;
import com.anandp.nasaapod.ui.HomeActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Single;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

/**
 * Created by @iamBedant on 20/02/18.
 */

public class GalleryFragmentTest {

    public final TestComponentRule component =
            new TestComponentRule(getTargetContext());

    public final IntentsTestRule<HomeActivity> main =
            new IntentsTestRule<>(HomeActivity.class, false, false);


    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    public void shouldBeAbleToLaunchLoginScreen() {
        launchActivity();
//        intended(hasComponent(HomeActivity.class.getName()));
//        Intents.intended(IntentMatchers.hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)));

        //TODO: figure out why Intents.intended not working, This is a hack
        onView(withId(R.id.home_root)).check(matches(isDisplayed()));
    }

    private void launchActivity() {
        Intent intent = new Intent(component.getContext(), HomeActivity.class);
        main.launchActivity(intent);
    }


    /**
     * Should show recyclerview and should not show error views.
     */
    @Test
    public void shouldShowRecyclerview() {
        when(component.getRepository().getApodForMonth(null)).thenReturn(Single.just(getGalleryItems()));
        launchActivity();
        onView(withId(R.id.retry_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(1));

    }

    /**
     * should not show recyclerview and should show error views.
     */
    @Test
    public void shouldShowErrorViewIfApiThrowsError(){
        when(component.getRepository().getApodForMonth(null)).thenReturn(Single.error(new Exception("this is an exception")));
        launchActivity();
        onView(withId(R.id.retry_button)).check(matches(isDisplayed()));
        onView(withId(R.id.error_tv)).check(matches(isDisplayed()));
        onView(withId(R.id.error_tv)).check(matches(withText(containsString("this is an exception"))));
        onView(ViewMatchers.withId(R.id.recycler_view)).check(matches(not(isDisplayed())));
    }

    /**
     * Create Dummy data for test
     */

    public List<GalleryItem> getGalleryItems() {
        List<GalleryItem> galleryList = new ArrayList<>();
        galleryList.add(GalleryItem.create("2018-01-27", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.", "https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1", "The Spider and The Fly", "https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg"));
        galleryList.add(GalleryItem.create("2018-01-29", "For scale, the more compact NGC 1931 (Fly) is about 10 light-years across.", "https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_1000.jpg", "image", "v1", "The Spider and The Fly", "https://apod.nasa.gov/apod/image/1801/SpiderandFly_Morris_960.jpg"));
        return galleryList;
    }

}
