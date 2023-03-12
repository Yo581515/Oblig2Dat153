package com.example.oblig2dat153.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<Boolean> mode = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getMode() {
        return mode;
    }

    public void setMode(boolean mode_boolean) {
        this.mode.setValue(mode_boolean);
    }
}
