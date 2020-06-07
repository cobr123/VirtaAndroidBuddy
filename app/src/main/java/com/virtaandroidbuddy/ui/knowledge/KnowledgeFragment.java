package com.virtaandroidbuddy.ui.knowledge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.virtaandroidbuddy.AppDelegate;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.data.Storage;
import com.virtaandroidbuddy.data.database.model.KnowledgeKind;

public class KnowledgeFragment extends Fragment {

    private KnowledgeViewModel mKnowledgeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mKnowledgeViewModel = ViewModelProviders.of(this).get(KnowledgeViewModel.class);
        final View root = inflater.inflate(R.layout.fr_knowledge, container, false);

        final TextView knowledgeManagementLvlTextView = root.findViewById(R.id.knowledge_management_lvl);
        final TextView knowledgeAdvertLvlTextView = root.findViewById(R.id.knowledge_advert_lvl);
        final TextView knowledgeItLvlTextView = root.findViewById(R.id.knowledge_it_lvl);
        final TextView knowledgeCarLvlTextView = root.findViewById(R.id.knowledge_car_lvl);
        final TextView knowledgeMedicineLvlTextView = root.findViewById(R.id.knowledge_medicine_lvl);
        final TextView knowledgeEducationalLvlTextView = root.findViewById(R.id.knowledge_educational_lvl);
        final TextView knowledgeRestaurantLvlTextView = root.findViewById(R.id.knowledge_restaurant_lvl);
        final TextView knowledgeServiceLvlTextView = root.findViewById(R.id.knowledge_service_lvl);
        final TextView knowledgeTradeLvlTextView = root.findViewById(R.id.knowledge_trade_lvl);
        final TextView knowledgeMiningLvlTextView = root.findViewById(R.id.knowledge_mining_lvl);
        final TextView knowledgeManufactureLvlTextView = root.findViewById(R.id.knowledge_manufacture_lvl);
        final TextView knowledgePowerLvlTextView = root.findViewById(R.id.knowledge_power_lvl);
        final TextView knowledgeAnimalLvlTextView = root.findViewById(R.id.knowledge_animal_lvl);
        final TextView knowledgeFishingLvlTextView = root.findViewById(R.id.knowledge_fishing_lvl);
        final TextView knowledgeFarmingLvlTextView = root.findViewById(R.id.knowledge_farming_lvl);
        final TextView knowledgeResearchLvlTextView = root.findViewById(R.id.knowledge_research_lvl);

        final TextView knowledgeManagementPercTextView = root.findViewById(R.id.knowledge_management_perc);
        final TextView knowledgeAdvertPercTextView = root.findViewById(R.id.knowledge_advert_perc);
        final TextView knowledgeItPercTextView = root.findViewById(R.id.knowledge_it_perc);
        final TextView knowledgeCarPercTextView = root.findViewById(R.id.knowledge_car_perc);
        final TextView knowledgeMedicinePercTextView = root.findViewById(R.id.knowledge_medicine_perc);
        final TextView knowledgeEducationalPercTextView = root.findViewById(R.id.knowledge_educational_perc);
        final TextView knowledgeRestaurantPercTextView = root.findViewById(R.id.knowledge_restaurant_perc);
        final TextView knowledgeServicePercTextView = root.findViewById(R.id.knowledge_service_perc);
        final TextView knowledgeTradePercTextView = root.findViewById(R.id.knowledge_trade_perc);
        final TextView knowledgeMiningPercTextView = root.findViewById(R.id.knowledge_mining_perc);
        final TextView knowledgeManufacturePercTextView = root.findViewById(R.id.knowledge_manufacture_perc);
        final TextView knowledgePowerPercTextView = root.findViewById(R.id.knowledge_power_perc);
        final TextView knowledgeAnimalPercTextView = root.findViewById(R.id.knowledge_animal_perc);
        final TextView knowledgeFishingPercTextView = root.findViewById(R.id.knowledge_fishing_perc);
        final TextView knowledgeFarmingPercTextView = root.findViewById(R.id.knowledge_farming_perc);
        final TextView knowledgeResearchPercTextView = root.findViewById(R.id.knowledge_research_perc);

