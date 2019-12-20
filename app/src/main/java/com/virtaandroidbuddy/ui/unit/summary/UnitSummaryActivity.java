package com.virtaandroidbuddy.ui.unit.summary;

import androidx.fragment.app.Fragment;

import com.virtaandroidbuddy.common.SingleFragmentActivity;

public class UnitSummaryActivity extends SingleFragmentActivity {

    public static final String UNIT_SUMMARY_BUNDLE_KEY = "UNIT_SUMMARY_BUNDLE_KEY";

    @Override
    protected Fragment getFragment() {
        if (getIntent() != null) {
            return UnitSummaryFragment.newInstance(getIntent().getBundleExtra(UNIT_SUMMARY_BUNDLE_KEY));
        }
        throw new IllegalStateException("getIntent cannot be null");
    }
}

