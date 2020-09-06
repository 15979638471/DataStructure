package com.ryou.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

 /**
 * @author zxc11
 * 中缀表达式计算器
 */
public class ReversePolishNationCalculatorDemo {

	public static void main(String[] args) {
		//测试功能
		String exp = "2.1 + ((1+(2.       3*3.))+(((5*6.5)+8)*9))";
		System.out.println(ReversePolishNationCalculator.calculate(exp));
	}

}

class ReversePolishNationCalculator {
	
	//进行计算
	public static Double calculate(String expression) {
		List<String> infixNation = getInfixNation(expression);
		Stack<Double> stack = new Stack<Double>();
		for (String string : infixNation) {
			if (string.matches("\\d+.?\\d*")) {//如果匹配的是数（包括小数）
				stack.push(Double.parseDouble(string));
			} else if(isSymbol(string)) {
				Double num2 = stack.pop();
				Double num1 = stack.pop();
				Double res = simpleCalculate(num1, num2, string);
				stack.push(res);
			} else {
				throw new RuntimeException("运算出错！");
			}
		}
		return stack.pop();
	}
	
	// 将中缀表达式转换成后缀表达式（逆波兰表达式）
	private static List<String> getInfixNation(String expression) {
		if ("".equals(expression)) {
			throw new RuntimeException("表达式不能为空！");
		}
		// 消除空白字符
		expression = expression.replaceAll("\\s", "");
		Stack<String> stack = new Stack<String>();
		List<String> out = new ArrayList<String>();
		// 遍历表达式
		// 1.如果是数则直接加入输出集合
		// 2.如果是左括号直接入栈
		// 3.1如果是操作符，当栈为空或者栈顶为左括号直接入栈
		// 3.2 否则判断操作符与栈顶操作符的优先级，如果优先级高入栈
		//     否则将栈顶操作符弹出入输出集合，然后继续判断，直到高于栈顶优先级才入栈
		// 4 如果是右括号，则弹出栈顶操作符加入到输出集合，直到碰到左括号（括号丢弃不入集合）
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < expression.length(); i++) {
			String str = expression.substring(i, i + 1);
			if (isNumber(str) || ".".equals(str)) { 
				sb.append(str);
			} else {
				if(sb.length() > 0) {
					out.add(sb.toString());
					sb.delete(0, sb.length());
				}
				if (isSymbol(str)) {
					if (stack.isEmpty()) {
						stack.push(str);
					} else {
						while (true) {
							if (stack.isEmpty()) {
								stack.push(str);
								break;
							}
							if (getPower(str) > getPower(stack.peek())) {
								stack.push(str);
								break;
							} else {
								out.add(stack.pop());
							}
						}
					}
				} else if(isOpenParen(str)) {
					stack.push(str);
				} else if(isCloseParen(str)) {
					while(true) {
						String str2 = stack.pop();
						if(isOpenParen(str2)) {
							break;
						}
						out.add(str2);
					}
				} else {
					throw new RuntimeException("表达式错误！");
				}
			}
		}
		
		while(!stack.isEmpty()) {
			out.add(stack.pop());
		}
		
		return out;
	}
	
	//简单计算
	private static Double simpleCalculate(Double num1, Double num2, String symbol) {
		Double res;
		switch (symbol) {
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

	// 判断是否为操作符
	private static boolean isSymbol(String str) {
		return str.matches("\\+|\\-|\\*|/");
	}

	// 判断是否为操作数
	private static boolean isNumber(String str) {
		return str.matches("\\d+");
	}

	// 判断是否为左括号
	private static boolean isOpenParen(String str) {
		return "(".equals(str);
	}

	// 判断是否为右括号
	private static boolean isCloseParen(String str) {
		return ")".equals(str);
	}

	// 判断操作符的优先级
	private static int getPower(String str) {
		if ("+".equals(str) || "-".equals(str)) {
			return 0;
		} else if ("*".equals(str) || "/".equals(str)) {
			return 1;
		} else {
			return -1;
		}
	}
}