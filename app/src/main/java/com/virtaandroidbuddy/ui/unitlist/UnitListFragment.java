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

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.api.ApiUtils;
import com.virtaandroidbuddy.api.VirtonomicaApi;
import com.virtaandroidbuddy.api.model.Company;
import com.virtaandroidbuddy.api.model.Unit;
import com.virtaandroidbuddy.ui.login.LoginActivity;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UnitListFragment extends Fragment {

    private RecyclerView mUnitListRecyclerView;
    private final UnitListAdapter mUnitListAdapter = new UnitListAdapter();

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUnitListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUnitListRecyclerView.setAdapter(mUnitListAdapter);
        //mUnitListAdapter.addData(Arrays.asList(new Unit("1","name 1"), new Unit("2","name 2")));

        try {
            final OkHttpClient client = ApiUtils.getClient(getActivity());
            final String realm = ApiUtils.getRealm(getActivity());
            final VirtonomicaApi api = ApiUtils.getApi(client, getString(R.string.base_url));

            api.getCompanyInfo(realm).enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    final Company company = response.body();
                    api.getUnitList(realm, company.getId()).enqueue(new Callback<List<Unit>>() {
                        @Override
                        public void onResponse(Call<List<Unit>> call, Response<List<Unit>> response) {
                            Log.d("VirtonomicaApi", "onResponse");
                            mUnitListAdapter.addData(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<Unit>> call, Throwable t) {
                            Log.d("VirtonomicaApi", t.toString());
                            showLoginWindow();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Company> call, Throwable t) {
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

}