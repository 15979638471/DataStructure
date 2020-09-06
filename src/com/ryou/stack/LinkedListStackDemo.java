package com.ryou.stack;

import java.util.Scanner;

public class LinkedListStackDemo {
	public static void main(String[] args) {
		//测试LinkedListStack是否正确
		//先创建一个LinkedListStack对象
		LinkedListStack stack = new LinkedListStack();
		String key = "";
		boolean loop = true;//控制是否退出菜单
		Scanner scanner = new Scanner(System.in);
		while(loop) {
			System.out.println("show:表示显示栈");
			System.out.println("exit:退出程序");
			System.out.println("push:表示添加数据到栈（入栈）");
			System.out.println("pop:表示从栈取出数据");
			System.out.println("请输入你的选择");
			key = scanner.next();
			switch (key) {
				case "show":
					try {
						stack.list();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case "push":
					System.out.println("请输入一个数");
					String value = scanner.next();
					stack.push(value);
					break;
				case "pop":
					try {
						String res = stack.pop();
						System.out.println("出栈的数据是"+res);
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
					break;
				case "exit":
					scanner.close();
					loop = false;
					break;
				default:
					break;
			}
		}
		
		System.out.println("程序退出");
	}
}

//定义一个栈
class LinkedListStack {
	private Node top;
	
	//判断栈空
	public boolean isEmpty() {
		return top == null;
	}
	
	//入栈
	public void push(String value) {
		Node node = new Node(value);
		if(isEmpty()) {//如果是第一个节点
			node.setNext(null);
			top = node;
		}else {
			Node temp = top;
			node.setNext(temp);
			top = node;
		}
	}
	
	//出栈
	public String pop() {
		if(isEmpty()) {//判断栈是否为空
			throw new RuntimeException("栈为空");
		}
		String value = top.getValue();
		top = top.getNext();
		return value;
	}
	
	//遍历栈
	public void list() {
		if(isEmpty()) {
			throw new RuntimeException("栈为空");
		}
		Node cur = top;//使用一个辅助指针
		System.out.println("====开始遍历栈====");
		while(cur != null) {
			System.out.println(cur);
			cur = cur.getNext();
		}
		System.out.println("=====遍历完成====");
	}
}


//定义一个链表节点
class Node {
	String value;
	Node next;
	public Node(String value) {
		super();
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}
}