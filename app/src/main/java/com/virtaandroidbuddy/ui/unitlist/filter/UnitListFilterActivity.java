package com.virtaandroidbuddy.ui.unitlist.filter;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Region;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.UnitClass;
import com.virtaandroidbuddy.data.database.model.UnitListFilter;
import com.virtaandroidbuddy.data.database.model.UnitType;
import com.virtaandroidbuddy.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UnitListFilterActivity extends AppCompatActivity {

    private static final String TAG = UnitListFilterActivity.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Storage mStorage;

    private Button mShowFilteredUnitlistBtn;
    private Spinner mUnitClassSp;
    private Spinner mUnitTypeSp;
    private Spinner mCountrySp;
    private Spinner mRegionSp;
    private Spinner mCitySp;
    private boolean mUnitClassSpTouched = false;
    private boolean mUnitTypeSpTouched = false;
    private boolean mCountrySpTouched = false;
    private boolean mRegionSpTouched = false;
    private boolean mCitySpTouched = false;
    private ArrayAdapter<UnitClass> mUnitClassAdapter;
    private ArrayAdapter<UnitType> mUnitTypeAdapter;
    private ArrayAdapter<Country> mCountryAdapter;
    private ArrayAdapter<Region> mRegionAdapter;
    private ArrayAdapter<City> mCityAdapter;
    private final List<Region> mAllRegionList = new ArrayList<>();
    private final List<City> mAllCityList = new ArrayList<>();
    private final List<UnitType> mAllUnitTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_unit_list_filter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mStorage = ((AppDelegate) getApplicationContext()).getStorage();
        final Session session = mStorage.getSession();

        mShowFilteredUnitlistBtn = findViewById(R.id.show_filtered_unitlist_button);
        mShowFilteredUnitlistBtn.setText(getString(R.string.show_filtered_unitlist_button_title, "0"));
        mShowFilteredUnitlistBtn.setOnClickListener(v -> finish());

        mUnitClassSp = findViewById(R.id.sp_unit_class);
        mUnitTypeSp = findViewById(R.id.sp_unit_type);
        mCountrySp = findViewById(R.id.sp_country);
        mRegionSp = findViewById(R.id.sp_region);
        mCitySp = findViewById(R.id.sp_city);

        mCountryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        mCountrySp.setAdapter(mCountryAdapter);
        mCountrySp.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCountrySpTouched = true;
                return false;
            }
        });
        mCountrySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mCountrySpTouched) return;

                mCountrySpTouched = false;
                final UnitListFilter unitListFilter = mStorage.getUnitListFilter(session);

                mRegionAdapter.clear();
                mCityAdapter.clear();
                if (position > 0) {
                    final Country country = mCountryAdapter.getItem(position);
                    unitListFilter.setCountryId(country.getId());
                    mRegionAdapter.addAll(mAllRegionList.stream().filter(r -> r.getCountryId().equals(country.getId())).collect(Collectors.toList()));
                    mCityAdapter.addAll(mAllCityList.stream().filter(r -> r.getCountryId().equals(country.getId())).collect(Collectors.toList()));
                } else {
                    unitListFilter.setCountryId("0");
                    mRegionAdapter.addAll(mAllRegionList);
                    mCityAdapter.addAll(mAllCityList);
                }

                mRegionAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                mRegionAdapter.insert(new Region(getString(R.string.all_regions)), 0);
                if (unitListFilter.getRegionId().equals("0")) {
                    mRegionSp.setSelection(0, false);
                } else {
                    final Optional<Region> regionOpt = mAllRegionList.stream()
                            .filter(r -> r.getId().equals(unitListFilter.getRegionId()))
                            .filter(r -> unitListFilter.getCountryId().equals("0") || r.getCountryId().equals(unitListFilter.getCountryId()))
                            .findFirst();
                    if (regionOpt.isPresent()) {
                        Log.d(TAG, "region setter id = " + regionOpt.get().getId() + ", name = " + regionOpt.get().getName() + ", position = " + mRegionAdapter.getPosition(regionOpt.get()));
                        mRegionSp.setSelection(mRegionAdapter.getPosition(regionOpt.get()), false);
                    } else {
                        unitListFilter.setRegionId("0");
                        mRegionSp.setSelection(0, false);
                    }
                }

                mCityAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                mCityAdapter.insert((new City(getString(R.string.all_cities))), 0);
                if (unitListFilter.getCityId().equals("0")) {
                    mCitySp.setSelection(0, false);
                } else {
                    final Optional<City> cityOpt = mAllCityList.stream()
                            .filter(c -> c.getId().equals(unitListFilter.getCityId()))
                            .filter(r -> unitListFilter.getRegionId().equals("0") || r.getRegionId().equals(unitListFilter.getRegionId()))
                            .filter(r -> unitListFilter.getCountryId().equals("0") || r.getCountryId().equals(unitListFilter.getCountryId()))
                            .findFirst();
                    if (cityOpt.isPresent()) {
                        Log.d(TAG, "city setter id = " + cityOpt.get().getId() + ", name = " + cityOpt.get().getName() + ", position = " + mCityAdapter.getPosition(cityOpt.get()));
                        mCitySp.setSelection(mCityAdapter.getPosition(cityOpt.get()), false);
                    } else {
                        unitListFilter.setCityId("0");
                        mCitySp.setSelection(0, false);
                    }
                }

                mStorage.insertUnitListFilter(unitListFilter);
                Log.d(TAG, "mCountrySp position = " + position + ", id = " + id + ", unitListFilter = " + unitListFilter);
                updateFinishButtonText(session, unitListFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRegionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        mRegionSp.setAdapter(mRegionAdapter);
        mRegionSp.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRegionSpTouched = true;
                return false;
            }
        });
        mRegionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mRegionSpTouched) return;

                mRegionSpTouched = false;
                final UnitListFilter unitListFilter = mStorage.getUnitListFilter(session);
                mCityAdapter.clear();
                if (position > 0) {
                    final Region region = mRegionAdapter.getItem(position);
                    unitListFilter.setRegionId(region.getId());
                    mCityAdapter.addAll(mAllCityList.stream()
                            .filter(r -> r.getRegionId().equals(region.getId()))
                            .collect(Collectors.toList()));
                } else {
                    unitListFilter.setRegionId("0");
                    if (!unitListFilter.getCountryId().equals("0")) {
                        mCityAdapter.addAll(mAllCityList.stream()
                                .filter(r -> r.getCountryId().equals(unitListFilter.getCountryId()))
                                .collect(Collectors.toList()));
                    } else {
                        mCityAdapter.addAll(mAllCityList);
                    }
                }
                mCityAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                mCityAdapter.insert((new City(getString(R.string.all_cities))), 0);
                if (unitListFilter.getCityId().equals("0")) {
                    mCitySp.setSelection(0, false);
                } else {
                    final Optional<City> cityOpt = mAllCityList.stream()
                            .filter(c -> c.getId().equals(unitListFilter.getCityId()))
                            .filter(r -> unitListFilter.getRegionId().equals("0") || r.getRegionId().equals(unitListFilter.getRegionId()))
                            .filter(r -> unitListFilter.getCountryId().equals("0") || r.getCountryId().equals(unitListFilter.getCountryId()))
                            .findFirst();
                    if (cityOpt.isPresent()) {
                        Log.d(TAG, "city setter id = " + cityOpt.get().getId() + ", name = " + cityOpt.get().getName() + ", position = " + mCityAdapter.getPosition(cityOpt.get()));
                        mCitySp.setSelection(mCityAdapter.getPosition(cityOpt.get()), false);
                    } else {
                        unitListFilter.setCityId("0");
                        mCitySp.setSelection(0, false);
                    }
                }

                mStorage.insertUnitListFilter(unitListFilter);
                Log.d(TAG, "mRegionSp position = " + position + ", id = " + id + ", unitListFilter = " + unitListFilter);
                updateFinishButtonText(session, unitListFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        mCitySp.setAdapter(mCityAdapter);
        mCitySp.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCitySpTouched = true;
                return false;
            }
        });
        mCitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mCitySpTouched) return;

                mCitySpTouched = false;
                final UnitListFilter unitListFilter = mStorage.getUnitListFilter(session);
                if (position > 0) {
                    final City city = mCityAdapter.getItem(position);
                    unitListFilter.setCityId(city.getId());
                } else {
                    unitListFilter.setCityId("0");
                }
                mStorage.insertUnitListFilter(unitListFilter);
                Log.d(TAG, "mCitySp position = " + position + ", id = " + id + ", unitListFilter = " + unitListFilter);
                updateFinishButtonText(session, unitListFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mUnitClassAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        mUnitClassSp.setAdapter(mUnitClassAdapter);
        mUnitClassSp.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mUnitClassSpTouched = true;
                return false;
            }
        });
        mUnitClassSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mUnitClassSpTouched) return;

                mUnitClassSpTouched = false;
                final UnitListFilter unitListFilter = mStorage.getUnitListFilter(session);
                mUnitTypeAdapter.clear();
                if (position > 0) {
                    final UnitClass unitClass = mUnitClassAdapter.getItem(position);
                    unitListFilter.setUnitClassId(unitClass.getId());
                    mUnitTypeAdapter.addAll(mAllUnitTypeList.stream()
                            .filter(r -> r.getUnitClassId().equals(unitClass.getId()))
                            .collect(Collectors.toList()));
                } else {
                    unitListFilter.setUnitClassId("0");
                    mUnitTypeAdapter.addAll(mAllUnitTypeList);
                }
                mUnitTypeAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                mUnitTypeAdapter.insert((new UnitType(getString(R.string.all_unit_types))), 0);
                if (unitListFilter.getUnitTypeId().equals("0")) {
                    mUnitTypeSp.setSelection(0, false);
                } else {
                    final Optional<UnitType> unitTypeOpt = mAllUnitTypeList.stream()
                            .filter(c -> c.getId().equals(unitListFilter.getUnitTypeId()))
                            .filter(r -> unitListFilter.getUnitClassId().equals("0") || r.getUnitClassId().equals(unitListFilter.getUnitClassId()))
                            .findFirst();
                    if (unitTypeOpt.isPresent()) {
                        Log.d(TAG, "UnitType setter id = " + unitTypeOpt.get().getId() + ", name = " + unitTypeOpt.get().getName() + ", position = " + mUnitTypeAdapter.getPosition(unitTypeOpt.get()));
                        mUnitTypeSp.setSelection(mUnitTypeAdapter.getPosition(unitTypeOpt.get()), false);
                    } else {
                        unitListFilter.setUnitTypeId("0");
                        mUnitTypeSp.setSelection(0, false);
                    }
                }

                mStorage.insertUnitListFilter(unitListFilter);
                Log.d(TAG, "mUnitClassSp position = " + position + ", id = " + id + ", unitListFilter = " + unitListFilter);
                updateFinishButtonText(session, unitListFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mUnitTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        mUnitTypeSp.setAdapter(mUnitTypeAdapter);
        mUnitTypeSp.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mUnitTypeSpTouched = true;
                return false;
            }
        });
        mUnitTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mUnitTypeSpTouched) return;

                mUnitTypeSpTouched = false;
                final UnitListFilter unitListFilter = mStorage.getUnitListFilter(session);
                if (position > 0) {
                    final UnitType unitType = mUnitTypeAdapter.getItem(position);
                    unitListFilter.setUnitTypeId(unitType.getId());
                } else {
                    unitListFilter.setUnitTypeId("0");
                }
                mStorage.insertUnitListFilter(unitListFilter);
                Log.d(TAG, "mUnitTypeSp position = " + position + ", id = " + id + ", unitListFilter = " + unitListFilter);
                updateFinishButtonText(session, unitListFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final UnitListFilter unitListFilter = mStorage.getUnitListFilter(session);

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
                                mCountryAdapter.insert(new Country(getString(R.string.all_countries)), 0);

                                if (!unitListFilter.getCountryId().equals("0")) {
                                    final Optional<Country> countryOpt = countries.stream().filter(c -> c.getId().equals(unitListFilter.getCountryId())).findFirst();
                                    countryOpt.ifPresent(country -> {
                                        Log.d(TAG, "countryOpt id = " + country.getId() + ", name = " + country.getName() + ", position = " + mCountryAdapter.getPosition(country));
                                        mCountrySp.setSelection(mCountryAdapter.getPosition(country), false);
                                    });
                                }
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
                                mAllRegionList.clear();
                                mAllRegionList.addAll(regions);

                                mRegionAdapter.clear();
                                if (!unitListFilter.getCountryId().equals("0")) {
                                    mRegionAdapter.addAll(mAllRegionList.stream().filter(r -> r.getCountryId().equals(unitListFilter.getCountryId())).collect(Collectors.toList()));
                                } else {
                                    mRegionAdapter.addAll(mAllRegionList);
                                }
                                mRegionAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mRegionAdapter.insert(new Region(getString(R.string.all_regions)), 0);

                                final Optional<Region> regionOpt = mAllRegionList.stream().filter(r -> r.getId().equals(unitListFilter.getRegionId())).findFirst();
                                if (regionOpt.isPresent()) {
                                    Log.d(TAG, "regionOpt id = " + regionOpt.get().getId() + ", name = " + regionOpt.get().getName() + ", position = " + mRegionAdapter.getPosition(regionOpt.get()));
                                    mRegionSp.setSelection(mRegionAdapter.getPosition(regionOpt.get()), false);
                                }
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
                                mAllCityList.clear();
                                mAllCityList.addAll(cities);

                                mCityAdapter.clear();
                                if (!unitListFilter.getRegionId().equals("0")) {
                                    mCityAdapter.addAll(mAllCityList.stream().filter(r -> r.getRegionId().equals(unitListFilter.getRegionId())).collect(Collectors.toList()));
                                } else if (!unitListFilter.getCountryId().equals("0")) {
                                    mCityAdapter.addAll(mAllCityList.stream().filter(r -> r.getCountryId().equals(unitListFilter.getCountryId())).collect(Collectors.toList()));
                                } else {
                                    mCityAdapter.addAll(mAllCityList);
                                }
                                mCityAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mCityAdapter.insert((new City(getString(R.string.all_cities))), 0);

                                final Optional<City> cityOpt = mAllCityList.stream().filter(c -> c.getId().equals(unitListFilter.getCityId())).findFirst();
                                if (cityOpt.isPresent()) {
                                    Log.d(TAG, "cityOpt id = " + cityOpt.get().getId() + ", name = " + cityOpt.get().getName() + ", position = " + mCityAdapter.getPosition(cityOpt.get()));
                                    mCitySp.setSelection(mCityAdapter.getPosition(cityOpt.get()), false);
                                }
                            }
                        },
                        this::showError));

        mCompositeDisposable.add(mStorage.getUnitClassList(session.getRealm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unitClassList -> {
                            if (unitClassList.isEmpty()) {
                                mCompositeDisposable.add(ApiUtils.getApiService(this).getUnitClassList(session.getRealm())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(unitClassList2 -> mStorage.insertUnitClassList(session.getRealm(), unitClassList2),
                                                this::showError));
                            } else {

                                mUnitClassAdapter.clear();
                                mUnitClassAdapter.addAll(unitClassList);
                                mUnitClassAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mUnitClassAdapter.insert((new UnitClass(getString(R.string.all_unit_classes))), 0);

                                final Optional<UnitClass> unitClassOpt = unitClassList.stream().filter(c -> c.getId().equals(unitListFilter.getUnitClassId())).findFirst();
                                if (unitClassOpt.isPresent()) {
                                    Log.d(TAG, "unitClassOpt id = " + unitClassOpt.get().getId() + ", name = " + unitClassOpt.get().getName() + ", position = " + mUnitClassAdapter.getPosition(unitClassOpt.get()));
                                    mUnitClassSp.setSelection(mUnitClassAdapter.getPosition(unitClassOpt.get()), false);
                                }
                            }
                        },
                        this::showError));

        mCompositeDisposable.add(mStorage.getUnitTypeList(session.getRealm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unitTypeList -> {
                            if (unitTypeList.isEmpty()) {
                                mCompositeDisposable.add(ApiUtils.getApiService(this).getUnitTypeList(session.getRealm())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(unitTypeList2 -> mStorage.insertUnitTypeList(session.getRealm(), unitTypeList2),
                                                this::showError));
                            } else {
                                mAllUnitTypeList.clear();
                                mAllUnitTypeList.addAll(unitTypeList);

                                mUnitTypeAdapter.clear();
                                if (!unitListFilter.getUnitClassId().equals("0")) {
                                    mUnitTypeAdapter.addAll(mAllUnitTypeList.stream().filter(r -> r.getUnitClassId().equals(unitListFilter.getUnitClassId())).collect(Collectors.toList()));
                                } else {
                                    mUnitTypeAdapter.addAll(mAllUnitTypeList);
                                }
                                mUnitTypeAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                                mUnitTypeAdapter.insert((new UnitType(getString(R.string.all_unit_types))), 0);

                                final Optional<UnitType> unitTypeOpt = mAllUnitTypeList.stream().filter(c -> c.getId().equals(unitListFilter.getUnitTypeId())).findFirst();
                                if (unitTypeOpt.isPresent()) {
                                    Log.d(TAG, "unitTypeOpt id = " + unitTypeOpt.get().getId() + ", name = " + unitTypeOpt.get().getName() + ", position = " + mUnitTypeAdapter.getPosition(unitTypeOpt.get()));
                                    mUnitTypeSp.setSelection(mUnitTypeAdapter.getPosition(unitTypeOpt.get()), false);
                                }
                            }
                        },
                        this::showError));

        updateFinishButtonText(session, unitListFilter);
    }

    private void updateFinishButtonText(final Session session, final UnitListFilter unitListFilter) {
        mShowFilteredUnitlistBtn.setText(getString(R.string.show_filtered_unitlist_button_title, "..."));

        mCompositeDisposable.add(ApiUtils.getApiService(getApplicationContext()).getUnitList(session.getRealm(), session.getCompanyId(), unitListFilter.getGeo(), unitListFilter.getUnitClassId(), unitListFilter.getUnitTypeId())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                        return mStorage.getUnitList(session.getRealm(), session.getCompanyId(), unitListFilter);
                    } else {
                        showError(throwable);
                        return new UnitListJson();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(unitListJson -> mShowFilteredUnitlistBtn.setText(getString(R.string.show_filtered_unitlist_button_title, String.valueOf(unitListJson.getData().size()))),
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