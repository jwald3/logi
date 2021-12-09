package logi.util;

import javafx.collections.ObservableList;

import java.sql.Connection;

public interface Connector<T> {
    Connection getConnection();

    void executeQuery(String query);

    ObservableList<T> getRecords();

    void insertRecord(T object);

    void updateRecord(T object, String primaryKey);

    public void deleteRecord(T object, String primaryKey);
}
