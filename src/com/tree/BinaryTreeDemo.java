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
public class BinaryTreeDemo {
    public static void main(String[] args) {
        BinaryTreeSearch binaryTreeSearch = new BinaryTreeSearch();
        
        
        
        binaryTreeSearch.add(binaryTreeSearch.getRoot(), binaryTreeSearch.createNode(10));
        binaryTreeSearch.add(binaryTreeSearch.getRoot(), binaryTreeSearch.createNode(3));
        binaryTreeSearch.add(binaryTreeSearch.getRoot(), binaryTreeSearch.createNode(5));
        binaryTreeSearch.add(binaryTreeSearch.getRoot(), binaryTreeSearch.createNode(9));
        
        binaryTreeSearch.search(3, binaryTreeSearch.getRoot());
    }
}
