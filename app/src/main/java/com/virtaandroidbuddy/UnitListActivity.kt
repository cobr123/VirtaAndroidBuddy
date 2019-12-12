package com.virtaandroidbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.virtaandroidbuddy.api.ApiUtils
import com.virtaandroidbuddy.api.VirtonomicaApi
import com.virtaandroidbuddy.model.Company
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UnitListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_list)

        val thr = Thread {
            try {
                val client = ApiUtils.getClient(this)
                val realm = ApiUtils.getRealm(this)
//                ApiUtils.changeRealm(client, getString(R.string.base_url), realm)
                val api = ApiUtils.getRetrofit(client, getString(R.string.base_url)).create(VirtonomicaApi::class.java)
                api.getCompanyInfo(realm).enqueue(object : Callback<Company> {
                    override fun onFailure(call: Call<Company>?, t: Throwable?) {
                        Log.d("VirtonomicaApi", t.toString())
                        runOnUiThread {
                            val intent = Intent(this@UnitListActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onResponse(call: Call<Company>?, response: Response<Company>?) {
                        Log.d("VirtonomicaApi", "onResponse")
                        Log.d("VirtonomicaApi", "response = ${response?.toString()}")
                    }

                })
            } catch (t: Throwable) {
                Log.d("VirtonomicaApi", t.toString())
                runOnUiThread {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        thr.start()
        thr.join()
    }
}