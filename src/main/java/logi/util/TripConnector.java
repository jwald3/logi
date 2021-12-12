package logi.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TripConnector implements Connector<Trip>{

    @Override
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logistics_planner", "root", "password");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Trip> getRecords() {
        ObservableList<Trip> tripsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM (SELECT t1.truckID, t1.capacity, t1.originFacilityID, t1.destinationFacilityID, " +
                "t1.startDate, t1.endDate, t1.originFacilityAddress, t1.tripId, facilities.facilityAddress AS destinationFacilityAddress FROM " +
                "(SELECT trips.truckID, trips.originFacilityID, trips.destinationFacilityID, trips.tripId, trips.startDate, " +
                "trips.endDate, trucks.capacity, " +
                "facilities.facilityName, facilities.facilityAddress AS originFacilityAddress FROM trips INNER JOIN trucks " +
                "ON trips.truckID = trucks.truckId INNER JOIN facilities ON trips.originFacilityID = facilities.facilityName " +
                ") AS t1 INNER JOIN facilities ON t1.destinationFacilityID = facilities.facilityName) AS t2";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Trip trip;
            while (rs.next()) {
                trip = new Trip(
                        new Truck(rs.getString("t2.truckId"), rs.getInt("t2.capacity")),
                        new Facility(rs.getString("t2.originFacilityId"),
                                rs.getString("t2.originFacilityAddress")),
                        new Facility(rs.getString("t2.destinationFacilityId"),
                                rs.getString("t2.destinationFacilityAddress")),
                        LocalDate.parse(rs.getString("t2.startDate")),
                        LocalDate.parse(rs.getString("t2.endDate")),
                        rs.getInt("t2.tripId")
                );
                tripsList.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripsList;
    }

    @Override
    public Trip getRecord(String primaryKey) {
        Connection conn = getConnection();
        String query = "SELECT * FROM (SELECT t1.truckID, t1.capacity, t1.originFacilityID, t1.destinationFacilityID, " +
                "t1.startDate, t1.endDate, t1.originFacilityAddress, t1.tripId, facilities.facilityAddress AS destinationFacilityAddress FROM " +
                "(SELECT trips.truckID, trips.originFacilityID, trips.destinationFacilityID, trips.tripId, trips.startDate, trips.endDate, trucks.capacity, " +
                "facilities.facilityName, facilities.facilityAddress AS originFacilityAddress FROM trips INNER JOIN trucks " +
                "ON trips.truckID = trucks.truckId INNER JOIN facilities ON trips.originFacilityID = facilities.facilityName " +
                ") AS t1 INNER JOIN facilities ON t1.destinationFacilityID = facilities.facilityName) AS t2 WHERE t2.tripId = '" + primaryKey + "';";
        Statement st;
        ResultSet rs;
        Trip trip = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                trip = new Trip (
                        new Truck(rs.getString("t2.truckId"), rs.getInt("t2.capacity")),
                        new Facility(rs.getString("t2.originFacilityId"),
                                rs.getString("t2.originFacilityAddress")),
                        new Facility(rs.getString("t2.destinationFacilityId"),
                                rs.getString("t2.destinationFacilityAddress")),
                        LocalDate.parse(rs.getString("t2.startDate")),
                        LocalDate.parse(rs.getString("t2.endDate")),
                        rs.getInt("t2.tripId")
                );
            }
        } catch(Exception e) {
            e.printStackTrace();

        }
        return trip;
    }

    @Override
    public ObservableList<Trip> getRelatedRecords(Trip trip) {
        return null;
    }

    @Override
    public void insertRecord(Trip trip) {
        String query = "INSERT INTO trips VALUES ('"
                + trip.getTruck() + "', '"
                + trip.getOriginFacility() + "', '"
                + trip.getDestinationFacility() + "', '"
                + trip.getStartDate() + "', '"
                + trip.getEndDate() + "', tripID = NULL);";

        executeQuery(query);
    }

    @Override
    public void updateRecord(Trip trip, String tripID) {
        ArrayList<String> queries = new ArrayList<>();

        queries.add("UPDATE trips " + "SET truckId = '" + trip.getTruck() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET originFacilityID = '" + trip.getOriginFacility() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET destinationFacilityID  = '" + trip.getDestinationFacility() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET startDate  = '" + trip.getStartDate() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET endDate  = '" + trip.getEndDate() + "' WHERE tripID = " + tripID + ";");

        for (String query: queries) {
            executeQuery(query);
        }
    }

    @Override
    public void deleteRecord(Trip trip, String primaryKey) {
        String query = "DELETE FROM trips WHERE truckId ='" + trip.getTruck().toString()
                + "' AND originFacilityID = '" + trip.getOriginFacility().toString()
                + "' AND destinationFacilityID = '" + trip.getDestinationFacility().toString() + "';";
        executeQuery(query);
    }
}