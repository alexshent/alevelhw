package ua.com.alevel.alexshent.treesort;

public class Tree {
    public Tree leftSubTree;
    public Tree rightSubTree;
    public int key;

    public Tree(int key) {
        this.key = key;
    }

    public void insert(Tree newSubTree) {
        if (newSubTree.key < key) {
            if (leftSubTree != null) leftSubTree.insert(newSubTree);
            else leftSubTree = newSubTree;
        } else {
            if (rightSubTree != null) rightSubTree.insert(newSubTree);
            else rightSubTree = newSubTree;
        }
    }

    public void traverse(TreeVisitor visitor) {
        if (leftSubTree != null) {
            leftSubTree.traverse(visitor);
        }
        visitor.visit(this);
        if (rightSubTree != null) {
            rightSubTree.traverse(visitor);
        }
    }
}
