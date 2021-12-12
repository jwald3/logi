package logi.models;

public class Truck {
    private final String id;
    private final int capacity;

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