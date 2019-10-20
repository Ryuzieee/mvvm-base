package com.example.ryuji_mvvm_architecture.sub.recyclerView;

import java.util.ArrayList;

public class SectionDataModel {

    private ArrayList<SingleItemModel> allItemsInSection;

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

}
