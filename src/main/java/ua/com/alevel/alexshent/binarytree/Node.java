package ua.com.alevel.alexshent.binarytree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Node<T> {
    private T value;
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Comparator<T> comparator;

    public Node(T value, Comparator<T> comparator) {
        this.value = value;
        this.comparator = comparator;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(Node<T> node) {
        if (comparator.compare(node.getValue(), value) < 0) {
            if (leftChild != null) {
                leftChild.insert(node);
            } else {
                leftChild = node;
            }
        } else if (comparator.compare(node.getValue(), value) > 0) {
            if (rightChild != null) {
                rightChild.insert(node);
            } else {
                rightChild = node;
            }
        }
    }

    public List<T> getChildValues() {
        List<T> list = new ArrayList<>();
        if (leftChild != null) {
            list.add(leftChild.getValue());
            list.addAll(leftChild.getChildValues());
        }
        if (rightChild != null) {
            list.add(rightChild.getValue());
            list.addAll(rightChild.getChildValues());
        }
        return list;
    }
}
