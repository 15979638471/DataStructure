package com.ryou.linkedlist;

public class Josephu {
	
	public static void main(String[] args) {
		// ���Թ������������ͱ���
		CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
		circleSingleLinkedList.addBoy(125);//����5��С���ڵ�
		circleSingleLinkedList.showBoy();
		
		//����С����Ȧ�Ƿ���ȷ
		circleSingleLinkedList.countBoy(5, 20, 125);
	}
	
}

//����һ�����εĵ�������
class CircleSingleLinkedList {
	//����һ��first�ڵ㣬��ǰû�б��
	private Boy first = null;
	//����С���ڵ㣬����һ�����ε�����
	public void addBoy(int nums) {
		//�� ��һ������У��
		if(nums < 1) {
			System.out.println("nums��ֵ����ȷ");
			return;
		}
		Boy curBoy = null; //����ָ�룬�������������б�
		//ʹ��for���������ǵĻ�������
		for (int i = 1; i <= nums; i++) {
			//���ݱ�ţ�����С���ڵ�
			Boy boy = new Boy(i);
			//����ǵ�һ��С��
			if (i == 1) {
				first = boy;
				first.setNext(first);//���ɻ�
				curBoy = first; //��curboyָ���һ��С��
			} else {
				curBoy.setNext(boy);
				boy.setNext(first);
				curBoy = boy;
			}
		}
	}
	
	//������ǰ�Ļ�������
	public void showBoy() {
		//�ж������Ƿ�Ϊ��
		if (first == null) {
			System.out.println("û���κ�С��");
			return;
		}
		//��Ϊfirst���ܶ��������û��Ȼ��Ҫʹ��һ������ָ����ɱ���
		Boy curBoy = first;
		while(true) {
			System.out.println("С���ı��" + curBoy.getNo());
			if (curBoy.getNext() == first) {//˵���Ѿ��������
				break;
			}
			curBoy = curBoy.getNext();//curBoy����
		}
	}
	
	//�����û������룬�����С����Ȧ��˳��
	/**
	 * @param startNo ��ʾ�ӵڼ���С����ʼ����
	 * @param countNum ��ʾ������
	 * @param nums ��ʾ����ж���С����Ȧ��
	 */
	public void countBoy(int startNo, int countNum, int nums) {
		//�ȶ����ݽ���У��
		if (first == null || startNo < 1 || startNo > nums) {
			System.out.println("����������������������");
			return;
		}
		//����һ������ָ�룬�������С����Ȧ
		Boy helper = first;
		//���󴴽�һ������ָ�루������helper������Ӧ��ָ�����������������ڵ�
		while(true) {
			if(helper.getNext() == first) {//˵��helperָ�����С���ڵ�
				break;
			}
			helper = helper.getNext();
		}
		//С������ǰ������first��helper�ƶ�k-1��
		for (int i = 0; i < startNo - 1; i++) {
			first = first.getNext();
			helper = helper.getNext();
		}
		//��С������ʱ����first��helperָ��ͬʱ���ƶ�m -1�Σ�Ȼ���Ȧ
		//������һ��ѭ��������֮��Ȧ��ֻ��һ���ڵ�
		while(true) {
			if(helper == first) {//˵��Ȧ��ֻ��һ���ڵ�
				break;
			}
			//��first��helperָ��ͬʱ���ƶ�countNum -1
			for (int i = 0; i < countNum - 1; i++) {
				first = first.getNext();
				helper = helper.getNext();
			}
			//��ʱfirstָ��Ľڵ����Ҫ��Ȧ��С���ڵ�
			System.out.println("С��" + first.getNo() + "��Ȧ");
			//��ʱ��firstָ���С���ڵ��Ȧ
			first = first.getNext();
			helper.setNext(first);
		}
		System.out.println("�������Ȧ�е�С�����"+first.getNo());
	}
}

//����һ��Boy�࣬��ʾһ���ڵ�
class Boy {
	private int no;//���
	private Boy next;//ָ����һ���ڵ㣬Ĭ��null
	public Boy(int no) {
		this.no = no;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Boy getNext() {
		return next;
	}
	public void setNext(Boy next) {
		this.next = next;
	}
	
}