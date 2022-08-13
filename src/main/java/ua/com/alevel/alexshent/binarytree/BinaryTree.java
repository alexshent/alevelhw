package ua.com.alevel.alexshent.binarytree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinaryTree<T extends Costly> {
    private final Node<T> rootNode;
    private final Comparator<T> comparator;

    public BinaryTree(Comparator<T> comparator) {
        this.rootNode = new Node<>(null);
        this.comparator = comparator;
    }

    public boolean contains(T value) {
        return contains(rootNode, value);
    }

    public void add(T value) {
        add(rootNode, value);
    }

    private boolean contains(Node<T> node, T value) {
        if (node == null) {
            return false;
        }
        if (comparator.compare(value, node.getValue()) == 0) {
            return true;
        }
        if (comparator.compare(value, node.getValue()) < 0) {
            return contains(node.getLeftChild(), value);
        } else {
            return contains(node.getRightChild(), value);
        }
    }

    private void add(Node<T> node, T value) {
        if (node.getValue() == null) {
            node.setValue(value);
        } else if (comparator.compare(value, node.getValue()) < 0) {
            if (node.getLeftChild() == null) {
                node.setLeftChild(new Node<>(value));
            } else {
                add(node.getLeftChild(), value);
            }
        } else if (comparator.compare(value, node.getValue()) > 0) {
            if (node.getRightChild() == null) {
                node.setRightChild(new Node<>(value));
            } else {
                add(node.getRightChild(), value);
            }
        }
    }

    public List<T> getLeftBranchValues() {
        List<T> list;
        if (rootNode.getLeftChild() != null) {
            list = rootNode.getLeftChild().getChildValues();
            list.add(rootNode.getLeftChild().getValue());
        } else {
            list = new ArrayList<>();
        }
        return list;
    }

    public List<T> getRightBranchValues() {
        List<T> list;
        if (rootNode.getRightChild() != null) {
            list = rootNode.getRightChild().getChildValues();
            list.add(rootNode.getRightChild().getValue());
        } else {
            list = new ArrayList<>();
        }
        return list;
    }

    public BigDecimal getLeftBranchCost() {
        List<T> list = getLeftBranchValues();
        BigDecimal cost = BigDecimal.ZERO;
        for (T item : list) {
            cost = cost.add(item.getPrice());
        }
        return cost;
    }

    public BigDecimal getRightBranchCost() {
        List<T> list = getRightBranchValues();
        BigDecimal cost = BigDecimal.ZERO;
        for (T item : list) {
            cost = cost.add(item.getPrice());
        }
        return cost;
    }

    private String traversePreOrder(Node<T> rootNode) {
        if (rootNode == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rootNode.getValue().getPrice());
        stringBuilder.append(" (root)");
        String pointerRight = "└──";
        String pointerLeft = (rootNode.getRightChild() != null) ? "├──" : "└──";
        traverseNodes(stringBuilder, "", pointerLeft, rootNode.getLeftChild(), rootNode.getRightChild() != null, true);
        traverseNodes(stringBuilder, "", pointerRight, rootNode.getRightChild(), false, false);
        return stringBuilder.toString();
    }

    private void traverseNodes(StringBuilder stringBuilder, String padding, String pointer, Node<T> node, boolean hasRightSibling, boolean isLeftChild) {
        if (node != null) {
            stringBuilder.append("\n");
            stringBuilder.append(padding);
            stringBuilder.append(pointer);
            stringBuilder.append(node.getValue().getPrice());
            stringBuilder.append(isLeftChild ? " (L)" : " (R)");
            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }
            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRightChild() != null) ? "├──" : "└──";
            traverseNodes(stringBuilder, paddingForBoth, pointerLeft, node.getLeftChild(), node.getRightChild() != null, true);
            traverseNodes(stringBuilder, paddingForBoth, pointerRight, node.getRightChild(), false, false);
        }
    }

    @Override
    public String toString() {
        return traversePreOrder(rootNode);
    }
}
