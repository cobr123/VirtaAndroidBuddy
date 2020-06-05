package com.virtaandroidbuddy.ui.unitlist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;


public class UnitListHolder extends RecyclerView.ViewHolder {

    private ImageView mCountrySymbol;
    private TextView mRegionName;
    private TextView mCityName;

    private TextView mUnitId;
    private TextView mUnitName;
    private TextView mUnitEff;
    private ImageView mUnitWorkersInHoliday;
    private ImageView mUnitTypeSymbol;

    public UnitListHolder(@NonNull View itemView) {
        super(itemView);

        mCountrySymbol = itemView.findViewById(R.id.iv_country_symbol);
        mRegionName = itemView.findViewById(R.id.tv_region_name);
        mCityName = itemView.findViewById(R.id.tv_city_name);

        mUnitId = itemView.findViewById(R.id.tv_unit_id);
        mUnitName = itemView.findViewById(R.id.tv_unit_name);
        mUnitEff = itemView.findViewById(R.id.tv_unit_eff);
        mUnitWorkersInHoliday = itemView.findViewById(R.id.iv_workers_in_holiday);
        mUnitTypeSymbol = itemView.findViewById(R.id.iv_unit_type_symbol);
    }

    public void bind(Context context, UnitListDataJson unit, UnitListAdapter.OnItemClickListener onItemClickListener) {
        Picasso.get().load(context.getString(R.string.base_url) + "img/flag/" + unit.getCountrySymbol() + ".png").into(mCountrySymbol);
        mRegionName.setText(unit.getRegionName());
        mCityName.setText(unit.getCityName());

        mUnitId.setText(unit.getId());
        mUnitName.setText(unit.getName());
        if (unit.isWorkersInHoliday()) {
            mUnitEff.setVisibility(View.GONE);
            mUnitWorkersInHoliday.setVisibility(View.VISIBLE);
            Picasso.get().load(context.getString(R.string.base_url) + "img/unit_indicator/workers_in_holiday.gif").into(mUnitWorkersInHoliday);
        } else {
            mUnitWorkersInHoliday.setVisibility(View.GONE);
            mUnitEff.setVisibility(View.VISIBLE);
            mUnitEff.setText(unit.getUnitProductivityString());
        }
        Picasso.get().load(context.getString(R.string.base_url) + "img/unit_types/" + unit.getUnitTypeSymbol() + ".gif").into(mUnitTypeSymbol);

        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(unit));
        }
    }
}
