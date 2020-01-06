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

        mKnowledgeViewModel.getData(getContext(), obtainStorage()).observe(this, knowledge -> {
            knowledgeManagementLvlTextView.setText(knowledge.getManagementLvlString());
            knowledgeAdvertLvlTextView.setText(knowledge.getAdvertLvlString());
            knowledgeItLvlTextView.setText(knowledge.getItLvlString());
            knowledgeCarLvlTextView.setText(knowledge.getCarLvlString());
            knowledgeMedicineLvlTextView.setText(knowledge.getMedicineLvlString());
            knowledgeEducationalLvlTextView.setText(knowledge.getEducationalLvlString());
            knowledgeRestaurantLvlTextView.setText(knowledge.getRestaurantLvlString());
            knowledgeServiceLvlTextView.setText(knowledge.getServiceLvlString());
            knowledgeTradeLvlTextView.setText(knowledge.getTradeLvlString());
            knowledgeMiningLvlTextView.setText(knowledge.getMiningLvlString());
            knowledgeManufactureLvlTextView.setText(knowledge.getManufactureLvlString());
            knowledgePowerLvlTextView.setText(knowledge.getPowerLvlString());
            knowledgeAnimalLvlTextView.setText(knowledge.getAnimalLvlString());
            knowledgeFishingLvlTextView.setText(knowledge.getFishingLvlString());
            knowledgeFarmingLvlTextView.setText(knowledge.getFarmingLvlString());
            knowledgeResearchLvlTextView.setText(knowledge.getResearchLvlString());
        });
        return root;
    }

    private Storage obtainStorage() {
        return ((AppDelegate) getActivity().getApplicationContext()).getStorage();
    }
}