        mKnowledgeViewModel.getData(getContext(), obtainStorage()).observe(getViewLifecycleOwner(), knowledgeMap -> {
            knowledgeManagementLvlTextView.setText(knowledgeMap.get(KnowledgeKind.management).getLvlString());
            knowledgeAdvertLvlTextView.setText(knowledgeMap.get(KnowledgeKind.advert).getLvlString());
            knowledgeItLvlTextView.setText(knowledgeMap.get(KnowledgeKind.it).getLvlString());
            knowledgeCarLvlTextView.setText(knowledgeMap.get(KnowledgeKind.car).getLvlString());
            knowledgeMedicineLvlTextView.setText(knowledgeMap.get(KnowledgeKind.medicine).getLvlString());
            knowledgeEducationalLvlTextView.setText(knowledgeMap.get(KnowledgeKind.educational).getLvlString());
            knowledgeRestaurantLvlTextView.setText(knowledgeMap.get(KnowledgeKind.restaurant).getLvlString());
            knowledgeServiceLvlTextView.setText(knowledgeMap.get(KnowledgeKind.service).getLvlString());
            knowledgeTradeLvlTextView.setText(knowledgeMap.get(KnowledgeKind.trade).getLvlString());
            knowledgeMiningLvlTextView.setText(knowledgeMap.get(KnowledgeKind.mining).getLvlString());
            knowledgeManufactureLvlTextView.setText(knowledgeMap.get(KnowledgeKind.manufacture).getLvlString());
            knowledgePowerLvlTextView.setText(knowledgeMap.get(KnowledgeKind.power).getLvlString());
            knowledgeAnimalLvlTextView.setText(knowledgeMap.get(KnowledgeKind.animal).getLvlString());
            knowledgeFishingLvlTextView.setText(knowledgeMap.get(KnowledgeKind.fishing).getLvlString());
            knowledgeFarmingLvlTextView.setText(knowledgeMap.get(KnowledgeKind.farming).getLvlString());
            knowledgeResearchLvlTextView.setText(knowledgeMap.get(KnowledgeKind.research).getLvlString());

            knowledgeManagementPercTextView.setText(knowledgeMap.get(KnowledgeKind.management).getProgressString());
            knowledgeAdvertPercTextView.setText(knowledgeMap.get(KnowledgeKind.advert).getProgressString());
            knowledgeItPercTextView.setText(knowledgeMap.get(KnowledgeKind.it).getProgressString());
            knowledgeCarPercTextView.setText(knowledgeMap.get(KnowledgeKind.car).getProgressString());
            knowledgeMedicinePercTextView.setText(knowledgeMap.get(KnowledgeKind.medicine).getProgressString());
            knowledgeEducationalPercTextView.setText(knowledgeMap.get(KnowledgeKind.educational).getProgressString());
            knowledgeRestaurantPercTextView.setText(knowledgeMap.get(KnowledgeKind.restaurant).getProgressString());
            knowledgeServicePercTextView.setText(knowledgeMap.get(KnowledgeKind.service).getProgressString());
            knowledgeTradePercTextView.setText(knowledgeMap.get(KnowledgeKind.trade).getProgressString());
            knowledgeMiningPercTextView.setText(knowledgeMap.get(KnowledgeKind.mining).getProgressString());
            knowledgeManufacturePercTextView.setText(knowledgeMap.get(KnowledgeKind.manufacture).getProgressString());
            knowledgePowerPercTextView.setText(knowledgeMap.get(KnowledgeKind.power).getProgressString());
            knowledgeAnimalPercTextView.setText(knowledgeMap.get(KnowledgeKind.animal).getProgressString());
            knowledgeFishingPercTextView.setText(knowledgeMap.get(KnowledgeKind.fishing).getProgressString());
            knowledgeFarmingPercTextView.setText(knowledgeMap.get(KnowledgeKind.farming).getProgressString());
            knowledgeResearchPercTextView.setText(knowledgeMap.get(KnowledgeKind.research).getProgressString());
        });
        return root;
    }

    private Storage obtainStorage() {
        return ((AppDelegate) getActivity().getApplicationContext()).getStorage();
    }
}