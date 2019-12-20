package com.virtaandroidbuddy.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.utils.ApiUtils;
import com.virtaandroidbuddy.data.api.VirtonomicaApi;
import com.virtaandroidbuddy.data.database.model.Session;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {

    private EditText mLoginEd;
    private EditText mPasswordEd;
    private Spinner mRealmSp;
    private Button mLoginBtn;
    private TextView mErrorTv;

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
                ApiUtils.setCookies(view.getContext(), null);
                final String realm = realms.get((int) mRealmSp.getSelectedItemId());
                try {
                    final VirtonomicaApi api = ApiUtils.getApiService(view.getContext());

                    //init cookies
                    api.getCompanyInfo(realm)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(companyJson -> Log.d("VirtonomicaApi", "accept init cookies"),
                                    throwable ->
                                            api.login(realm, login, password, "ru")
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(() -> api.getCompanyInfo(realm)
                                                                    .subscribeOn(Schedulers.io())
                                                                    .observeOn(AndroidSchedulers.mainThread())
                                                                    .subscribe(companyJson -> {
                                                                                mErrorTv.setText("");
                                                                                ((AppDelegate) getApplicationContext()).getStorage().insertSession(new Session(1, realm, companyJson.getId()));
                                                                                finish();
                                                                            },
                                                                            throwable12 -> {
                                                                                Log.e("VirtonomicaApi", throwable12.toString(), throwable12);
                                                                                mErrorTv.setText(getString(R.string.error_create_company_before_login));
                                                                            }),
                                                            throwable1 -> {
                                                                Log.e("VirtonomicaApi", throwable1.toString(), throwable1);
                                                                mErrorTv.setText(throwable1.toString());
                                                            }));
                } catch (Throwable t) {
                    Log.e("VirtonomicaApi", t.toString());
                    mErrorTv.setText(t.toString());
                }
            }
        }
    };
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
    }

}
