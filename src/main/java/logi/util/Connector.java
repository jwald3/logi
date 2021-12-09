package logi.util;

import java.sql.Connection;

public interface Connector<T> {
    public Connection getConnection();

    public void executeQuery(String query);

    public void insertRecord(T object);

    public void updateRecord(T object, String primaryKey);

    public void deleteRecord(T object, String primaryKey);
}
