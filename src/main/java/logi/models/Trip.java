package logi.models;

import java.time.LocalDate;

public class Trip {
    private Truck truck;
    private Facility originFacility;
    private Facility destinationFacility;
    private LocalDate startDate;
    private int tripId;

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDate startDate, int tripId) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.tripId = tripId;
    }

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDate startDate) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
    }

    public Truck getTruck() {
        return truck;
    }

    public Facility getOriginFacility() {
        return originFacility;
    }

    public Facility getDestinationFacility() {
        return destinationFacility;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getTripId() {
        return this.tripId;
    }

    public String toString() {
        return this.truck.toString() + " (" + this.originFacility + " -> " + this.destinationFacility + ")";
    }
}