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
import com.virtaandroidbuddy.api.model.UnitListJson;
import com.virtaandroidbuddy.database.VirtonomicaDao;
import com.virtaandroidbuddy.database.model.Session;
import com.virtaandroidbuddy.ui.login.LoginActivity;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
            final String realm = ApiUtils.getRealm(getActivity());
            final VirtonomicaApi api = ApiUtils.getApi(client, getString(R.string.base_url));
            final VirtonomicaDao virtonomicaDao = ((AppDelegate) getActivity().getApplicationContext()).getVirtonomicaDatabase().getVirtonomicaDao();
            final Session session = virtonomicaDao.getSession();
            api.getUnitList(realm, session.getCompanyId()).enqueue(new Callback<List<UnitListJson>>() {
                @Override
                public void onResponse(Call<List<UnitListJson>> call, Response<List<UnitListJson>> response) {
                    Log.d("VirtonomicaApi", "onResponse");
                    if (response != null && response.body() != null && response.code() == 200) {
                        showData(response.body());
                    } else {
                        Log.d("VirtonomicaApi", "response = " + response);
                        showLoginWindow();
                    }
                }

                @Override
                public void onFailure(Call<List<UnitListJson>> call, Throwable t) {
                    Log.d("VirtonomicaApi", t.toString());
                    showLoginWindow();
                }
            });
        } catch (Exception e) {
            Log.d("VirtonomicaApi", e.toString());
            showLoginWindow();
        }
    }

    private void showLoginWindow() {
        final Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshData();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mUnitListRecyclerView.setVisibility(View.GONE);
    }

    private void showData(List<UnitListJson> data) {
        mUnitListAdapter.addData(data, true);
        mErrorView.setVisibility(View.GONE);
        mUnitListRecyclerView.setVisibility(View.VISIBLE);
    }
}