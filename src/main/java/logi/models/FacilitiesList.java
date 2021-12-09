package logi.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FacilitiesList {
    private final ObservableList<Facility> facilities;

    public FacilitiesList() {
        this.facilities = FXCollections.observableArrayList();
    }

    public ObservableList<Facility> getFacilities() {
        return this.facilities;
    }

    public void addFacility(Facility facility) {
        this.facilities.add(facility);
    }
}