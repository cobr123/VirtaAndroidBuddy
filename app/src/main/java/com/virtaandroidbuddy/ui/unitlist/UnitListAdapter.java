package com.virtaandroidbuddy.ui.unitlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.api.model.UnitListJson;

import java.util.ArrayList;
import java.util.List;


public class UnitListAdapter extends RecyclerView.Adapter<UnitListHolder> {

    private final List<UnitListJson> mUnitList = new ArrayList<>();

    @NonNull
    @Override
    public UnitListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_unit, parent, false);
        return new UnitListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitListHolder holder, int position) {
        holder.bind(mUnitList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUnitList.size();
    }

    public void addData(UnitListJson data, boolean refresh) {
        if (refresh) {
            mUnitList.clear();
        }
        mUnitList.add(data);
        notifyDataSetChanged();
    }
}
