package com.ryou.recursion;

//使用递归解决八皇后问题
public class EightQueens {
	int[] queen = new int[8];// 用一个一维数组表示棋盘，下标为行，值为列
	int count;

	/**
	 * @param n 表示第n个皇后
	 * @return
	 */
	public void check(int n) {
		if (n >= 8) {// 当n>=8时说明完成一个答案
			System.out.println("答案"+(++count));
			printAnswer();
			return;
		}
		
		for (int i = 0; i < 8; i++) {
			//先将第n个皇后放置到第n行的第i列
			queen[n] = i;
			//判断当前这个皇后是否与其他皇后冲突
			if(judge(n)) {
				//如果不冲突，则继续放置下一个皇后，即开始递归
				check(n + 1);
			}
			//如果冲突，则继续放置本行的皇后
		}
	}

	/**
	  * 判断当前的皇后是否与其他皇后冲突
	 * @param n 表示第n个皇后
	 * @return
	 */
	public boolean judge(int n) {
		for (int i = 0; i < n; i++) {
			//如果当前皇后与第i个皇后在同一列或同一斜线，则冲突
			if(queen[i] == queen[n] || Math.abs(i - n) == Math.abs(queen[i] - queen[n])) {
				return false;
			}
		}
		return true;
	}
	
	// 打印输出答案
	public void printAnswer() {
		System.out.print("[");
		for (int i = 0; i < queen.length; i++) {
			if (i == queen.length - 1) {
				System.out.print(queen[i]);
			} else {
				System.out.print(queen[i]+",");
			}
		}
		System.out.println("]");
	}

	public static void main(String[] args) {
		//测试功能
		EightQueens eightQueens = new EightQueens();
		eightQueens.check(0);
	}
}
