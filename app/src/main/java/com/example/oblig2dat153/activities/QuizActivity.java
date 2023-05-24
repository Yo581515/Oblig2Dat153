package com.example.oblig2dat153.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.databinding.ActivityQuizBinding;
import com.example.oblig2dat153.model.Image;
import com.example.oblig2dat153.viewmodel.QuizActivityViewModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity {

    public ActivityQuizBinding activityQuizBinding;
    QuizActivityViewModel quizActivityViewModel;

    QuizActivityClickHandlers clickHandlers;

    List<Image> imageList;

    int currentImageIndex = 0;

    Thread t;

    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activityQuizBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        quizActivityViewModel = new ViewModelProvider(this).get(QuizActivityViewModel.class);
        clickHandlers = new QuizActivityClickHandlers();
        activityQuizBinding.setClickHandler(clickHandlers);
        Image layoutImage = new Image();
        activityQuizBinding.setImage(layoutImage);


    /*    quizActivityViewModel.getAllImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageList = (ArrayList<Image>) images;
                Collections.shuffle(imageList);
                if (!imageList.isEmpty()) {
                    displayQuestion(imageList.get(currentImageIndex));
                }
            }
        });*/


        t = new Thread(new Runnable() {
            @Override
            public void run() {
                imageList = quizActivityViewModel.imageRepository.imageDAO.getAllImages2();
                Collections.shuffle(imageList);
                if (!imageList.isEmpty()) {
                    displayQuestion(imageList.get(currentImageIndex));
                }
            }
        });

        t.start();

       /* try {
            t.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        Toast.makeText(this, mode, Toast.LENGTH_SHORT).show();
    }

    private void displayQuestion(Image image) {
        activityQuizBinding.setImage(image);
        List<Image> randomImageList = find2MoreRandomImages(imageList, image);
        activityQuizBinding.setImage0(randomImageList.get(0));
        activityQuizBinding.setImage1(randomImageList.get(1));
        activityQuizBinding.setImage2(randomImageList.get(2));
    }

    private List<Image> find2MoreRandomImages(List<Image> imageList, Image image) {

        List<Image> filteredList = imageList.stream()
                .filter(i -> i.getId() != image.getId())
                .collect(Collectors.toList());
        Collections.shuffle(filteredList);
        filteredList = filteredList.subList(0, 2);
        filteredList.add(image);
        Collections.shuffle(filteredList);
        return filteredList;
    }

    public class QuizActivityClickHandlers {

        public void onNameChoden(RadioGroup group, int checkedId) {
            int selectedImageIndex = -1;

            switch (checkedId) {
                case R.id.radio_1:
                    selectedImageIndex = 0;
                    break;
                case R.id.radio_2:
                    selectedImageIndex = 1;
                    break;
                case R.id.radio_3:
                    selectedImageIndex = 2;
                    break;
            }
            if (selectedImageIndex >= 0) {
                Image selectedImage = null;
                if (selectedImageIndex == 0) {
                    selectedImage = activityQuizBinding.getImage0();
                } else if (selectedImageIndex == 1) {
                    selectedImage = activityQuizBinding.getImage1();
                } else if (selectedImageIndex == 2) {
                    selectedImage = activityQuizBinding.getImage2();
                }
                if (selectedImage.getId() == imageList.get(currentImageIndex).getId()) {
                    // Correct answer
                    int currentScore = Integer.parseInt(activityQuizBinding.scoreNumber.getText().toString());
                    activityQuizBinding.scoreNumber.setText(String.valueOf(currentScore + 1));
                }
                // Incorrect answer
                int currentTries = Integer.parseInt(activityQuizBinding.triesNumber.getText().toString());
                activityQuizBinding.triesNumber.setText(String.valueOf(currentTries + 1));

                // Move to next question
                currentImageIndex++;
                if (currentImageIndex >= imageList.size()) {
                    currentImageIndex = 0;
                    Collections.shuffle(imageList);
                }
                displayQuestion(imageList.get(currentImageIndex));
                // Clear radio button selection
                group.clearCheck();
            }
        }


    }
}