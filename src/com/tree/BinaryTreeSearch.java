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
public class BinaryTreeSearch {

    private Node root;

    public BinaryTreeSearch() {
        root = null;
    }
    
    public Node createNode(int value){
        Node node = new Node(value, null, null);
        return node;
    }
    
    public void add(Node start, Node newNode){
        
        if(root == null){
            root = newNode;
            return;
        }
        
        if(newNode.getValue() > start.getValue()){
            if(start.getRightNode() == null){
                start.setRightNode(newNode);
            }
            add(start.getRightNode(), newNode);
        }
        
        if(newNode.getValue() < start.getValue()){
            if(start.getLeftNode() == null){
                start.setLeftNode(newNode);
            }
            add(start.getLeftNode(), newNode);
        }
    }
    
    
    public void search(int value, Node start){
        
        
        
        if(start == null){
            System.out.println("Node is not found");
            return;
        }
        
        if(start.getValue() == value){
            System.out.println("Node is found");
            return;
        }
        
        if(value > start.getValue()){
            search(value, start.getRightNode());
        }
        
        if(value < start.getValue()){
            search(value, start.getLeftNode());
        }
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }

}
