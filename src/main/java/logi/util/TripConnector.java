package logi.util;

import logi.models.Trip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
    public void deleteRecord(Trip trip, String tripID) {
        String query = "DELETE FROM trips WHERE tripID = " + tripID + ";";
        executeQuery(query);
    }
}
