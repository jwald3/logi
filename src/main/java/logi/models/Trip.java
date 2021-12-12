package logi.models;

import java.time.LocalDate;

public class Trip {
    private final Truck truck;
    private final Facility originFacility;
    private final Facility destinationFacility;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private int tripId;

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDate startDate, LocalDate endDate, int tripId) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripId = tripId;
    }

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDate startDate, LocalDate endDate) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDate getEndDate() { return  endDate; }

    public int getTripId() {
        return this.tripId;
    }

    public String toString() {
        return this.truck.toString() + " (" + this.originFacility + " -> " + this.destinationFacility + ")";
    }
}