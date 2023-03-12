package com.example.oblig2dat153.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.databinding.ActivityMainBinding;
import com.example.oblig2dat153.viewmodel.ImageListActivityViewModel;
import com.example.oblig2dat153.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding activityMainBinding;
    MainActivityViewModel mainActivityViewModel;

    MainActivityClickHandlers clickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        clickHandlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandler(clickHandlers);
        activityMainBinding.setMode(mainActivityViewModel.getMode().getValue());

        mainActivityViewModel.getMode().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                activityMainBinding.setMode(mainActivityViewModel.getMode().getValue());
            }
        });
    }

    public class MainActivityClickHandlers {


        public void onPlayQuizeBtnClicked(View view) {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);

        }

        public void onImageListBtnClicked(View view) {
            Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
            startActivity(intent);
        }

        public void onModeSwitchClicked(View view) {
            Switch modeSwitch = (Switch) view;
            if (modeSwitch.isChecked()) {
                mainActivityViewModel.setMode(true);
            } else {
                mainActivityViewModel.setMode(false);
            }
        }

    }
}
