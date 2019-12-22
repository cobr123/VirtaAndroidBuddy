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

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.common.PresenterFragment;
import com.virtaandroidbuddy.common.RefreshOwner;
import com.virtaandroidbuddy.common.Refreshable;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.ui.login.LoginActivity;

public class UnitSummaryFragment extends PresenterFragment<UnitSummaryPresenter> implements UnitSummaryView, Refreshable {

    public static final String UNIT_ID_KEY = "UNIT_ID_KEY";

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mUnitSummaryView;
    private String mUnitId;
    private Storage mStorage;
    private UnitSummaryPresenter mPresenter;

    private TextView mUnitSummaryId;
    private TextView mUnitSummaryName;

    public static UnitSummaryFragment newInstance(Bundle args) {
        UnitSummaryFragment fragment = new UnitSummaryFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStorage = context instanceof Storage.StorageOwner ? ((Storage.StorageOwner) context).obtainStorage() : null;
        mRefreshOwner = context instanceof RefreshOwner ? (RefreshOwner) context : null;
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

        mUnitSummaryId = view.findViewById(R.id.tv_id);
        mUnitSummaryName = view.findViewById(R.id.tv_name);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mUnitId = getArguments().getString(UNIT_ID_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUnitId);
        }

        mPresenter = new UnitSummaryPresenter(this, mStorage);
        mUnitSummaryView.setVisibility(View.VISIBLE);

        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        try {
            mPresenter.getUnitSummary(getActivity(), mUnitId);
        } catch (Exception e) {
            Log.e("VirtonomicaApi", e.toString());
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
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showLoading() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideLoading() {
        mRefreshOwner.setRefreshState(false);
    }


    @Override
    public void showUnitSummary(UnitSummaryJson unitSummaryJson) {
        mErrorView.setVisibility(View.GONE);
        mUnitSummaryView.setVisibility(View.VISIBLE);
        bind(unitSummaryJson);
    }

    @Override
    public void showError(Throwable throwable) {
        mErrorView.setVisibility(View.VISIBLE);
        mUnitSummaryView.setVisibility(View.GONE);
        Log.e("VirtonomicaApi", throwable.toString(), throwable);
        //showLoginWindow(throwable.toString());
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
