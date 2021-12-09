package logi.models;

public class Truck {
    private String id;
    private int capacity;

    public Truck(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String toString() {
        return this.id;
    }
}