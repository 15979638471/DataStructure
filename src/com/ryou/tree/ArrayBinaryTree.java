package com.ryou.tree;

//顺序存储二叉树
public class ArrayBinaryTree {
	
	//前序遍历
	public static void preOrder(ArrNode[] arrNodes, int index) {
		if(arrNodes[index] == null) {
			return;
		}
		System.out.println(arrNodes[index]);
		if(2 * index + 1 < arrNodes.length) {
			preOrder(arrNodes,2 * index + 1);
		}
		if(2 * index + 2 < arrNodes.length) {
			preOrder(arrNodes,2 * index + 2);
		}
	}
	
	public static void infixOrder(ArrNode[] arrNodes) {
		infixOrder(arrNodes, 0);
	}
	//中序遍历
	public static void infixOrder(ArrNode[] arrNodes, int index) {
		if(arrNodes[index] == null) {
			return;
		}
		if(2 * index + 1 < arrNodes.length) {
			infixOrder(arrNodes,2 * index + 1);
		}
		System.out.println(arrNodes[index]);
		if(2 * index + 2 < arrNodes.length) {
			infixOrder(arrNodes,2 * index + 2);
		}
	}
	
	public static void preOrder(ArrNode[] arrNodes) {
		preOrder(arrNodes, 0);
	}
	
	//后序遍历
	public static void postOrder(ArrNode[] arrNodes, int index) {
		if(arrNodes[index] == null) {
			return;
		}
		if(2 * index + 1 < arrNodes.length) {
			postOrder(arrNodes,2 * index + 1);
		}
		if(2 * index + 2 < arrNodes.length) {
			postOrder(arrNodes,2 * index + 2);
		}
		System.out.println(arrNodes[index]);
	}
	
	public static void postOrder(ArrNode[] arrNodes) {
		postOrder(arrNodes, 0);
	}
	
	public static void main(String[] args) {
		ArrNode[] arrNodes = new ArrNode[6];
		int ascii = 65;
		for (int i = 0; i < arrNodes.length; i++) {
			arrNodes[i] = new ArrNode((char)ascii + "", ascii);
			ascii++;
		}
		System.out.println("================开始前序遍历顺序二叉树================");
		preOrder(arrNodes);
		System.out.println("================开始中序遍历顺序二叉树================");
		infixOrder(arrNodes);
		System.out.println("================开始后序遍历顺序二叉树================");
		postOrder(arrNodes);
	}
}

class ArrNode {
	private String name;
	private int age;
	
	public ArrNode(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Node [name=" + name + ", age=" + age + "]";
	}
	
}