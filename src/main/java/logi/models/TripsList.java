package logi.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logi.models.Trip;

public class TripsList {
    private final ObservableList<Trip> trips;

    public TripsList() {
        this.trips = FXCollections.observableArrayList();
    }

    public ObservableList<Trip> getTrips() {
        return this.trips;
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }
}
