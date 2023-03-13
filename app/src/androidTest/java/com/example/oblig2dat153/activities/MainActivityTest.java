package com.example.oblig2dat153.activities;


import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.activities.MainActivity;

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
        onView(withId(R.id.q_txt)).check(matches(isDisplayed()));
    }
}