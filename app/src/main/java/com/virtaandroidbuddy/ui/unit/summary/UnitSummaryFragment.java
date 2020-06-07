package com.virtaandroidbuddy.ui.unit.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.common.PresenterFragment;
import com.virtaandroidbuddy.common.Refreshable;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.GameUpdateHappeningNowException;
import com.virtaandroidbuddy.data.api.model.UnitClassKindEnum;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.ui.login.LoginActivity;
import com.virtaandroidbuddy.ui.unit.UnitMainActivity;

public class UnitSummaryFragment extends PresenterFragment<UnitSummaryPresenter> implements UnitSummaryView, Refreshable {

    private static final String TAG = UnitSummaryFragment.class.getSimpleName();

    public static final String UNIT_ID_KEY = "UNIT_ID_KEY";
    public static final String UNIT_CLASS_NAME_KEY = "UNIT_CLASS_NAME_KEY";
    public static final String UNIT_CLASS_KIND_KEY = "UNIT_CLASS_KIND_KEY";

    private View mErrorView;
    private View mUnitSummaryView;
    private View mWorkshopUnitSummaryView;
    private View mShopUnitSummaryView;
    private String mUnitId;
    private UnitClassKindEnum mUnitClassKind;
    private Storage mStorage;
    private UnitSummaryPresenter mPresenter;

    private TextView mUnitSummaryId;
    private TextView mUnitSummaryName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStorage = ((AppDelegate) getActivity().getApplicationContext()).getStorage();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((UnitMainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_unit_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mUnitSummaryView = view.findViewById(R.id.view_unit_summary);
        mWorkshopUnitSummaryView = view.findViewById(R.id.view_unit_summary_workshop);
        mShopUnitSummaryView = view.findViewById(R.id.view_unit_summary_shop);

        try {
            mUnitClassKind = UnitClassKindEnum.valueOf(getActivity().getIntent().getStringExtra(UNIT_CLASS_KIND_KEY));
        } catch (final Exception e) {
            mUnitClassKind = UnitClassKindEnum.unknown;
            Log.e(TAG, e.toString());
        }
        mUnitSummaryId = getUnitSummaryView().findViewById(R.id.tv_id);
        mUnitSummaryName = getUnitSummaryView().findViewById(R.id.tv_name);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUnitId = getActivity().getIntent().getStringExtra(UNIT_ID_KEY);

        final String unitClassName = getActivity().getIntent().getStringExtra(UNIT_CLASS_NAME_KEY);
        ((UnitMainActivity) getActivity()).getSupportActionBar().setTitle(unitClassName);

        mPresenter = new UnitSummaryPresenter(this, mStorage);

        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        try {
            mPresenter.getUnitSummary(getActivity(), mUnitId);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showLoginWindow(null);
        }
    }

    private void bind(UnitSummaryJson unitSummaryJson) {
        mUnitSummaryId.setText(unitSummaryJson.getId());
        mUnitSummaryName.setText(unitSummaryJson.getName());
    }

    @Override
    protected UnitSummaryPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDetach() {
        mStorage = null;
        super.onDetach();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private View getUnitSummaryView() {
        switch (mUnitClassKind) {
            case workshop:
                return mWorkshopUnitSummaryView;
            case shop:
                return mShopUnitSummaryView;
            default:
                return mUnitSummaryView;
        }
    }

    @Override
    public void showUnitSummary(UnitSummaryJson unitSummaryJson) {
        mErrorView.setVisibility(View.GONE);
        getUnitSummaryView().setVisibility(View.VISIBLE);
        bind(unitSummaryJson);
    }

    @Override
    public void showError(Throwable throwable) {
        mErrorView.setVisibility(View.VISIBLE);
        getUnitSummaryView().setVisibility(View.GONE);
        Log.e(TAG, throwable.toString(), throwable);
        //showLoginWindow(throwable.toString());
        if (throwable instanceof GameUpdateHappeningNowException) {
            showLoginWindow(getString(R.string.game_update_happening_now));
        }
    }

    private final int REQUEST_CODE_LOGIN = 1;

    private void showLoginWindow(final String error) {
        final Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(LoginActivity.ERROR_TEXT_PROP_NAME, error);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                onRefreshData();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
