package com.virtaandroidbuddy.ui.unit.summary;

import com.virtaandroidbuddy.common.BaseView;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;

public interface UnitSummaryView extends BaseView {

    void showUnitSummary(UnitSummaryJson unitSummaryJson);
}