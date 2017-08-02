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
public class SelectionSorting {
 
    public static void selectionSort(int[] array){
        
        for (int i = 0; i < array.length-1; i++) {
            
            int index = i;            
            
            for (int j = i +1; j < array.length; j++) {
                
                if(array[j] < array[index]){
                    index = j;
                }
                
            }
            
            if(index != i){
                int temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
            
        }
        
    }
    
    public static void main(String[] args) {
        int[] array = {1,43,243,3,232,4,67,24,423,325,653,423,2};
        System.out.println("Before sort: "+ Arrays.toString(array));
        selectionSort(array);        
        System.out.println("After sort: "+ Arrays.toString(array));
    }
}
