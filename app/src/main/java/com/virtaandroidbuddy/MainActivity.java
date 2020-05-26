package com.virtaandroidbuddy;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.utils.ApiUtils;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_unitlist, R.id.nav_knowledge)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        final View headerView = navigationView.getHeaderView(0);
        final TextView headerTitleTextView = headerView.findViewById(R.id.nav_header_title);
        final TextView headerSubtitleTextView = headerView.findViewById(R.id.nav_header_subtitle);
        final ImageView headerAvatar = headerView.findViewById(R.id.iv_nav_header_avatar);
        final Session session = ((AppDelegate) getApplicationContext()).getStorage().getSession();

        if (session != null) {
            headerTitleTextView.setText(session.getCompanyName());
            headerSubtitleTextView.setText(session.getRealm());

            final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
            mCompositeDisposable.add(ApiUtils.getApiService(this).getUserAvatarUrl(session.getRealm(), session.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(avatarUrl -> {
                                Picasso.get().load(avatarUrl).into(headerAvatar);
                                mCompositeDisposable.clear();
                            },
                            throwable -> {
                                Log.e(TAG, throwable.getLocalizedMessage());
                                Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                mCompositeDisposable.clear();
                            }));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
