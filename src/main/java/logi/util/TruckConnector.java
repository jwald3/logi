package logi.util;

import logi.models.Truck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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


        for (String query: queries) {
            executeQuery(query);
        }
    }

    @Override
    public void deleteRecord(Truck truck, String originalTruckID) {
        String query = "DELETE FROM trucks WHERE truckID = '" + originalTruckID + "';";
        executeQuery(query);
    }
}
