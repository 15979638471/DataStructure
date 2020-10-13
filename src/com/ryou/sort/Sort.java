package com.ryou.sort;

import java.util.Arrays;

/**
 * 排序算法
 * @author zxc11
 *
 */
public class Sort {
	
	public static void main(String[] args) {
//		int[] arr = {19,2,88,7,33,51,22,13};
		int[] arr = new int[10000000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 100000);
		}
		//测试冒泡排序
//		bubbleSort(Arrays.copyOf(arr, 100000));
		//测试选择排序
//		selectSort(Arrays.copyOf(arr, 100000));
		//测试插入排序
//		insertSort(Arrays.copyOf(arr, 10000000));
		//测试希尔排序
		shellSort(Arrays.copyOf(arr, 10000000));
		//测试快速排序
		quickSort(Arrays.copyOf(arr, 10000000));
		//测试归并排序
		mergeSort(Arrays.copyOf(arr, 10000000));
		long t1 = System.currentTimeMillis();
		Arrays.sort(Arrays.copyOf(arr, 10000000));
		long t2 = System.currentTimeMillis();
		System.out.println("API自带排序花的时间：" + (t2 - t1));
	}
	
	/**
	 * 冒泡排序
	 * @param arr
	 */
	public static void bubbleSort(int[] arr) {
		long t1 = System.currentTimeMillis();
		int temp = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if(arr[j] > arr[j + 1]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
//			System.out.println("冒泡排序第"+ (i+1) + "轮排序后的数组：" + Arrays.toString(arr));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("冒泡排序花的时间：" + (t2 - t1));
	}
	
	/**
	 * 选择排序
	 * @param arr
	 */
	public static void selectSort(int[] arr) {
		long t1 = System.currentTimeMillis();
		int index = 0;
		int temp = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			index = i;
			for (int j = i + 1; j < arr.length; j++) {
				if(arr[j] < arr[index]) {
					index = j;
				}
			}
			if(index != i) {
				temp = arr[i];
				arr[i] = arr[index];
				arr[index] = temp;
			}
//			System.out.println("选择排序第"+ (i+1) + "轮排序后的数组：" + Arrays.toString(arr));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("选择排序花的时间：" + (t2 - t1));
	}
	
	/**
	 * 插入排序
	 * @param arr
	 */
	public static void insertSort(int[] arr) {
		long t1 = System.currentTimeMillis();
		int temp = 0;
		for (int i = 1; i < arr.length; i++) {
			temp = arr[i];
			int j = i;
			if(temp < arr[j - 1]) {
				while(j - 1 >= 0 && temp < arr[j - 1]) {
					arr[j] = arr[j - 1];
					j--;
				}
			}
			arr[j] = temp;
//			System.out.println("插入排序第"+ i + "轮排序后的数组：" + Arrays.toString(arr));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("插入排序花的时间：" + (t2 - t1));
	}
	
	/**
	 * 希尔排序
	 * @param arr
	 */
	public static void shellSort(int[] arr) {
		long t1 = System.currentTimeMillis();
		int temp = 0;
		for (int gap = arr.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < arr.length; i++) {
				temp = arr[i];
				int j = i;
				if(temp < arr[j - gap]) {
					while(j - gap >= 0 && temp < arr[j - gap]) {
						arr[j] = arr[j - gap];
						j -= gap;
					}
				}
				arr[j] = temp;
			}
		}
//		System.out.println("希尔排序后的数组：" + Arrays.toString(arr));
		long t2 = System.currentTimeMillis();
		System.out.println("希尔排序花的时间：" + (t2 - t1));
	}
	
	/**
	 * 快速排序
	 * @param arr
	 */
	public static void quickSort(int[] arr) {
		long t1 = System.currentTimeMillis();
		quick_sort(arr, 0, arr.length-1);
//		System.out.println("快速排序后的数组：" + Arrays.toString(arr));
		long t2 = System.currentTimeMillis();
		System.out.println("快速排序花的时间：" + (t2 - t1));
	}
	
	public static void quick_sort(int[] arr, int l, int r)
	{
	    if (l < r)
	    {
	        //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
	        int i = l, j = r, x = arr[l];
	        while (i < j)
	        {
	            while(i < j && arr[j] >= x) // 从右向左找第一个小于x的数
	                j--;  
	            if(i < j) 
	                arr[i++] = arr[j];
	            
	            while(i < j && arr[i] < x) // 从左向右找第一个大于等于x的数
	                i++;  
	            if(i < j) 
	                arr[j--] = arr[i];
	        }
	        arr[i] = x;
	        quick_sort(arr, l, i - 1); // 递归调用 
	        quick_sort(arr, i + 1, r);
	    }
	}
	
	/**
	 * 归并排序
	 * @param arr
	 */
	public static void mergeSort(int[] arr) {
		long t1 = System.currentTimeMillis();
		
		int[] temp = new int[arr.length];
		merge_sort(arr, 0, arr.length - 1, temp);
		
//		System.out.println("归并排序后的数组：" + Arrays.toString(arr));
		long t2 = System.currentTimeMillis();
		System.out.println("归并排序花的时间：" + (t2 - t1));
	}
	
	public static void merge_sort(int[] arr, int l, int r, int[] temp) {
		//当l = r时退出递归
		if(l < r) {
			int mid = (l + r) / 2;
			//向左拆分
			merge_sort(arr, l, mid, temp);
			//向右拆分
			merge_sort(arr, mid + 1, r, temp);
			//归并
			merge(arr, l, r, temp);
		}
	}
	
	public static void merge(int[] arr, int l, int r, int[] temp) {
		int mid = (l + r) / 2;
		int i = l, j = mid + 1, t = 0;
		
		//将arr数组（有序）依次比较放到temp数组中
		while(i <= mid && j <= r) {
			if(arr[i] < arr[j]) {
				temp[t++] = arr[i++];
			}else {
				temp[t++] = arr[j++];
			}
		}
		
		//将arr中剩余的数全部按顺序放到temp数组中
		while(i <= mid) {
			temp[t++] = arr[i++];
		}
		
		while(j <= r) {
			temp[t++] = arr[j++];
		}
		
		//将temp数组元素拷贝到arr数组中
		t = 0;
		i = l;
		while(i <= r) {
			arr[i++] = temp[t++];
		}
	}
}
