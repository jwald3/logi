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
        String query = "SELECT * FROM trips";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Trip trip;
            while (rs.next()) {
                trip = new Trip(
                        new Truck(rs.getString("truckId"), 0),
                        new Facility(rs.getString("originFacilityId"), ""),
                        new Facility(rs.getString("destinationFacilityId"), ""),
                        LocalDate.parse(rs.getString("startDate")),
                        rs.getString("tripID")
                );
                tripsList.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripsList;
    }

    @Override
    public void insertRecord(Trip trip) {
        String query = "INSERT INTO trips VALUES ('"
                + trip.getTruck() + "', '"
                + trip.getOriginFacility() + "', '"
                + trip.getDestinationFacility() + "', '"
                + trip.getStartDate() + "', tripID = NULL);";

        executeQuery(query);
    }

    @Override
    public void updateRecord(Trip trip, String tripID) {
        ArrayList<String> queries = new ArrayList<>();

        queries.add("UPDATE trips " + "SET truckId = '" + trip.getTruck() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET originFacilityID = '" + trip.getOriginFacility() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET destinationFacilityID  = '" + trip.getDestinationFacility() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET startDate  = '" + trip.getStartDate() + "' WHERE tripID = " + tripID + ";");


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
