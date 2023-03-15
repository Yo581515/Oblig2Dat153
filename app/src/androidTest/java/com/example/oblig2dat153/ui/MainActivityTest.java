package com.example.oblig2dat153.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testPlayQuizButton() {
        // Click on the "Play Quiz" button
        onView(ViewMatchers.withId(R.id.play_quiz_btn)).perform(click());
        // Verify that QuizeActivity is launched
        onView(withId(R.id.activity_quiz)).check(matches(isDisplayed()));
    }

    @Test
    public void testExitingQuizReturnsToMainActivity() {
        onView(ViewMatchers.withId(R.id.play_quiz_btn)).perform(click());

        // Press the back button to exit the QuizActivity
        onView(isRoot()).perform(ViewActions.pressBack());

        // Check that the MainActivity is now in the foreground
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }
}