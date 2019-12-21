package com.virtaandroidbuddy.ui.unitlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.common.PresenterFragment;
import com.virtaandroidbuddy.common.RefreshOwner;
import com.virtaandroidbuddy.common.Refreshable;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.ui.login.LoginActivity;
import com.virtaandroidbuddy.ui.unit.summary.UnitSummaryActivity;
import com.virtaandroidbuddy.ui.unit.summary.UnitSummaryFragment;


public class UnitListFragment extends PresenterFragment<UnitListPresenter> implements UnitListView, Refreshable, UnitListAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private Storage mStorage;
    private UnitListAdapter mUnitListAdapter;
    private UnitListPresenter mPresenter;


    public static UnitListFragment newInstance() {
        return new UnitListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            mRefreshOwner = ((RefreshOwner) context);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_unit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.unitlist_title);
        }

        mPresenter = new UnitListPresenter(this, mStorage);
        mUnitListAdapter = new UnitListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mUnitListAdapter);

        onRefreshData();
    }

    @Override
    public void onItemClick(UnitListDataJson unitListDataJson) {
        mPresenter.openUnitSummary(unitListDataJson);
    }

    @Override
    protected UnitListPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void onRefreshData() {
        try {
            mPresenter.getUnitlist(getActivity());
        } catch (Exception e) {
            Log.e("VirtonomicaApi", e.toString());
            showLoginWindow(null);
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

    @Override
    public void showUnitList(UnitListJson unitListJson) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mUnitListAdapter.addData(unitListJson.getData(), true);
    }

    @Override
    public void openUnitSummary(UnitListDataJson unitListDataJson) {
        Intent intent = new Intent(getActivity(), UnitSummaryActivity.class);
        Bundle args = new Bundle();
        args.putString(UnitSummaryFragment.UNIT_ID_KEY, unitListDataJson.getId());
        intent.putExtra(UnitSummaryActivity.UNIT_SUMMARY_BUNDLE_KEY, args);
        startActivity(intent);
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
    public void showError(Throwable throwable) {
//        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        Log.e("VirtonomicaApi", throwable.toString(), throwable);
        showLoginWindow(throwable.toString());
    }
}