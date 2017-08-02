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
public class QuickSorting {

    public static void quickSorting(int[] array, int low, int high) {
        if (low > high) {
            return;
        }
        int mid = low + (high - low) / 2;
        int pivot = array[mid];
        int i = low;
        int j = high;

        while (i <= j) {

            while (array[i] < pivot) {
                i++;
            }
            while (array[j] > pivot) {
                j--;
            }
            
            if(i <=j){
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }

        }
        
        if(low < j){
            quickSorting(array, low, j);
        }
        
        if(high > i){
            quickSorting(array, i, high);
        }
    }
    
    

    public static void main(String[] args) {
        int[] array = {1, 43, 243, 3, 232, 4, 67, 24, 423, 325, 653, 423, 2};
        System.out.println("Before sort: " + Arrays.toString(array));
        quickSorting(array, 0, array.length-1);
        System.out.println("After sort: " + Arrays.toString(array));
    }
}
