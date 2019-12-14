package com.virtaandroidbuddy.ui.login;

import android.os.Bundle;
import android.os.Handler;
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
import com.virtaandroidbuddy.api.ApiUtils;
import com.virtaandroidbuddy.api.VirtonomicaApi;
import com.virtaandroidbuddy.api.model.CompanyJson;
import com.virtaandroidbuddy.database.VirtonomicaDao;
import com.virtaandroidbuddy.database.model.Session;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                    final OkHttpClient client = ApiUtils.getClient(view.getContext());
                    final VirtonomicaApi api = ApiUtils.getApi(client, getString(R.string.base_url));
                    final Handler handler = new Handler(getMainLooper());

                    //init cookies
                    api.getCompanyInfo(realm).enqueue(new Callback<CompanyJson>() {
                        @Override
                        public void onResponse(Call<CompanyJson> call, Response<CompanyJson> response) {
                            Log.d("VirtonomicaApi", "onResponse init cookies");
                        }

                        @Override
                        public void onFailure(Call<CompanyJson> call, Throwable t) {
                            ApiUtils.loginUser(client, getString(R.string.base_url), login, password, realm).enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(okhttp3.Call call, final IOException e) {
                                    Log.d("VirtonomicaApi", e.toString());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mErrorTv.setText(e.toString());
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                                    api.getCompanyInfo(realm).enqueue(new Callback<CompanyJson>() {
                                        @Override
                                        public void onResponse(Call<CompanyJson> call, final Response<CompanyJson> response) {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (response.body() == null || response.body().getId() == null || response.body().getId().isEmpty()) {
                                                        mErrorTv.setText(getString(R.string.error_create_company_before_login));
                                                    } else {
                                                        mErrorTv.setText("");
                                                        VirtonomicaDao virtonomicaDao = ((AppDelegate) getApplicationContext()).getVirtonomicaDatabase().getVirtonomicaDao();
                                                        virtonomicaDao.insertSession(new Session(1, realm, response.body().getId()));
                                                        finish();
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<CompanyJson> call, final Throwable t) {
                                            Log.d("VirtonomicaApi", t.toString());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mErrorTv.setText(t.toString());
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    Log.d("VirtonomicaApi", e.toString());
                    mErrorTv.setText(getString(R.string.error_create_company_before_login));
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
