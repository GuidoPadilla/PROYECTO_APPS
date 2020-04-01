package com.example.proyecto.ui.home;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto.databinding.FragmentHomeBinding;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public void setmText(String mText){
        this.mText.setValue(mText);
    }

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");



    }
    public LiveData<String> getText() {
        return mText;
    }
}