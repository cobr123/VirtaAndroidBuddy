package com.virtaandroidbuddy.ui.knowledge;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.VirtonomicaApi;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeViewModel extends ViewModel {

    private static final String TAG = KnowledgeViewModel.class.getSimpleName();

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private MutableLiveData<Knowledge> mKnowledgeLiveData = new MutableLiveData<>();

    public LiveData<Knowledge> getData(final Context context, final Storage storage) {
        final VirtonomicaApi api = ApiUtils.getApiService(context);
        final Session session = storage.getSession();
        mCompositeDisposable.add(
                api.getKnowledge(session.getRealm())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(mCompositeDisposable::clear)
                        .subscribe(knowledge -> {
                                    try {
                                        knowledge.setRealm(session.getRealm());
                                        knowledge.setUserId(session.getUserId());
                                        storage.insertKnowledge(knowledge);
                                        mKnowledgeLiveData.setValue(knowledge);
                                    } catch (Throwable throwable) {
                                        Log.e(TAG, throwable.toString(), throwable);
                                        Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                },
                                throwable -> {
                                    Log.e(TAG, throwable.toString(), throwable);
                                    Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                        ));
        return mKnowledgeLiveData;
    }

}