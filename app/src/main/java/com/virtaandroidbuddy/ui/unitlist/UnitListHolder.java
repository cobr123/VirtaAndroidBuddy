package com.virtaandroidbuddy.ui.unitlist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.api.model.UnitListDataJson;


public class UnitListHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mId;

    public UnitListHolder(@NonNull View itemView) {
        super(itemView);

        mName = itemView.findViewById(R.id.tv_name);
        mId = itemView.findViewById(R.id.tv_id);
    }

    public void bind(UnitListDataJson unit) {
        mName.setText(unit.getName());
        mId.setText(unit.getId());
    }
}
