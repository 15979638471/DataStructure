package com.ryou.tree;

public class BinaryTree {
	
	public static void preOrder(Node node) {
		if(node == null) {
			System.out.println("二叉树为空！");
			return;
		}
		System.out.println(node);
		if(node.getLeft() != null) {
			preOrder(node.getLeft());
		}
		if(node.getRight() != null) {
			preOrder(node.getRight());
		}
	}
	
	public static void infixOrder(Node node) {
		if(node == null) {
			System.out.println("二叉树为空！");
			return;
		}
		if(node.getLeft() != null) {
			infixOrder(node.getLeft());
		}
		System.out.println(node);
		if(node.getRight() != null) {
			infixOrder(node.getRight());
		}
	}
	
	public static void postOrder(Node node) {
		if(node == null) {
			System.out.println("二叉树为空！");
			return;
		}
		if(node.getLeft() != null) {
			postOrder(node.getLeft());
		}
		if(node.getRight() != null) {
			postOrder(node.getRight());
		}
		System.out.println(node);
	}
	
	public static void main(String[] args) {
		Node root = new Node("A", 22);
		Node node2 = new Node("B", 23);
		Node node3 = new Node("C", 24);
		Node node4 = new Node("D", 25);
		Node node5 = new Node("E", 26);
		Node node6 = new Node("F", 27);
		
		root.setLeft(node2);
		root.setRight(node3);
		node2.setRight(node4);
		node3.setLeft(node5);
		node3.setRight(node6);
		
		System.out.println("================开始前序遍历二叉树================");
		preOrder(root);
		System.out.println("================开始中序遍历二叉树================");
		infixOrder(root);
		System.out.println("================开始中序遍历二叉树================");
		postOrder(root);
	}
}

class Node {
	private String name;
	private int age;
	private Node left;
	private Node right;
	
	public Node(String name, int age) {
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
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "Node [name=" + name + ", age=" + age + "]";
	}
	
}