package com.virtaandroidbuddy.ui.unit.summary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.common.RefreshOwner;
import com.virtaandroidbuddy.common.Refreshable;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.UnitSummary;
import com.virtaandroidbuddy.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UnitSummaryFragment extends Fragment implements Refreshable {

    public static final String UNIT_ID_KEY = "UNIT_ID_KEY";

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mUnitSummaryView;
    private String mUnitId;
    private Storage mStorage;
    private Disposable mDisposable;

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

        mUnitSummaryView.setVisibility(View.VISIBLE);

        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        getUnitSummary();
    }

    private void getUnitSummary() {
        final Session session = mStorage.getSession();
        mDisposable = ApiUtils.getApiService(getContext()).getUnitSummary(session.getRealm(), mUnitId)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUnitSummary(response, session))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUnitSummary(mUnitId, session) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefreshOwner.setRefreshState(true))
                .doFinally(() -> mRefreshOwner.setRefreshState(false))
                .subscribe(
                        response -> {
                            mErrorView.setVisibility(View.GONE);
                            mUnitSummaryView.setVisibility(View.VISIBLE);
                            bind(response);
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mUnitSummaryView.setVisibility(View.GONE);
                        });
    }

    private void bind(UnitSummaryJson unitSummaryJson) {
        mUnitSummaryId.setText(unitSummaryJson.getId());
        mUnitSummaryName.setText(unitSummaryJson.getName());
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDetach();
    }
}
