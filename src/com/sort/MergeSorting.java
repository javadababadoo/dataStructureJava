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
public class MergeSorting {
    
    private int[] array;
    
    private int[] tempArray;
    
    public void mergeSorting(int[] array){
        this.array = array;
        this.tempArray = new int[array.length];
        doMergeSort(0, array.length-1);
    }
    
    public void doMergeSort(int lowerIndex, int highIndex){
        if(lowerIndex < highIndex){
            int middle = lowerIndex + (highIndex-lowerIndex)/2;
            doMergeSort(lowerIndex, middle);
            doMergeSort(middle+1, highIndex);
            mergepart(lowerIndex, middle, highIndex);
        }
    }

    private void mergepart(int lowerIndex, int middle, int highIndex) {
        for (int i = lowerIndex; i <= highIndex; i++) {
            tempArray[i] = array[i];            
        }
        
        int i = lowerIndex;
        int j = middle+1;
        int k = lowerIndex;
        
        while (i <= middle&& j <= highIndex) {            
            if(tempArray[i] <= tempArray[j]){
                array[k] = tempArray[i];
                i++;
            }else{
                array[k] = tempArray[j];
                j++;
            }
            k++;
        }
        
        while (i <= middle) {
            array[k] = tempArray[i];
            k++;
            i++;
        }
    }
    
    public static void main(String[] args) {
        MergeSorting mergeSorting = new MergeSorting();
        int[] array = {1, 43, 243, 3, 232, 4, 67, 24, 423, 325, 653, 423, 2};
        System.out.println("Before sort: " + Arrays.toString(array));
        mergeSorting.mergeSorting(array);
        System.out.println("After sort: " + Arrays.toString(mergeSorting.array));
    }

}
