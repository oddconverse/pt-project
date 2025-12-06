package org.pt;

public class Node<Key extends Comparable<Key>, Value> {
    private Key key;
    private Value data;
    private int height;
    private Node<Key, Value> leftChild;
    private Node<Key, Value> rightChild;

    public Node(Key key, Value data) {
        this.key = key;
        this.data = data;
        this.height = 1;
        this.leftChild = null;
        this.rightChild = null;
    }
    public Key getKey() {
        return key;
    }
    public Value getData() {
        return data;
    }
    public int getHeight() {
        return height;
    }
    public Node<Key, Value> getLeftChild() {
        return leftChild;
    }
    public Node<Key, Value> getRightChild() {
        return rightChild;
    }
    public void setLeftChild(Node<Key, Value> n) {
        leftChild = n;
    }
    public void setRightChild(Node<Key, Value> n) {
        rightChild = n;
    }
    public void add(Node<Key, Value> newNode) {
        if (getKey().compareTo(newNode.getKey()) <= 0 && getRightChild() != null) {
            getRightChild().add(newNode);
        }
        else if (getKey().compareTo(newNode.getKey()) >= 0 && getLeftChild() != null) {
            getLeftChild().add(newNode);
        }
        else if (getKey().compareTo(newNode.getKey()) <= 0) {
            setRightChild(newNode);
            System.out.println(newNode.print() + " right node");
        }
        else if (getKey().compareTo(newNode.getKey()) >= 0) {
            setLeftChild(newNode);
            System.out.println(newNode.print() + " left node");
        }
    }
    
    public int size() {
        int size = 0;
        if (getLeftChild() != null) {
            size++;
            size += getLeftChild().size();
        }
        if (getRightChild() != null) {
            size++;
            size += getRightChild().size();
        }
        return size;

    }
    public Value search(Key s) {
        if (getKey().compareTo(s) == 0) {
            return getData();
        }
        else if (getKey().compareTo(s) > 0 && getLeftChild() != null) {
            System.out.println(print() + " left");
            return getLeftChild().search(s);
        }
        else if (getKey().compareTo(s) < 0 && getRightChild() != null) {
            System.out.println(print() + " right");
            return getRightChild().search(s);
        }
        else {
            System.out.println(print() + " Null");
            return null;
        }
    }
    public String print() {
        return getData().toString() + ", " + getKey().toString();
    }
    public String printAll() {
        String str = "";
        if (getLeftChild() != null)
            str = getLeftChild().printAll();
        str += print() +"\n";
        if (getRightChild() != null)
            str += getRightChild().printAll();
        return str;
    }
    @Override
    public String toString() {
        return data.toString();
    }
}
