package logi.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;

import java.sql.*;
import java.util.ArrayList;

public class TruckConnector implements Connector<Truck> {

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
    public ObservableList<Truck> getRecords() {
        ObservableList<Truck> trucksList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM trucks";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Truck truck;
            while (rs.next()) {
                truck = new Truck(
                        rs.getString("truckID"),
                        rs.getInt("capacity")
                );
                trucksList.add(truck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trucksList;
    }

    @Override
    public Truck getRecord(String primaryKey) {
        Connection conn = getConnection();
        String query = "SELECT * FROM trucks WHERE truckId = '" + primaryKey + "';";
        Statement st;
        ResultSet rs;
        Truck truck = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                truck = new Truck(
                        rs.getString("truckID"),
                        rs.getInt("capacity")
                );
            }
        } catch(Exception e) {
            e.printStackTrace();

        }
        return truck;
    }

    @Override
    public ObservableList<Trip> getRelatedRecords(Truck truck) {
        DateUtils dateUtils = new DateUtils();

        ObservableList<Trip> tripsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM trucks " +
                "INNER JOIN trips ON trucks.truckID = trips.truckID " +
                "WHERE trucks.truckID = '" + truck.getId() + "';";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Trip trip;
            while (rs.next()) {
                trip = new Trip (
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
    public void insertRecord(Truck truck) {
        String query = "INSERT INTO trucks VALUES ('"
                + truck.getId() + "', "
                + truck.getCapacity() + ")";
        executeQuery(query);
    }

    @Override
    public void updateRecord(Truck truck, String originalTruckID) {
        ArrayList<String> queries = new ArrayList<>();


        queries.add("UPDATE trucks " + "SET truckID = '" + truck.getId() + "' WHERE truckID = '" + originalTruckID + "';");
        queries.add("UPDATE trucks " + "SET capacity = " + truck.getCapacity() + " WHERE truckID = '" + originalTruckID + "';");
        queries.add("UPDATE trips SET truckID ='" + truck.getId() + "' WHERE truckID = '" + originalTruckID + "';");


        for (String query: queries) {
            executeQuery(query);
        }
    }

    @Override
    public void deleteRecord(Truck truck, String originalTruckID) {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("DELETE FROM trucks WHERE truckID = '" + originalTruckID + "';");
        queries.add("DELETE FROM trips WHERE truckID = '" +  originalTruckID + "';");

        for (String query: queries) {
            executeQuery(query);
        }
    }
}