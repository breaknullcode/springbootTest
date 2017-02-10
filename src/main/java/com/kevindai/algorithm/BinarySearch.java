package com.kevindai.algorithm;

/**
 * 二分法
 * Created by daiwenkai on 2017/2/10.
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {0,3,7,9,12,14,16,19,23,56,77,78,90,97,99};
        int key = 99;
        int result = binarySearh(arr,key);
        //int result = Insert_Search(arr,key);
        System.out.println(result);
    }

    public static int binarySearh(int[] arr,int key){
        int max = arr.length - 1;
        int min = 0;
        int mid = 0;
        while (min <= max){
            mid = (max + min)/2;
            //mid = min + (max - min) * (key - arr[min])/ (arr[max] - arr[min]); // 插值查找
            System.out.println(arr[mid]);
            if(arr[mid] < key){
                min = mid + 1;
            }else if(arr[mid] > key){
                max = mid - 1;
            }else if(arr[mid] == key){
                return mid;
            }
        }
        return -1;
    }

    private static int Insert_Search(int[] arr, int key) {
        int min, max, mid;
        min = 0;
        max = arr.length - 1;
        while (min <= max) {
            // mid = (min + max) / 2;//二分查找
            mid = min + (max - min) * (key - arr[min])/ (arr[max] - arr[min]); // 插值查找
            if (key < arr[mid])
                max = mid - 1;
            else if (key > arr[mid])
                min = mid + 1;
            else
                // 如果等于则直接还回下标值
                return mid;
        }
        return -1;
    }
}
