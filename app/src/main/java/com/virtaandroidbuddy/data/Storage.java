package com.virtaandroidbuddy.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.virtaandroidbuddy.data.api.model.UnitListDataJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.data.database.VirtonomicaDao;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Session;
import com.virtaandroidbuddy.data.database.model.Unit;
import com.virtaandroidbuddy.data.database.model.UnitSummary;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private VirtonomicaDao mVirtonomicaDao;

    public Storage(VirtonomicaDao virtonomicaDao) {
        mVirtonomicaDao = virtonomicaDao;
    }

    public void insertSession(Session session) {
        mVirtonomicaDao.insertSession(session);
    }

    public void insertUnits(UnitListJson unitListJson, Session session) {
        final List<UnitListDataJson> data = unitListJson.getData();
        final List<Unit> unitList = new ArrayList<>(data.size());
        for (UnitListDataJson item : data) {
            final Unit unit = new Unit();
            unit.setCompanyId(session.getCompanyId());
            unit.setId(item.getId());
            unit.setRealm(session.getRealm());
            unit.setName(item.getName());
            unitList.add(unit);
        }
        mVirtonomicaDao.insertUnits(unitList);
    }

    public UnitListJson getUnitList(Session session) {
        final List<Unit> unitList = mVirtonomicaDao.getUnitList(session.getRealm(), session.getCompanyId());
        final List<UnitListDataJson> data = new ArrayList<>(unitList.size());
        for (Unit unit : unitList) {
            final UnitListDataJson dataItem = new UnitListDataJson();
            dataItem.setId(unit.getId());
            dataItem.setName(unit.getName());
            data.add(dataItem);
        }
        final UnitListJson unitListJson = new UnitListJson();
        unitListJson.setData(data);
        return unitListJson;
    }

    public void deleteSession(Session session) {
        mVirtonomicaDao.deleteSession(session);
    }

    public Session getSession() {
        return mVirtonomicaDao.getSession();
    }

    public void insertUnitSummary(UnitSummaryJson unitSummaryJson, Session session) {
        final UnitSummary unitSummary = new UnitSummary();
        unitSummary.setId(unitSummaryJson.getId());
        unitSummary.setCompanyId(session.getCompanyId());
        unitSummary.setName(unitSummaryJson.getName());
        unitSummary.setRealm(session.getRealm());
        mVirtonomicaDao.insertUnitSummary(unitSummary);
    }

    public UnitSummaryJson getUnitSummary(String unitId, Session session) {
        final UnitSummary unitSummary = mVirtonomicaDao.getUnitSummary(session.getRealm(), unitId);
        final UnitSummaryJson unitSummaryJson = new UnitSummaryJson();
        unitSummaryJson.setId(unitSummary.getId());
        unitSummaryJson.setName(unitSummary.getName());
        return unitSummaryJson;
    }

    public LiveData<Knowledge> getKnowledge(String realm, String userId) {
        final LiveData<Knowledge> knowledgeLiveData = mVirtonomicaDao.getKnowledge(realm, userId);
        if (knowledgeLiveData.getValue() == null) {
            final MutableLiveData<Knowledge> emptyKnowledgeLiveData = new MutableLiveData<>();
            emptyKnowledgeLiveData.setValue(new Knowledge());
            return emptyKnowledgeLiveData;
        } else {
            return knowledgeLiveData;
        }
    }

    public void insertKnowledge(Knowledge knowledge) {
        mVirtonomicaDao.insertKnowledge(knowledge);
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }
}
