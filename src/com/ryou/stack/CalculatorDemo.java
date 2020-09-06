package com.ryou.stack;

import java.util.Stack;

public class CalculatorDemo {
	public static void main(String[] args) {
		String expression = "15*2-20*5+18/3";
		System.out.println(Calculator.doCalculate(expression));
	}
}

class Calculator {
	
//	public static Double handleBrackets(String expression) {
//		
//	}
	
	public static Double doCalculate(String expression) {
		// 定义存储操作符的栈
		Stack<String> symbals = new Stack<String>();
		// 定义存储操作数的栈
		Stack<Double> nums = new Stack<Double>();

		// 遍历表达式
		int s1 = 0;
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			if (isSymbal(c)) {
				// 将操作符之间的数入数栈
				String num = expression.substring(s1, i);
				nums.push(Double.parseDouble(num));
				// 将操作符入符号栈
				handleSymbal(c + "", symbals, nums);
				s1 = i + 1;
			}
		}

		// 将最后一个数入栈
		String num = expression.substring(s1, expression.length());
		nums.push(Double.parseDouble(num));

		// 将剩下的数计算完
		while (!symbals.isEmpty()) {
			Double num2 = nums.pop();
			Double num1 = nums.pop();
			Double res = calculate(num1, num2, symbals.pop());
			nums.push(res);
		}

		return nums.pop();
	}

	// 处理操作符
	public static void handleSymbal(String symbal, Stack<String> symbals, Stack<Double> nums) {
		// 1.如果符号栈中无符号，则直接入栈
		if (symbals.isEmpty()) {
			symbals.push(symbal);
			return;
		}

		// 2.如果符号栈中有符号，判断符号的优先级
		// 2.1 如果当前符号优先级大于栈顶符号优先级，直接入栈
		// 2.2 如果当前符号优先级小于等于栈顶符号优先级，则弹出栈顶符号，并从数栈中弹出两个数进行运算
		while (!symbals.isEmpty()) {
			if (getPower(symbal) > getPower(symbals.peek())) {
				symbals.push(symbal);
				break;
			} else {
				Double num2 = nums.pop();
				Double num1 = nums.pop();
				Double res = calculate(num1, num2, symbals.pop());
				nums.push(res);
			}
		}

		if (symbals.isEmpty()) // 栈为空，意味着前面的符号都被处理掉了
			symbals.push(symbal);
	}

	// 进行计算
	public static Double calculate(Double num1, Double num2, String symbal) {
		Double res;
		switch (symbal) {
		case "+":
			res = num1 + num2;
			break;
		case "-":
			res = num1 - num2;
			break;
		case "*":
			res = num1 * num2;
			break;
		case "/":
			res = num1 / num2;
			break;
		default:
			throw new RuntimeException("运算符错误！");
		}
		return res;
	}

	// 判断符号优先级
	public static int getPower(String symbal) {
		if ("+".equals(symbal) || "-".equals(symbal)) {
			return 0;
		} else if ("*".equals(symbal) || "/".equals(symbal)) {
			return 1;
		} else {
			return -1;
		}
	}

	// 判断当前符号是否为操作符
	public static boolean isSymbal(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/') {
			return true;
		} else {
			return false;
		}
	}
}