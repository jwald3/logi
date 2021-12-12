package logi.models;

public class Facility {
    private final String ID;
    private final String address;

    public Facility(String ID, String address) {
        this.ID = ID;
        this.address = address;
    }

    public String toString() {
        return this.ID;
    }

    public String getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }
}