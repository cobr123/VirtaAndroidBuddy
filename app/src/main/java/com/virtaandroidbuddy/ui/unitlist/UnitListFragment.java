package com.virtaandroidbuddy.ui.unitlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.common.PresenterFragment;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.api.GameUpdateHappeningNowException;
import com.virtaandroidbuddy.data.api.SessionExpiredException;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.ui.login.LoginActivity;
import com.virtaandroidbuddy.ui.unit.UnitMainActivity;
import com.virtaandroidbuddy.ui.unit.summary.UnitSummaryFragment;
import com.virtaandroidbuddy.ui.unitlist.filter.UnitListFilterActivity;

import io.reactivex.exceptions.CompositeException;


public class UnitListFragment extends PresenterFragment<UnitListPresenter> implements UnitListView, UnitListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = UnitListFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private View mErrorView;
    private Storage mStorage;
    private UnitListAdapter mUnitListAdapter;
    private UnitListPresenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStorage = ((AppDelegate) getActivity().getApplicationContext()).getStorage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_unit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = view.findViewById(R.id.recycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.unitlist_title);
        }

        mPresenter = new UnitListPresenter(this, mStorage);
        mUnitListAdapter = new UnitListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mUnitListAdapter);

        onRefresh();
    }

    @Override
    public void onItemClick(UnitListDataJson unitListDataJson) {
        mPresenter.openUnitSummary(unitListDataJson);
    }

    @Override
    protected UnitListPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDetach() {
        mStorage = null;
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        try {
            mPresenter.getUnitlist(getActivity());
        } catch (Throwable throwable) {
            Log.e(TAG + "onRefresh", throwable.toString(), throwable);
            showLoginWindow(null);
        }
    }


    private final int REQUEST_CODE_LOGIN = 1;
    private final int REQUEST_CODE_FILTER = 2;

    private void showLoginWindow(final String error) {
        final Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(LoginActivity.ERROR_TEXT_PROP_NAME, error);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                onRefresh();
                break;
            case REQUEST_CODE_FILTER:
                onRefresh();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.unitlist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.unitlist_filter_action:
                final Intent intent = new Intent(getActivity(), UnitListFilterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FILTER);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUnitList(UnitListJson unitListJson) {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mUnitListAdapter.addData(unitListJson.getData(), true);
    }

    @Override
    public void openUnitSummary(final UnitListDataJson unitListDataJson) {
        final Intent intent = new Intent(getActivity(), UnitMainActivity.class);
        intent.putExtra(UnitSummaryFragment.UNIT_ID_KEY, unitListDataJson.getId());
        intent.putExtra(UnitSummaryFragment.UNIT_CLASS_NAME_KEY, unitListDataJson.getUnitClassName());
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void showError(Throwable throwable) {
//        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        Log.e(TAG, throwable.toString(), throwable);
        if (throwable instanceof GameUpdateHappeningNowException) {
            showLoginWindow(getString(R.string.game_update_happening_now));
        } else if (throwable instanceof NullPointerException
                || throwable instanceof SessionExpiredException
                || (throwable instanceof CompositeException && ((CompositeException) throwable).getExceptions().get(0) instanceof SessionExpiredException)
        ) {
            showLoginWindow(null);
        } else {
            showLoginWindow(throwable.toString());
        }
    }
}