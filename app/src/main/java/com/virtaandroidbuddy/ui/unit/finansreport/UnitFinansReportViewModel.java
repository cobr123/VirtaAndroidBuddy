package com.virtaandroidbuddy.ui.unit.finansreport;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnitFinansReportViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UnitFinansReportViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is UnitFinansReport fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}