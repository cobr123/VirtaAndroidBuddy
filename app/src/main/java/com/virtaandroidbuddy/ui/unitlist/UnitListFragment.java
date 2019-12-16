package com.virtaandroidbuddy.ui.unitlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.api.ApiUtils;
import com.virtaandroidbuddy.api.VirtonomicaApi;
import com.virtaandroidbuddy.api.model.UnitListDataJson;
import com.virtaandroidbuddy.api.model.UnitListJson;
import com.virtaandroidbuddy.database.VirtonomicaDao;
import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.database.model.Unit;
import com.virtaandroidbuddy.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;


public class UnitListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mUnitListRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final UnitListAdapter mUnitListAdapter = new UnitListAdapter();
    private View mErrorView;

    public static UnitListFragment newInstance() {
        return new UnitListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_unit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnitListRecyclerView = view.findViewById(R.id.recycler);
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.error_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUnitListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUnitListRecyclerView.setAdapter(mUnitListAdapter);
        refreshData();
    }

    private void refreshData() {
        try {
            final OkHttpClient client = ApiUtils.getClient(getActivity());
            final VirtonomicaApi api = ApiUtils.getApi(client, getString(R.string.base_url));
            final VirtonomicaDao virtonomicaDao = ((AppDelegate) getActivity().getApplicationContext()).getVirtonomicaDatabase().getVirtonomicaDao();
            final Session session = virtonomicaDao.getSession();

            api.getUnitList(session.getRealm(), session.getCompanyId())
                    .subscribeOn(Schedulers.io())
                    .doOnSuccess(unitListJson -> {
                        final List<UnitListDataJson> data = unitListJson.getData();
                        final List<Unit> unitList = new ArrayList<>(data.size());
                        for (UnitListDataJson item : data) {
                            final Unit unit = new Unit();
                            unit.setCompanyId(session.getCompanyId());
                            unit.setId(item.getId());
                            unit.setRealm(session.getRealm());
                            unit.setName(item.getName());
                            unitList.add(unit);
                        }
                        virtonomicaDao.insertUnits(unitList);
                    })
                    .onErrorReturn(throwable -> {
                        if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                            final List<Unit> unitList = virtonomicaDao.getUnitList(session.getRealm(), session.getCompanyId());
                            final List<UnitListDataJson> data = new ArrayList<>(unitList.size());
                            for (Unit unit : unitList) {
                                final UnitListDataJson dataItem = new UnitListDataJson();
                                dataItem.setId(unit.getId());
                                dataItem.setName(unit.getName());
                                data.add(dataItem);
                            }
                            final UnitListJson unitListJson = new UnitListJson();
                            unitListJson.setData(data);
                            return unitListJson;
                        } else {
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> mSwipeRefreshLayout.setRefreshing(true))
                    .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(unitListJson -> {
                                mErrorView.setVisibility(View.GONE);
                                mUnitListRecyclerView.setVisibility(View.VISIBLE);
                                mUnitListAdapter.addData(unitListJson.getData(), true);
                            },
                            throwable -> {
                                mErrorView.setVisibility(View.VISIBLE);
                                mUnitListRecyclerView.setVisibility(View.GONE);
                                Log.e("VirtonomicaApi", throwable.toString(), throwable);
                                virtonomicaDao.deleteSession(session);
                                showLoginWindow(throwable.toString());
                            });
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
                refreshData();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(this::refreshData);
    }
}