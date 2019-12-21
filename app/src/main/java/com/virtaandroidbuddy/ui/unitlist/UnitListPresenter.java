package com.virtaandroidbuddy.ui.unitlist;

import android.content.Context;

import com.virtaandroidbuddy.common.BasePresenter;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UnitListPresenter extends BasePresenter {

    private UnitListView mView;

    private Storage mStorage;

    public UnitListPresenter(UnitListView unitListView, Storage storage) {
        mView = unitListView;
        mStorage = storage;
    }

    public void getUnitlist(Context context) {
        final Session session = mStorage.getSession();

        mCompositeDisposable.add(ApiUtils.getApiService(context).getUnitList(session.getRealm(), session.getCompanyId())
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUnits(response, session))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getUnitList(session) : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .subscribe(unitListJson -> mView.showUnitList(unitListJson),
                        throwable -> mView.showError(throwable)));
    }

    public void openUnitSummary(UnitListDataJson unitListDataJson) {
        mView.openUnitSummary(unitListDataJson);
    }
}
