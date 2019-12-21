package com.virtaandroidbuddy.ui.unitlist;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.common.SingleFragmentActivity;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.ui.login.LoginActivity;

public class UnitListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return UnitListFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.unit_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLogout:
                final Storage storage = obtainStorage();
                storage.deleteSession(storage.getSession());
                final Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
