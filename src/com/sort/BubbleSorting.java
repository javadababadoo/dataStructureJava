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
public class BubbleSorting {
    
    public static void bubbleSort(int[] array){
        int n = array.length;
        int temp = 0;
        
        for (int i = 0; i < n; i++) {
            
            for (int j = 1; j < (n-i); j++) {
                
                if(array[j-1] > array[j]){
                    temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }                
            }                                    
        }
    }
    
    public static void main(String[] args) {
        int[] array = {1,43,243,3,232,4,67,24,423,325,653,423,2};
        System.out.println("Before sort: "+ Arrays.toString(array));
        bubbleSort(array);        
        System.out.println("After sort: "+ Arrays.toString(array));
        
    }
    
}
