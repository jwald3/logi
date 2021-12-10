package logi.util;

import javafx.collections.ObservableList;
import logi.models.Trip;

import java.sql.Connection;

public interface Connector<T> {
    Connection getConnection();

    void executeQuery(String query);

    ObservableList<T> getRecords();

    ObservableList<Trip> getRelatedRecords(T object);

    void insertRecord(T object);

    void updateRecord(T object, String primaryKey);

    void deleteRecord(T object, String primaryKey);
}
