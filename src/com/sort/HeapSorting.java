/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sort;

import java.util.Arrays;

/**
 *
 * @author Jaime Zapata - nebulae.com.co
 */
public class HeapSorting {

    private int total;

    public void heapify(Comparable[] array, int i) {
        int lft = i * 2;
        int rgt = i * 2 + 1;
        int grt = i;
        
        if(lft <= total && array[lft].compareTo(array[grt]) >= 0){
            grt = lft; 
        }
        
        if(rgt <= total && array[rgt].compareTo(array[grt]) >= 0){
            grt = rgt; 
        }
        
        if(grt != i){
            swap(array, i, grt);
            heapify(array, grt);            
        }
    }


    private void swap(Comparable[] array, int a, int b) {
        Comparable temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    
    private void sort(Comparable[] array){
        total = array.length-1;
        
        for (int i = total/2; i >= 0; i--) {
            heapify(array, i);
        }
        
        for (int i = total; i > 0; i--) {
            swap(array, 0, i);
            total--;
            heapify(array, 0);
        }
    }
    
    public static void main(String[] args) {
        HeapSorting heapSorting = new HeapSorting();
        
        Integer[] array = {1, 43, 243, 3, 232, 4, 67, 24, 423, 325, 653, 423, 2};
        System.out.println("Before sort: " + Arrays.toString(array));
        heapSorting.sort(array);
        System.out.println("After sort: " + Arrays.toString(array));
        
        
    }

}
