package com.example.oblig2dat153.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private static final String EASY_MODE = "EASY MODE";
    private static final String HARD_MODE = "HARD MODE";
    private static final int EASY_MODE_COLOR = Color.BLUE;
    private static final int HARD_MODE_COLOR = Color.RED;

    Switch mode_switcher;

    ActivityMainBinding activityMainBinding;

    MainActivityClickHandlers clickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandler(clickHandlers);
        mode_switcher = findViewById(R.id.mode_switcher);
        DisplaySavedText();

        mode_switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayAndSaveText();
            }
        });

    }


    // handling mode state
    private void DisplaySavedText() {
        // Retrieving the value from SharedPref
        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sharedPreferences.getString("mode_switcher", " ");
        System.out.print(s1);
        boolean isTextEmpty = s1 != null && !s1.trim().equals("");
        if (isTextEmpty) {
            if (s1.equals(MainActivity.HARD_MODE)) {
                mode_switcher.setTextColor(HARD_MODE_COLOR);
                mode_switcher.setText(MainActivity.HARD_MODE);
                mode_switcher.setChecked(true);
            } else {
                mode_switcher.setTextColor(EASY_MODE_COLOR);
                mode_switcher.setText(MainActivity.EASY_MODE);
                mode_switcher.setChecked(false);
            }
        } else {
            mode_switcher.setTextColor(EASY_MODE_COLOR);
            mode_switcher.setText(MainActivity.EASY_MODE);
            mode_switcher.setChecked(false);
        }
    }

    private void DisplayAndSaveText() {
        if (mode_switcher.isChecked()) {
            mode_switcher.setTextColor(HARD_MODE_COLOR);
            mode_switcher.setText(MainActivity.HARD_MODE);
        } else {
            mode_switcher.setTextColor(EASY_MODE_COLOR);
            mode_switcher.setText(MainActivity.EASY_MODE);
        }
        // Saving the Text into SharedPreff
        SharedPreferences sharedPreferences =
                getSharedPreferences("MySharedPref", MODE_PRIVATE);
        // Writing data to shared pref
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mode_switcher", mode_switcher.getText().toString());
        editor.commit();
    }


    public class MainActivityClickHandlers {
        public void onPlayQuizeBtnClicked(View view) {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("mode", mode_switcher.getText().toString());
            startActivity(intent);
        }

        public void onImageListBtnClicked(View view) {
            Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
            startActivity(intent);
        }
    }


}
