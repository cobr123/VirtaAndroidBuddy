package com.virtaandroidbuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            val login = ed_login.text.toString()
            val password = ed_password.text.toString()
            if (login.isEmpty()) {
                tv_error.setText(getString(R.string.error_login_required))
            } else if (password.isEmpty()) {
                tv_error.setText(getString(R.string.error_password_required))
            } else {
                val thr = Thread {

                    try {
                        val initCookies = Jsoup.connect(getString(R.string.base_url))
                                .method(Connection.Method.GET)
                                .execute()
                        val auth = Jsoup.connect("${getString(R.string.base_url)}vera/main/user/login")
                                .timeout(60_000)
                                .referrer(getString(R.string.base_url))
                                .data("userData[login]", login)
                                .data("userData[password]", password)
                                .data("userData[lang]", "ru")
                                .cookies(initCookies.cookies()) // important!
                                .method(Connection.Method.POST)
                                .execute()

                        val companyInfo = Jsoup.connect("${getString(R.string.base_url)}api/${auth.cookie("last_realm")
                                ?: "vera"}/main/my/company")
                                .referrer(getString(R.string.base_url))
                                .cookies(auth.cookies()) // important!
                                .header("Accept-Language", "ru")
                                .header("Accept-Encoding", "gzip, deflate")
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                .maxBodySize(0)
                                .timeout(60_000)
                                .ignoreContentType(true)
                                .method(Connection.Method.GET)
                                .execute()

                        runOnUiThread {
                            if ("false".equals(companyInfo.body().toString())) {
                                tv_error.setText(getString(R.string.error__create_company_before_login))
                            } else {
                                tv_error.setText(companyInfo.body())
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            tv_error.setText(e.localizedMessage)
                        }
                    }


                }
                thr.start()
                thr.join()
            }
        }
    }
}
