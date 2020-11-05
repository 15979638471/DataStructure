package com.ryou.binarysorttree;

public class BinarySortTree {
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
				if(cur.getLeft() == null) {
					cur.setLeft(node);
					break;
				}else {
					cur = cur.getLeft();
				}
			}else {
				if(cur.getRight() == null) {
					cur.setRight(node);
					break;
				}else {
					cur = cur.getRight();
				}
			}
		}
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
		int[] arr = {5,1,7,3,11,9,2,6,10};
		BinarySortTree sortTree = new BinarySortTree();
		for (int i : arr) {
			Node node = new Node(i);
			sortTree.add(node);
		}
		sortTree.show();
//		System.out.println(sortTree.search(4));
		sortTree.delete(5);
		sortTree.show();
	}
}

class Node {
	private int value;
	private Node left;
	private Node right;
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
	public Node(int value) {
		super();
		this.value = value;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}
}