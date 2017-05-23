package com.example.eric.mymovies;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.example.eric.mymovies.search.SearchActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.eric.mymovies.TestUtils.childAtPosition;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public ActivityTestRule<SearchActivity> mActivityTestRule = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void searchDefaultTest() {
        Matcher<View> firstListItem = childAtPosition(withId(R.id.recyclerview), 0);
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(firstListItem, 1), isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(allOf(withId(R.id.image_poster), childAtPosition(firstListItem, 0),
                isDisplayed()));

        imageView.check(matches(isDisplayed()));

        ViewInteraction textTitleView = onView(allOf(withId(R.id.text_title), childAtPosition(childAtPosition
                        (firstListItem, 1), 0),
                isDisplayed()));
        textTitleView.check(matches(isDisplayed()));
    }

    @Test
    public void searchNoResultTest() {
        ViewInteraction searchAutoComplete11 = onView(
                allOf(withId(R.id.search_src_text), withParent(allOf(withId(R.id.search_plate), withParent(withId(R
                        .id.search_edit_frame)))), isDisplayed()));
        searchAutoComplete11.perform(replaceText("fweffafw"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction messageNoResultView = onView(allOf(withId(R.id.message_no_result), isDisplayed()));
        messageNoResultView.check(matches(isDisplayed()));
    }

    @Test
    public void searchSmallTest() {
        ViewInteraction searchAutoComplete11 = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete11.perform(replaceText("small"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Matcher<View> firstListItem = childAtPosition(withId(R.id.recyclerview), 0);
        ViewInteraction imageView = onView(allOf(withId(R.id.image_poster), childAtPosition(firstListItem, 0),
                isDisplayed()));

        imageView.check(matches(isDisplayed()));

        ViewInteraction textTitleView = onView(allOf(withId(R.id.text_title), childAtPosition(childAtPosition
                        (firstListItem, 1), 0),
                isDisplayed()));
        textTitleView.check(matches(isDisplayed()));
    }
}
