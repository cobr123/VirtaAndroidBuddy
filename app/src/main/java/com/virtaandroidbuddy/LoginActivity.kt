package com.virtaandroidbuddy

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.virtaandroidbuddy.api.ApiUtils
import com.virtaandroidbuddy.api.VirtonomicaApi
import com.virtaandroidbuddy.model.Company
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ApiUtils.setCookies(this, null)
        val realms = arrayOf("vera", "olga", "anna", "lien", "mary", "nika", "fast")
        val realms_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, realms)
        sp_realm.adapter = realms_adapter
        sp_realm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                ApiUtils.setRealm(this@LoginActivity, realms[0])
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ApiUtils.setRealm(this@LoginActivity, realms[id.toInt()])
            }
        }
        val prevRealm = ApiUtils.getRealm(this@LoginActivity) ?: realms[0]
        sp_realm.setSelection(realms.indexOf(prevRealm))

        btn_login.setOnClickListener {
            val login = ed_login.text.toString()
            val password = ed_password.text.toString()
            if (login.isEmpty()) {
                tv_error.setText(getString(R.string.error_login_required))
            } else if (password.isEmpty()) {
                tv_error.setText(getString(R.string.error_password_required))
            } else {
                val realm = realms[sp_realm.selectedItemId.toInt()]
                val thr = Thread {
                    try {
                        val client = ApiUtils.getClient(this)
                        ApiUtils.loginUser(client, getString(R.string.base_url), login, password)

                        val api = ApiUtils.getRetrofit(client, getString(R.string.base_url)).create(VirtonomicaApi::class.java)
                        api.getCompanyInfo(realm).enqueue(object : Callback<Company> {
                            override fun onFailure(call: Call<Company>?, t: Throwable?) {
                                Log.d("VirtonomicaApi", t.toString())
                                runOnUiThread {
                                    tv_error.setText(t.toString())
                                }
                            }

                            override fun onResponse(call: Call<Company>?, response: Response<Company>?) {
                                runOnUiThread {
                                    tv_error.setText("")
                                    finish()
                                }
                            }
                        })
                    } catch (e: Exception) {
                        Log.d("VirtonomicaApi", e.toString())
                        runOnUiThread {
                            tv_error.setText(getString(R.string.error__create_company_before_login))
                        }
                    }
                }
                thr.start()
                thr.join()
            }
        }
    }
}
