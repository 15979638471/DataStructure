package com.ryou.search;

import java.util.Arrays;

public class BinarySearch {
	
	public static int binary(int[] arr, int val) {
		int front = 0;
		int end = arr.length;
		int mid = 0;
		while(front <= end) {
			mid = (front + end) / 2;
			if(val == arr[mid]) {
				return mid;
			}else if(val < arr[mid]) {
				end = mid - 1;
			}else if(val > arr[mid]) {
				front = mid + 1;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] arr = {43,22,15,35,33,24,16,77,86,94};
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println(binary(arr, 94));;
	}
}
