package ua.com.alevel.alexshent.treesort;

class KeyPrinter implements TreeVisitor {
    public void visit(Tree node) {
        System.out.println(" " + node.key);
    }
}
