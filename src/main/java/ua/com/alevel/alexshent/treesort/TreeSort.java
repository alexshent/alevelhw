package ua.com.alevel.alexshent.treesort;

public class TreeSort {
    public static void main(String[] args) {
        Tree myTree;
        // create root tree
        myTree = new Tree( 7 );
        // insert subtree
        myTree.insert( new Tree( 5 ) );
        // insert subtree
        myTree.insert( new Tree( 9 ) );
        // traverse all tree
        myTree.traverse(new KeyPrinter());
    }
}
