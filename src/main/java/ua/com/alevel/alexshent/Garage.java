package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.Vehicle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Garage<T extends Vehicle> implements Iterable<T> {
    private static final String DATE_TIME_PATTERN = "d.MM.yyyy H:mm";
    private int size;
    private Node head;

    public void add(T vehicle, int refreshNumber) {
        if (head == null) {
            head = new Node(vehicle, null, null, refreshNumber);
        } else {
            head = new Node(vehicle, null, head, refreshNumber);
            head.nextNode.previousNode = head;
        }
        size++;
    }

    private Node findNodeByRefreshNumber(int refreshNumber) {
        Node node = head;
        while (node != null) {
            if (node.refreshNumber == refreshNumber) {
                return node;
            }
            node = node.nextNode;
        }
        throw new IllegalArgumentException();
    }

    public T removeNodeByRefreshNumber(int refreshNumber) {
        Node node = findNodeByRefreshNumber(refreshNumber);
        if (node.previousNode == null) {
            head = node.nextNode;
        } else {
            node.previousNode.nextNode = node.nextNode;
        }
        if (node.nextNode != null) {
            node.nextNode.previousNode = node.previousNode;
        }
        size--;
        return node.vehicle;
    }

    public T getVehicleForRefreshNumber(int refreshNumber) {
        return findNodeByRefreshNumber(refreshNumber).vehicle;
    }

    public T setVehicleForRefreshNumber(int refreshNumber, T vehicle) {
        Node node = findNodeByRefreshNumber(refreshNumber);
        node.vehicle = vehicle;
        return node.vehicle;
    }

    public int getSize() {
        return size;
    }

    public String getFirstDate() {
        Node node = head;
        LocalDateTime localDateTime = LocalDateTime.now();
        while (node != null) {
            if (localDateTime.isBefore(node.createdAt)) {
                localDateTime = node.createdAt;
            }
            node = node.nextNode;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public String getLastDate() {
        Node node = head;
        LocalDateTime localDateTime = LocalDateTime.now();
        while (node != null) {
            if (localDateTime.isAfter(node.createdAt)) {
                localDateTime = node.createdAt;
            }
            node = node.nextNode;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    @Override
    public Iterator<T> iterator() {
        return new GarageIterator();
    }

    private class GarageIterator implements Iterator<T> {
        private Node cursor = head;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public T next() {
            T vehicle = cursor.vehicle;
            if (!hasNext()) {
                throw new NoSuchElementException("next element is not available");
            }
            cursor = cursor.nextNode;
            return vehicle;
        }
    }

    private class Node {
        private T vehicle;
        private Node previousNode;
        private Node nextNode;
        private final int refreshNumber;
        private final LocalDateTime createdAt;

        @Override
        public String toString() {
            return "Node {" +
                    "vehicle = " + vehicle.toString() +
                    '}';
        }

        public Node(T vehicle, Node previousNode, Node nextNode, int refreshNumber) {
            this.vehicle = vehicle;
            this.previousNode = previousNode;
            this.nextNode = nextNode;
            this.refreshNumber = refreshNumber;
            this.createdAt = LocalDateTime.now();
        }
    }
}
