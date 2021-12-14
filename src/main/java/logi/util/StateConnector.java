package logi.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import logi.models.State;
import logi.models.Trip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StateConnector implements Connector <State> {

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
    public ObservableList<State> getRecords() {
        ObservableList<State> statesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM states";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            State state;
            while (rs.next()) {
                state = new State(
                        rs.getString("stateName"),
                        rs.getString("stateAbbrev")
                );
                statesList.add(state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statesList;
    }

    @Override
    public State getRecord(String primaryKey) {
        return null;
    }

    @Override
    public ObservableList<Trip> getRelatedRecords(State object) {
        return null;
    }

    @Override
    public void insertRecord(State object) {

    }

    @Override
    public void updateRecord(State object, String primaryKey) {

    }

    @Override
    public void deleteRecord(State object, String primaryKey) {

    }
}
