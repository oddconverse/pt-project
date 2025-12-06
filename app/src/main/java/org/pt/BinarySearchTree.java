package org.pt;
// NOTE!!!! NEVER COMMIT/PUSH GTFS DATABASE TO MAIN
// STOP FUCKING UP YOUR GIT
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Node<Key, Value> master;
    public BinarySearchTree() {
        this.master = null;
    }
    public BinarySearchTree(Node<Key, Value> node) {
        this.master = node;
    }
    public BinarySearchTree(Key key, Value data) {
        this.master = new Node<>(key, data);
    }
    public void add(Key key, Value data) {
        Node<Key, Value> newNode = new Node<>(key, data);
        if (master == null) {
            master = newNode;
            System.out.println(master.print() + " master node");
        }
        else {
            master.add(newNode); 
        }
    }
    public Value search(Key s) {
        return master.search(s);
    }

    public int size() {
        int size = 0;
        if (master != null) {
            size++;
        }
        if (master.getLeftChild() != null) {
            size++;
            size += master.getLeftChild().size();
        }
        if (master.getRightChild() != null) {
            size++;
            size += master.getRightChild().size();
        }
        return size;

    }
    public void leftRotation() {

    }
    public void rightRotation() {

    }
    public void leftRightRotation() {

    }
    public void rightLeftRotation() {
        
    }
    public String printAll() {
        return master.printAll();
    }
    public static void main (String[] args) {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.add(4, 7);
        bst.add(3, 6);
        bst.add(2, 45);
        bst.add(1, 41);
        bst.add(5, 67);
        bst.add(6, 65);
        System.out.println(bst.size());
        System.out.println(bst.printAll());
        System.out.println(bst.search(4));
    }
}
