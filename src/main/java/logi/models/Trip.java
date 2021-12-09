package logi.models;

import java.time.LocalDate;

public class Trip {
    private Truck truck;
    private Facility originFacility;
    private Facility destinationFacility;
    private LocalDate startDate;
    private String facilityID;

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDate startDate, String facilityID) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.facilityID = facilityID;
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

    public String getFacilityID() {
        return facilityID;
    }

    public String toString() {
        return this.truck.toString() + " (" + this.originFacility + " -> " + this.destinationFacility + ")";
    }
}
