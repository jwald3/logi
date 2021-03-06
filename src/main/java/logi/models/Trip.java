package logi.models;

import logi.util.DateUtils;

import java.time.LocalDateTime;

public class Trip {
    private final Truck truck;
    private final Facility originFacility;
    private final Facility destinationFacility;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private String transitTime;
    private int tripId;


    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDateTime startDate, LocalDateTime endDate, String transitTime, int tripId) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripId = tripId;
        this.transitTime = transitTime;
    }

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDateTime startDate, LocalDateTime endDate, int tripId) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripId = tripId;
    }

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDateTime startDate, LocalDateTime endDate) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() { return endDate; }

    public String getTransitTime() { return transitTime; }

    public int getTripId() {
        return this.tripId;
    }

    public void setTransitTime() {
        this.transitTime = findTransitTime(this.startDate, this.endDate);
    }

    private static String findTransitTime(LocalDateTime start, LocalDateTime end) {
        DateUtils dateUtils = new DateUtils();

        return  dateUtils.getTransitTime(start, end);
    }

    public String toString() {
        return this.truck.toString() + " (" + this.originFacility + " -> " + this.destinationFacility + ")";
    }
}