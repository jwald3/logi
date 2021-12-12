package logi.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trip {
    private final Truck truck;
    private final Facility originFacility;
    private final Facility destinationFacility;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private int tripId;
    public String stringStartDate;


    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDateTime startDate, LocalDateTime endDate, int tripId) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripId = tripId;
        setStringStartDate(convertDateFormat(startDate));
    }

    public Trip(Truck truck, Facility originFacility, Facility destinationFacility, LocalDateTime startDate, LocalDateTime endDate) {
        this.truck = truck;
        this.originFacility = originFacility;
        this.destinationFacility = destinationFacility;
        this.startDate = startDate;
        this.endDate = endDate;
        setStringStartDate(convertDateFormat(startDate));
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

    public int getTripId() {
        return this.tripId;
    }

    public String toString() {
        return this.truck.toString() + " (" + this.originFacility + " -> " + this.destinationFacility + ")";
    }

    private static String convertDateFormat(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = localDateTime.format(formatter);


        LocalDateTime formattedDateTime = LocalDateTime.parse(formatDateTime, formatter);

        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return formattedDateTime.format(newFormatter);
    }

    public void setStringStartDate(String stringStartDate) {
        this.stringStartDate = stringStartDate;
    }
}