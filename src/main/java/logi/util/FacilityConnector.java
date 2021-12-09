package logi.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logi.models.Facility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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


        for (String query: queries) {
            executeQuery(query);
        }
    }

    @Override
    public void deleteRecord(Facility facility, String primaryKey) {

    }
}
