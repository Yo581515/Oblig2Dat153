package com.example.oblig2dat153.activities;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.util.Log;
import android.widget.RadioButton;

import androidx.databinding.DataBindingUtil;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.activities.QuizActivity;
import com.example.oblig2dat153.databinding.ActivityQuizBinding;
import com.example.oblig2dat153.model.Image;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityScenarioRule =
            new ActivityScenarioRule<>(QuizActivity.class);


    @Test
    public void testScoreIncreasesOnCorrectAnswer() {

        // Get a reference to the QuizActivity
        activityScenarioRule.getScenario().onActivity(activity -> {
            // Use the QuizActivity instance to set up the test
            ActivityQuizBinding binding = activity.activityQuizBinding;
            int initialScore = Integer.parseInt(binding.scoreNumber.getText().toString());

            // Get the Image objects from the data binding
            Image correctAnswer = binding.getImage();

            assertNotNull(correctAnswer);

            // Determine which RadioButton is the correct answer
            RadioButton correctRadioButton = null;
            if (correctAnswer.getImageName().equals(binding.radio1.getText().toString())) {
                correctRadioButton = binding.radio1;
            } else if (correctAnswer.getImageName().equals(binding.radio2.getText().toString())) {
                correctRadioButton = binding.radio2;
            } else if (correctAnswer.getImageName().equals(binding.radio3.getText().toString())) {
                correctRadioButton = binding.radio3;
            }

            assertNotNull(correctRadioButton);

            Log.d("Yosafe : ","correct answer : "+correctRadioButton.getText().toString());

            // Click the correct RadioButton
            correctRadioButton.performClick();

            int updatedScore = Integer.parseInt(binding.scoreNumber.getText().toString());
            // Check that the score has increased by 1
            assertEquals(initialScore + 1, updatedScore);
        });
    }

    @Test
    public void testScoreNotIncreasesOnWrongAnswer() {
        // Get a reference to the QuizActivity
        activityScenarioRule.getScenario().onActivity(activity -> {
            // Use the QuizActivity instance to set up the test
            ActivityQuizBinding binding = activity.activityQuizBinding;
            int initialScore = Integer.parseInt(binding.scoreNumber.getText().toString());

            // Get the Image objects from the data binding
            Image correctAnswer = binding.getImage();

            assertNotNull(correctAnswer);

            // Determine which RadioButton is the correct answer
            RadioButton wrongRadioButton = null;
            if (!correctAnswer.getImageName().equals(binding.radio1.getText().toString())) {
                wrongRadioButton = binding.radio1;
            } else if (!correctAnswer.getImageName().equals(binding.radio2.getText().toString())) {
                wrongRadioButton = binding.radio2;
            } else if (!correctAnswer.getImageName().equals(binding.radio3.getText().toString())) {
                wrongRadioButton = binding.radio3;
            }

            assertNotNull(wrongRadioButton);

            Log.d("Yosafe : ","wrong answer : "+wrongRadioButton.getText().toString());

            // Click the correct RadioButton
            wrongRadioButton.performClick();

            int updatedScore = Integer.parseInt(binding.scoreNumber.getText().toString());
            // Check that the score has increased by 1
            assertEquals(initialScore , updatedScore);
        });
    }
}
