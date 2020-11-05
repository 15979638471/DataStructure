package com.ryou.avltree;

public class AvlTree {
	private Node root;

	public Node getRoot() {
		return root;
	}
	
	public void add(Node node) {
		if(root == null) {
			root = node;
			return;
		}
		Node cur = root;
		while(true) {
			if(node.getValue() < cur.getValue()) {
				cur.setlTreeHeight(cur.getlTreeHeight() + 1);
				if(cur.getLeft() == null) {
					cur.setLeft(node);
					break;
				}else {
					cur = cur.getLeft();
				}
			}else {
				cur.setrTreeHeight(cur.getrTreeHeight() + 1);
				if(cur.getRight() == null) {
					cur.setRight(node);
					break;
				}else {
					cur = cur.getRight();
				}
			}
		}
		this.root.rotate();;
	}
	
	public void show() {
		if(root == null) {
			System.out.println("二叉树排序树为空！");
		}
		infixOrder(root);
	}
	
	public void infixOrder(Node node) {
		if(node == null) {
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
	
	public Node search(int value) {
		if(this.root == null) {
			System.out.println("二叉排序树为空！");
			return null;
		}
		Node cur = root;
		while(true) {
			if(value == cur.getValue()) {
				return cur;
			}else if(value < cur.getValue()) {
				if(cur.getLeft() == null) {
					return null;
				}else {
					cur = cur.getLeft();
				}
			}else {
				if(cur.getRight() == null) {
					return null;
				}else {
					cur = cur.getRight();
				}
			}
		}
	}
	
	public Node delete(int value) {
		if(this.root == null) {
			System.out.println("二叉排序树为空！");
			return null;
		}
		Node parent = null; //保存删除结点的父结点
		Node cur = root; //保存需要删除的结点
		boolean isLeft = false; //需要删除的结点是父结点的左结点还是右节点
		//遍历树删除结点
		while(true) {
			if(value == cur.getValue()) { //1、找到需要删除的结点
				if((cur.getLeft() == null) && (cur.getRight() == null)) { //2、如果需要删除的结点没有子结点
					//直接删除该节点
					if(parent == null) {
						root = null;
					}else {
						if(isLeft) {
							parent.setLeft(null);
						}else {
							parent.setRight(null);
						}
					}
				}else if((cur.getLeft() != null) && (cur.getRight() == null)) { //3、如果需要删除的结点仅有一个左子结点
					if(parent == null) {
						root = root.getLeft();
					}else {
						/**
						 * 如果结点是父节点的左子节点
						 * 则将要删除的结点的左子结点放在父节点的左子结点上
						 * 否则将要删除的结点的左子结点放在父节点的右子结点上
						 */
						if(isLeft) {
							parent.setLeft(cur.getLeft());
						}else {
							parent.setRight(cur.getLeft());
						}
					}
				}else if((cur.getRight() != null) && (cur.getLeft() == null)) { //4、如果需要删除的结点仅有一个右子结点
					if(parent == null) {
						root = root.getRight();
					}else {
						/**
						 * 如果结点是父节点的右子节点
						 * 则将要删除的结点的右子结点放在父节点的左子结点上
						 * 否则将要删除的结点的右子结点放在父节点的右子结点上
						 */
						if(isLeft) { 
							parent.setLeft(cur.getRight());
						}else {
							parent.setRight(cur.getRight());
						}
					}
				}else { //5、如果需要删除的结点有左右子结点
					/***找到要删除结点的右子树的最小结点***/
					Node tempParent = cur;
					Node temp = cur.getRight();
					while(temp.getLeft() != null) {
						tempParent = temp;
						temp = temp.getLeft();
					}
					
					/***删除这个最小结点***/
					if(temp.getRight() != null) {
						tempParent.setLeft(temp.getRight());
					}else {
						tempParent.setLeft(null);
					}
					
					/******把删除结点替换成这个最小结点******/
					if(parent == null) {
						temp.setLeft(root.getLeft());
						temp.setRight(root.getRight());
						root = temp;
					}else {
						temp.setLeft(cur.getLeft());
						temp.setRight(cur.getRight());
						if(isLeft) {
							parent.setLeft(temp);
						}else {
							parent.setRight(temp);
						}
					}
				}
				return cur;
			}else if(value < cur.getValue()) { //向左查找
				if(cur.getLeft() == null) {
					return null;
				}else {
					parent = cur;
					cur = cur.getLeft();
					isLeft = true;
				}
			}else { //向右查找
				if(cur.getRight() == null) {
					return null;
				}else {
					parent = cur;
					cur = cur.getRight();
					isLeft = false;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {10,7,11,6,8,9};
		AvlTree avlTree = new AvlTree();
		for (int i : arr) {
			Node node = new Node(i);
			avlTree.add(node);
		}
		avlTree.show();
		Node root = avlTree.getRoot();
		System.out.println(root.getTreeHeight());
		System.out.println(root.getlTreeHeight());
		System.out.println(root.getrTreeHeight());
		System.out.println(root);
		System.out.println(root.getLeft());
		System.out.println(root.getRight());
		System.out.println(root.getLeft().getLeft());
		System.out.println(root.getRight().getLeft());
		System.out.println(root.getRight().getRight());
	}
}

class Node {
	private int value;
	private Node left;
	private Node right;
	private int lTreeHeight;
	private int rTreeHeight;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
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
	public int getlTreeHeight() {
		return lTreeHeight;
	}
	public void setlTreeHeight(int lTreeHeight) {
		this.lTreeHeight = lTreeHeight;
	}
	public int getrTreeHeight() {
		return rTreeHeight;
	}
	public void setrTreeHeight(int rTreeHeight) {
		this.rTreeHeight = rTreeHeight;
	}
	public int getTreeHeight() {
		return Math.max(lTreeHeight, rTreeHeight) + 1;
	}
	public Node(int value) {
		super();
		this.value = value;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}
	public void leftRotate() {
		if(this.rTreeHeight - this.lTreeHeight > 1) {
			Node node = new Node(this.value);
			node.setLeft(this.left);
			node.setRight(this.right.left);
			if(this.left != null) {
				node.setlTreeHeight(this.left.getTreeHeight());
			}
			if(this.right.left != null) {
				node.setrTreeHeight(this.right.left.getTreeHeight());
			}
			this.setValue(this.right.value);
			this.setLeft(node);
			this.setRight(this.right.right);
			this.setlTreeHeight(this.left != null ? this.left.getTreeHeight() : 0);
			this.setrTreeHeight(this.right != null ? this.right.getTreeHeight() : 0);
		}
	}
	
	public void rightRotate() {
		if(this.lTreeHeight - this.rTreeHeight > 1) {
			Node node = new Node(this.value);
			node.setLeft(this.left.right);
			node.setRight(this.right);
			node.setlTreeHeight(node.getLeft() != null ? node.getLeft().getTreeHeight() : 0);
			node.setlTreeHeight(node.getRight() != null ? node.getRight().getTreeHeight() : 0);
			this.setValue(this.left.value);
			this.setLeft(this.left.left);
			this.setRight(node);
			this.setlTreeHeight(this.left != null ? this.left.getTreeHeight() : 0);
			this.setrTreeHeight(this.right != null ? this.right.getTreeHeight() : 0);
		}
	}
	
	public void rotate() {
		if(this.rTreeHeight - this.lTreeHeight > 1) {
			if(this.right != null && this.right.getlTreeHeight() > this.right.getrTreeHeight()) {
				this.right.rightRotate();
			}
			this.leftRotate();
		}else if(this.lTreeHeight - this.rTreeHeight > 1) {
			if(this.left != null && this.left.getrTreeHeight() > this.left.getlTreeHeight()) {
				this.left.leftRotate();
			}
			this.rightRotate();
		}
	}
}