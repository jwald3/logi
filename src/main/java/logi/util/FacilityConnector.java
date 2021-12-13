package logi.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;

import java.sql.*;
import java.util.ArrayList;

public class FacilityConnector implements Connector <Facility> {

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
    public ObservableList<Facility> getRecords() {
        ObservableList<Facility> facilitiesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM facilities";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Facility facility;
            while (rs.next()) {
                facility = new Facility(
                        rs.getString("facilityName"),
                        rs.getString("facilityAddress")
                );
                facilitiesList.add(facility);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facilitiesList;
    }

    @Override
    public Facility getRecord(String primaryKey) {
        Connection conn = getConnection();
        String query = "SELECT * FROM facilities WHERE facilityName = '" + primaryKey + "';";
        Statement st;
        ResultSet rs;
        Facility facility = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                facility = new Facility(
                        rs.getString("facilityName"),
                        rs.getString("facilityAddress")
                );
            }
        } catch(Exception e) {
            e.printStackTrace();

        }
        return facility;
    }

    @Override
    public ObservableList<Trip> getRelatedRecords(Facility facility) {
        DateUtils dateUtils = new DateUtils();

        ObservableList<Trip> tripsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * " +
                "FROM facilities " +
                "INNER JOIN trips " +
                "ON facilities.facilityName = trips.originFacilityID " +
                "OR facilities.facilityName = trips.destinationFacilityID " +
                "WHERE facilities.facilityName = '" + facility.getID() + "';";
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
                        dateUtils.convertDateFormat(rs.getTimestamp("startDate")),
                        dateUtils.convertDateFormat(rs.getTimestamp("endDate")),
                        dateUtils.getTransitTime(dateUtils.convertDateFormat(rs.getTimestamp("startDate")),
                                dateUtils.convertDateFormat(rs.getTimestamp("endDate"))),
                        rs.getInt("tripID")
                );
                tripsList.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tripsList;
    }

    @Override
    public void insertRecord(Facility facility) {
        String query = "INSERT INTO facilities VALUES ('"
                + facility.getID() + "', '"
                + facility.getAddress() + "')";
        executeQuery(query);
    }

    @Override
    public void updateRecord(Facility facility, String primaryKey) {
        ArrayList<String> queries = new ArrayList<>();


        queries.add("UPDATE facilities " + "SET facilityName = '" + facility.getID() + "' WHERE facilityName = '" + primaryKey + "';");
        queries.add("UPDATE facilities " + "SET facilityAddress = '" + facility.getAddress() + "' WHERE facilityName = '" + primaryKey + "';");
        queries.add("UPDATE trips " + "SET originFacilityID = '" + facility.getID() + "' WHERE originFacilityID = '" + primaryKey + "';");
        queries.add("UPDATE trips " + "SET destinationFacilityID = '" + facility.getID() + "' WHERE destinationFacilityID = '" + primaryKey + "';");

        for (String query: queries) {
            executeQuery(query);
        }
    }

    @Override
    public void deleteRecord(Facility facility, String primaryKey) {
        ArrayList<String> queries = new ArrayList<>();

        queries.add("DELETE FROM facilities WHERE facilityName = '" + primaryKey + "';");
        queries.add("DELETE FROM trips WHERE originFacilityID = '" + primaryKey + "';");
        queries.add("DELETE FROM trips WHERE destinationFacilityID = '" +  primaryKey + "';");

        for (String query: queries) {
            executeQuery(query);
        }

    }
}