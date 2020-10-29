package com.ryou.tree;
//线索二叉树
public class ThreadedBinaryTree {
	
	public static TNode pre;
	
	//先序线索化二叉树
	public static void preThread(TNode node) {
		if(node == null) {
			return;
		}
		
		//当前结点没有左子结点,处理前驱结点
		if(node.getLeft() == null) {
			node.setLeftType((byte)1); //将左子结点标志为前驱结点
			node.setLeft(pre);
		}
		
		//pre没有右子结点，处理后继结点
		if(pre != null && pre.getRight() == null) {
			pre.setRightType((byte)1); //将pre的右子结点标志为后继结点
			pre.setRight(node); //将pre结点的后继结点设为当前结点
		}
		
		pre = node; //让pre指向当前结点
		
		//向左递归线索化二叉树
		if(node.getLeftType() == 0) {
			preThread(node.getLeft());
		}
		
		//向右递归线索化二叉树
		if(node.getRightType() == 0) {
			preThread(node.getRight());
		}
	}
	
	//中序线索化二叉树
	public static void infixThread(TNode node) {
		if(node == null) {
			return;
		}
		//向左递归线索化二叉树
		infixThread(node.getLeft());
		
		//当前结点没有左子结点,处理前驱结点
		if(node.getLeft() == null) {
			node.setLeftType((byte)1); //将左子结点标志为前驱结点
			node.setLeft(pre);
		}
		
		//pre没有右子结点，处理后继结点
		if(pre != null && pre.getRight() == null) {
			pre.setRightType((byte)1); //将pre的右子结点标志为后继结点
			pre.setRight(node); //将pre结点的后继结点设为当前结点
		}
		
		pre = node; //让pre指向当前结点
		
		//向右递归线索化二叉树
		infixThread(node.getRight());
	}
	
	/**
	 * 前序遍历线索二叉树
	 * 思路：1、一直向左查找二叉树并打印结点，直到找到线索化过的结点
	 * 2、打印这个结点
	 * 3、向右走一步
	 * 4、重复以上过程，直到遍历完成
	 * @param root
	 */
	public static void preOrder(TNode root) {
		if(root == null) {
			System.out.println("二叉树为空！");
			return;
		}
		
		TNode node = root;
		
		while(node != null) {
			while(node.getLeftType() == (byte)0) {
				System.out.println(node);
				node = node.getLeft();
			}
			System.out.println(node);
			
			node = node.getRight();
		}
	}
	
	/**
	 * 中序遍历线索二叉树
	 * 思路：1、一直向左查找二叉树，直到找到线索化过的结点，输出当前结点
	 * 2、向右遍历后继结点，直到遇到没有后继结点的结点
	 * 3、向右走一步
	 * 4、重复以上过程，直到二叉树遍历完成
	 * @param root
	 */
	public static void infixOrder(TNode root) {
		if(root == null) {
			System.out.println("二叉树为空！");
			return;
		}
		
		TNode node = root;
		
		while(node != null) {
			while(node.getLeftType() == (byte)0) {
				node = node.getLeft();
			}
			System.out.println(node);
			
			while(node.getRightType() == (byte)1) {
				node = node.getRight();
				System.out.println(node);
			}
			
			node = node.getRight();
		}
	}
	
	public static void main(String[] args) {
		TNode root = new TNode("A", 22);
		TNode node2 = new TNode("B", 23);
		TNode node3 = new TNode("C", 24);
		TNode node4 = new TNode("D", 25);
		TNode node5 = new TNode("E", 26);
		TNode node6 = new TNode("F", 27);
		
		root.setLeft(node2);
		root.setRight(node3);
		node2.setRight(node4);
		node3.setLeft(node5);
		node3.setRight(node6);
		
//		System.out.println(node6.getLeft());
//		System.out.println(node6.getRight());
		
		preThread(root);
		preOrder(root);
		
//		infixThread(root);
//		infixOrder(root);
	}
}

class TNode {
	private String name;
	private int age;
	private TNode left;
	private TNode right;
	private byte leftType;
	private byte rightType;
	
	public byte getLeftType() {
		return leftType;
	}
	public void setLeftType(byte leftType) {
		this.leftType = leftType;
	}
	public byte getRightType() {
		return rightType;
	}
	public void setRightType(byte rightType) {
		this.rightType = rightType;
	}
	public TNode(String name, int age) {
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
	public TNode getLeft() {
		return left;
	}
	public void setLeft(TNode left) {
		this.left = left;
	}
	public TNode getRight() {
		return right;
	}
	public void setRight(TNode right) {
		this.right = right;
	}
	@Override
	public String toString() {
		return "TNode [name=" + name + ", age=" + age + "]";
	}
	
}