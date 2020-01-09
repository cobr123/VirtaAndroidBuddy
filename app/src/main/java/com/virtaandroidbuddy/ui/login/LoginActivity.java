package com.virtaandroidbuddy.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.google.gson.Gson;
import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.GameUpdateHappeningNowException;
import com.virtaandroidbuddy.utils.CryptoUtils;
import com.virtaandroidbuddy.utils.FingerprintUtils;
import com.virtaandroidbuddy.utils.ApiUtils;
import com.virtaandroidbuddy.data.api.VirtonomicaApi;
import com.virtaandroidbuddy.data.database.model.Session;

import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText mLoginEd;
    private EditText mPasswordEd;
    private Spinner mRealmSp;
    private Button mLoginBtn;
    private TextView mErrorTv;
    private ProgressBar mLoadingProgressBar;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private static final String LOGIN_PASSWORD_KEY = "login_password";
    private SharedPreferences mPreferences;
    private FingerprintHelper mFingerprintHelper;

    final List<String> realms = Arrays.asList("vera", "olga", "anna", "lien", "mary", "nika", "fast");

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final String login = mLoginEd.getText().toString();
            final String password = mPasswordEd.getText().toString();
            if (login.isEmpty()) {
                mErrorTv.setText(getString(R.string.error_login_required));
            } else if (password.isEmpty()) {
                mErrorTv.setText(getString(R.string.error_password_required));
            } else {
                mErrorTv.setText("");
                final Storage storage = obtainStorage();
                if (storage.getSession() != null) {
                    storage.deleteSession(storage.getSession());
                }
                ApiUtils.setCookies(view.getContext(), null);
                final String realm = realms.get((int) mRealmSp.getSelectedItemId());
                try {
                    disableInput();
                    final VirtonomicaApi api = ApiUtils.getApiService(view.getContext());

                    //init cookies
                    mCompositeDisposable.add(
                            api.getCompanyInfo(realm)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(companyJson -> {
                                                storage.insertSession(new Session(1, realm, companyJson.getId(), companyJson.getName(), companyJson.getPresidentUserId()));
                                                enabledInput();
                                                finish();
                                            },
                                            throwable -> mCompositeDisposable.add(
                                                    api.login(realm, login, password, "ru")
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(() -> mCompositeDisposable.add(
                                                                    api.getCompanyInfo(realm)
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                            .subscribe(companyJson2 -> {
                                                                                        save(login, password, realm);
                                                                                        storage.insertSession(new Session(1, realm, companyJson2.getId(), companyJson2.getName(), companyJson2.getPresidentUserId()));
                                                                                        enabledInput();
                                                                                        finish();
                                                                                    },
                                                                                    throwable12 -> {
                                                                                        enabledInput();
                                                                                        mCompositeDisposable.clear();
                                                                                        Log.e(TAG + ".getCompanyInfo2", throwable12.toString(), throwable12);
                                                                                        if (throwable12 instanceof GameUpdateHappeningNowException) {
                                                                                            mErrorTv.setText(getString(R.string.game_update_happening_now));
                                                                                        } else {
                                                                                            mErrorTv.setText(getString(R.string.error_create_company_before_login));
                                                                                        }
                                                                                    })),
                                                                    throwable1 -> {
                                                                        enabledInput();
                                                                        mCompositeDisposable.clear();
                                                                        Log.e(TAG + ".login", throwable1.toString(), throwable1);
                                                                        if (throwable1 instanceof GameUpdateHappeningNowException) {
                                                                            mErrorTv.setText(getString(R.string.game_update_happening_now));
                                                                        } else {
                                                                            mErrorTv.setText(throwable1.getLocalizedMessage());
                                                                        }
                                                                    }))));
                } catch (Throwable t) {
                    enabledInput();
                    mCompositeDisposable.clear();
                    Log.e(TAG + ".onClick", t.toString());
                    mErrorTv.setText(t.getLocalizedMessage());
                }
            }
        }
    };

    private void disableInput() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mLoginEd.setEnabled(false);
        mPasswordEd.setEnabled(false);
        mLoginBtn.setEnabled(false);
        mRealmSp.setEnabled(false);
    }

    private void enabledInput() {
        mLoadingProgressBar.setVisibility(View.GONE);
        mLoginEd.setEnabled(true);
        mPasswordEd.setEnabled(true);
        mLoginBtn.setEnabled(true);
        mRealmSp.setEnabled(true);
    }

    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ApiUtils.setRealm(parent.getContext(), realms.get((int) id));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            ApiUtils.setRealm(parent.getContext(), realms.get(0));
        }
    };

    private Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }

    public static final String ERROR_TEXT_PROP_NAME = "ERROR_TEXT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);

        mLoginEd = findViewById(R.id.ed_login);
        mPasswordEd = findViewById(R.id.ed_password);
        mRealmSp = findViewById(R.id.sp_realm);
        mLoginBtn = findViewById(R.id.btn_login);
        mErrorTv = findViewById(R.id.tv_error);
        mLoadingProgressBar = findViewById(R.id.loading);

        final ArrayAdapter<String> realms_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, realms);
        mRealmSp.setAdapter(realms_adapter);
        mRealmSp.setOnItemSelectedListener(mOnItemSelectedListener);

        final String prevRealm = ApiUtils.getRealm(this);
        if (prevRealm != null) {
            mRealmSp.setSelection(realms.indexOf(prevRealm));
        }
        mLoginBtn.setOnClickListener(mOnClickListener);
        final String error = getIntent().getStringExtra(ERROR_TEXT_PROP_NAME);
        if (error != null && !error.isEmpty()) {
            mErrorTv.setText(error);
        }
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreferences.contains(LOGIN_PASSWORD_KEY)) {
            prepareSensor();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFingerprintHelper != null) {
            mFingerprintHelper.cancel();
        }
        mCompositeDisposable.clear();
    }

    private void save(String login, String password, String realm) {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            mPreferences.edit()
                    .putString(LOGIN_PASSWORD_KEY, CryptoUtils.encode(new Gson().toJson(new LoginFormJson(login, password, realm))))
                    .apply();
        }
    }

    private void prepareSensor() {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            FingerprintManagerCompat.CryptoObject cryptoObject = CryptoUtils.getCryptoObject();
            if (cryptoObject != null) {
                Toast.makeText(this, "use fingerprint to login", Toast.LENGTH_LONG).show();
                mFingerprintHelper = new FingerprintHelper(this);
                mFingerprintHelper.startAuth(cryptoObject);
            } else {
                mPreferences.edit().remove(LOGIN_PASSWORD_KEY).apply();
                Toast.makeText(this, "new fingerprint enrolled. enter login and password again", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class FingerprintHelper extends FingerprintManagerCompat.AuthenticationCallback {
        private Context mContext;
        private CancellationSignal mCancellationSignal;

        FingerprintHelper(Context context) {
            mContext = context;
        }

        void startAuth(FingerprintManagerCompat.CryptoObject cryptoObject) {
            mCancellationSignal = new CancellationSignal();
            FingerprintManagerCompat manager = FingerprintManagerCompat.from(mContext);
            manager.authenticate(cryptoObject, 0, mCancellationSignal, this, null);
        }

        void cancel() {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Toast.makeText(mContext, errString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Toast.makeText(mContext, helpString, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            final Cipher cipher = result.getCryptoObject().getCipher();
            final String loginFormJsonString = CryptoUtils.decode(mPreferences.getString(LOGIN_PASSWORD_KEY, null), cipher);
            final LoginFormJson loginFormJson = new Gson().fromJson(loginFormJsonString, LoginFormJson.class);
            mLoginEd.setText(loginFormJson.getLogin());
            mPasswordEd.setText(loginFormJson.getPassword());
            final int spinnerPosition = ((ArrayAdapter<String>) mRealmSp.getAdapter()).getPosition(loginFormJson.getRealm());
            mRealmSp.setSelection(spinnerPosition);
            Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
            mLoginBtn.callOnClick();
        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(mContext, "try again", Toast.LENGTH_SHORT).show();
        }

    }
}
