package com.virtaandroidbuddy.ui.unitlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;

import java.util.ArrayList;
import java.util.List;


public class UnitListAdapter extends RecyclerView.Adapter<UnitListHolder> {

    public interface OnItemClickListener {
        void onItemClick(UnitListDataJson unit);
    }
    @NonNull
    private final List<UnitListDataJson> mUnitList = new ArrayList<>();
    private final OnItemClickListener mOnItemClickListener;

    public UnitListAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UnitListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_unit, parent, false);
        return new UnitListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitListHolder holder, int position) {
        holder.bind(mUnitList.get(position), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mUnitList.size();
    }

    public void addData(List<UnitListDataJson> data, boolean refresh) {
        if (refresh) {
            mUnitList.clear();
        }
        mUnitList.addAll(data);
        notifyDataSetChanged();
    }
}
