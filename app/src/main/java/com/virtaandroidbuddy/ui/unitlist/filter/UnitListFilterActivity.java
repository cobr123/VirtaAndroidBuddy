package com.virtaandroidbuddy.ui.unitlist.filter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Region;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UnitListFilterActivity extends AppCompatActivity {

    private static final String TAG = UnitListFilterActivity.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Storage mStorage;

    private Button mShowFilteredUnitlistBtn;
    private Spinner mCountrySp;
    private Spinner mRegionSp;
    private Spinner mCitySp;
    private ArrayAdapter<Country> mCountryAdapter;
    private ArrayAdapter<Region> mRegionAdapter;
    private ArrayAdapter<City> mCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mStorage = ((AppDelegate) getApplicationContext()).getStorage();

        mShowFilteredUnitlistBtn = findViewById(R.id.show_filtered_unitlist_button);
        mShowFilteredUnitlistBtn.setText(getString(R.string.show_filtered_unitlist_button_title, 0));
        mShowFilteredUnitlistBtn.setOnClickListener(v -> finish());

        mCountrySp = findViewById(R.id.sp_country);
        mRegionSp = findViewById(R.id.sp_region);
        mCitySp = findViewById(R.id.sp_city);

        final List<Country> countryList = new ArrayList<>();
        mCountryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countryList);
        mCountrySp.setAdapter(mCountryAdapter);
        mCountrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<Region> regionList = new ArrayList<>();
        mRegionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, regionList);
        mRegionSp.setAdapter(mRegionAdapter);
        mRegionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<City> cityList = new ArrayList<>();
        mCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityList);
        mCitySp.setAdapter(mCityAdapter);
        mCitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final Session session = mStorage.getSession();

        mCompositeDisposable.add(mStorage.getCountryList(session.getRealm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countries -> {
                            if (countries.isEmpty()) {
                                mCompositeDisposable.add(ApiUtils.getApiService(this).getCountryList(session.getRealm())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(countries2 -> mStorage.insertCountryList(session.getRealm(), countries2),
                                                this::showError));
                            } else {
                                mCountryAdapter.clear();
                                mCountryAdapter.addAll(countries);
                                mCountryAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mCountryAdapter.insert(new Country("All countries"), 0);
                                mCountryAdapter.notifyDataSetChanged();
                            }
                        },
                        this::showError));

        mCompositeDisposable.add(mStorage.getRegionList(session.getRealm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(regions -> {
                            if (regions.isEmpty()) {
                                mCompositeDisposable.add(ApiUtils.getApiService(this).getRegionList(session.getRealm())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(regions2 -> mStorage.insertRegionList(session.getRealm(), regions2),
                                                this::showError));
                            } else {
                                mRegionAdapter.clear();
                                mRegionAdapter.addAll(regions);
                                mRegionAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mRegionAdapter.insert(new Region("All regions"), 0);
                                mRegionAdapter.notifyDataSetChanged();
                            }
                        },
                        this::showError));

        mCompositeDisposable.add(mStorage.getCityList(session.getRealm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities -> {
                            if (cities.isEmpty()) {
                                mCompositeDisposable.add(ApiUtils.getApiService(this).getCityList(session.getRealm())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(cities2 -> mStorage.insertCityList(session.getRealm(), cities2),
                                                this::showError));
                            } else {
                                mCityAdapter.clear();
                                mCityAdapter.addAll(cities);
                                mCityAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mCityAdapter.insert((new City("All cities")), 0);
                                mCityAdapter.notifyDataSetChanged();
                            }
                        },
                        this::showError));
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    private void showError(Throwable throwable) {
        Log.e(TAG, throwable.toString(), throwable);
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}