/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tree;

/**
 *
 * @author Jaime Zapata - nebulae.com.co
 */
public class Node {

        
    private Node leftNode;

    private Integer value;
    
    private Node rightNode;

    public Node(int value, Node leftNode, Node rightNode) {
        this.leftNode = leftNode;
        this.value = value;
        this.rightNode = rightNode;
    }

    
    /**
     * @return the leftNode
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * @param leftNode the leftNode to set
     */
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    /**
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the rightNode
     */
    public Node getRightNode() {
        return rightNode;
    }

    /**
     * @param rightNode the rightNode to set
     */
    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    
}
