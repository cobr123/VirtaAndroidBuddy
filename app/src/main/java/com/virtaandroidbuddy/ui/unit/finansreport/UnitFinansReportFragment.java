package com.virtaandroidbuddy.ui.unit.finansreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.virtaandroidbuddy.R;

public class UnitFinansReportFragment extends Fragment {

    private UnitFinansReportViewModel mUnitFinansReportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mUnitFinansReportViewModel =
                ViewModelProviders.of(this).get(UnitFinansReportViewModel.class);
        View root = inflater.inflate(R.layout.fr_unit_finans_report, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        mUnitFinansReportViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}