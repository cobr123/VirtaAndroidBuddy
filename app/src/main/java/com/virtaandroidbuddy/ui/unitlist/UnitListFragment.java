package com.virtaandroidbuddy.ui.unitlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.api.ApiUtils;
import com.virtaandroidbuddy.api.VirtonomicaApi;
import com.virtaandroidbuddy.api.model.Company;
import com.virtaandroidbuddy.api.model.Unit;
import com.virtaandroidbuddy.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UnitListFragment extends Fragment {

    public static UnitListFragment newInstance() {
        Bundle args = new Bundle();

        UnitListFragment fragment = new UnitListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ListView mUnitListView;
    private ArrayAdapter<String> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fr_unit_list, container, false);

        mUnitListView = view.findViewById(R.id.lv_units);
        final List<String> emptyList = Collections.emptyList();
        mAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, emptyList);
        mUnitListView.setAdapter(mAdapter);

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
                            final List<String> list = new ArrayList<>();
                            for(Unit item : response.body()){
                                list.add(item.getName());
                            }
                            mAdapter.addAll(list);
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
        return view;
    }

    private void showLoginWindow() {
        final Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}