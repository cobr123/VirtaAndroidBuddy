package com.virtaandroidbuddy.ui.unit.summary;

import android.content.Context;

import com.virtaandroidbuddy.common.BasePresenter;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UnitSummaryPresenter extends BasePresenter {

    private UnitSummaryView mView;

    private Storage mStorage;

    public UnitSummaryPresenter(UnitSummaryView unitSummaryView, Storage storage) {
        mView = unitSummaryView;
        mStorage = storage;
    }

    public void getUnitSummary(Context context, String unitId) {
        final Session session = mStorage.getSession();
        mCompositeDisposable.add(ApiUtils.getApiService(context).getUnitSummary(session.getRealm(), unitId)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUnitSummary(session.getRealm(), session.getCompanyId(), response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUnitSummary(session.getRealm(), unitId) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .subscribe(
                        response -> mView.showUnitSummary(response),
                        throwable -> mView.showError(throwable)));
    }
}
