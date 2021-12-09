package logi.models;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import logi.models.Truck;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TrucksList implements ObservableList<Truck> {
    private final ObservableList<Truck> trucks;

    public TrucksList() {
        this.trucks = FXCollections.observableArrayList();
    }

    public ObservableList<Truck> getTrucks() {
        return this.trucks;
    }

    public void addTruck(Truck truck) {
        this.trucks.add(truck);
    }

    @Override
    public void addListener(ListChangeListener<? super Truck> listChangeListener) {

    }

    @Override
    public void removeListener(ListChangeListener<? super Truck> listChangeListener) {

    }

    @Override
    public boolean addAll(Truck... trucks) {
        return false;
    }

    @Override
    public boolean setAll(Truck... trucks) {
        return false;
    }

    @Override
    public boolean setAll(Collection<? extends Truck> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Truck... trucks) {
        return false;
    }

    @Override
    public boolean retainAll(Truck... trucks) {
        return false;
    }

    @Override
    public void remove(int i, int i1) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Truck> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Truck truck) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Truck> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Truck> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Truck get(int index) {
        return null;
    }

    @Override
    public Truck set(int index, Truck element) {
        return null;
    }

    @Override
    public void add(int index, Truck element) {

    }

    @Override
    public Truck remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Truck> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Truck> listIterator(int index) {
        return null;
    }

    @Override
    public List<Truck> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
