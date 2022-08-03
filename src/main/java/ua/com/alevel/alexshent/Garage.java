package ua.com.alevel.alexshent;

import java.time.LocalDateTime;
import java.util.*;

public class Garage implements Iterable<Refresh> {

    protected MyLinkedList<Refresh> list;

    public Garage() {
        list = new MyLinkedList<>();
    }

    public void add(Refresh refresh) {
        list.addFirst(refresh);
        Collections.sort(list);
    }

    public Refresh findById(int id) {
        int index =
        Collections.binarySearch(
                list,
                new Refresh(null, id, null),
                Comparator.comparingInt(Refresh::getId)
        );
        return list.get(index);
    }

    public void delete(int id) {
        Refresh target = this.findById(id);
        list.remove(target);
    }

    public void replace(int id, Refresh refresh) {
        Refresh target = this.findById(id);
        int index = list.indexOf(target);
        list.set(index, refresh);
    }

    public int getSize() {
        return list.size();
    }

    public LocalDateTime getFirstDate() {
        return list.getFirst().createdAt;
    }

    public LocalDateTime getLastDate() {
        return list.getLast().createdAt;
    }

    @Override
    public Iterator<Refresh> iterator() {
        return this.list.iterator();
    }
}
