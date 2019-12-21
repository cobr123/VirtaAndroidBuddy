package com.virtaandroidbuddy.ui.unitlist;

import com.virtaandroidbuddy.common.BaseView;
import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;


public interface UnitListView extends BaseView {

    void showUnitList(UnitListJson unitListJson);

    void openUnitSummary(UnitListDataJson unitListDataJson);
}
