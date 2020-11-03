package com.ryou.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanZip {
	
	/***********************压缩*************************/
	
	/**
	 * 数据压缩方法
	 * @param arr 需要压缩的二进制数组
	 * @return HuffmanCoding类型对象
	 */
	public HuffmanCoding zip(byte[] arr) {
		//构建huffman tree
		HuffmanNode root = buildHuffmanTree(arr);
		//获得huffman编码字典
		Map<Byte, String> dictionary = getDictionary(root);
		StringBuilder codingStr = new StringBuilder();
		for(byte b : arr) {
			codingStr.append(dictionary.get(b));
		}
		//获得huffman编码数组
		byte[] codingBytes = getCodingBytes(codingStr.toString());
		//返回HuffmanCoding封装对象
		return new HuffmanCoding(codingBytes, dictionary, codingStr.length());
	}
	
	/**
	 * 构建huffman树方法
	 * @param arr 需要压缩的二进制byte数组
	 * @return HuffmanTree根结点
	 */
	private HuffmanNode buildHuffmanTree(byte[] arr) {
		//统计每八个位出现的次数
		HashMap<Byte,Integer> map = new HashMap<Byte, Integer>();
		for(byte b : arr) {
			if(map.containsKey(b)) {
				map.put(b, map.get(b) + 1);
			}else {
				map.put(b, 1);
			}
		}
		//遍历map,获得HuffmanNode集合
		List<HuffmanNode> nodes = new ArrayList<HuffmanNode>();
		for(Map.Entry<Byte, Integer> entry : map.entrySet()) {
			HuffmanNode node = new HuffmanNode(entry.getKey(), entry.getValue());
			nodes.add(node);
		}
		
		//构建huffman tree
		while(nodes.size() > 1) {
			Collections.sort(nodes);
			HuffmanNode node1 = nodes.get(0);
			HuffmanNode node2 = nodes.get(1);
			HuffmanNode huffmanNode = new HuffmanNode((byte)0, node1.getWeight() + node2.getWeight());
			huffmanNode.setLeft(node1);
			huffmanNode.setRight(node2);
			nodes.remove(0);
			nodes.set(0, huffmanNode);
		}
		return nodes.get(0);
	}
	
	/**
	 * 生成字典的方法
	 * @param root HuffmanTree根结点
	 * @return 数据字典
	 */
	private Map<Byte,String> getDictionary(HuffmanNode root) {
		Map<Byte,String> map = new HashMap<>();
		StringBuilder builder = new StringBuilder();
		if(root.getLeft() == null && root.getRight() == null) {
			map.put(root.getValue(),"0");
			return map;
		}
		getDictionaryMap(root, map, builder,"");
		return map;
	}
	
	private void getDictionaryMap(HuffmanNode node, Map<Byte,String> map, StringBuilder builder, String code) {
		if(node == null) {
			return;
		}
		StringBuilder builder2 = new StringBuilder(builder);
		builder2.append(code);
		if(node.getLeft() == null && node.getRight() == null) {
			map.put(node.getValue(), builder2.toString());
		}
		//向左递归
		getDictionaryMap(node.getLeft(), map, builder2, "0");
		//向右递归
		getDictionaryMap(node.getRight(), map, builder2, "1");
	}
	
	/**
	 * 生成最后压缩后的byte数组
	 * @param codingStr 压缩后的二进制字符串
	 * @return 压缩后的二进制byte数组
	 */
	private byte[] getCodingBytes(String codingStr) {
		int len = codingStr.length();
		byte[] codingBytes = new byte[(len + 7) / 8];
		int sIndex,eIndex;
		for(int i = 0;i < codingBytes.length;i++) {
			sIndex = i * 8;
			eIndex = sIndex + 8;
			String sub;
			if(eIndex <= len) {
				sub = codingStr.substring(sIndex,eIndex);
			}else {
				sub = codingStr.substring(sIndex);
			}
			byte b = (byte)Integer.parseInt(sub,2);
			codingBytes[i] = b;
		}
		return codingBytes;
	}
	
	/***********************解压缩*************************/
	/**
	 * 解压缩方法
	 * @param huffmanCoding 
	 * @return 解压后的byte数组
	 */
	public byte[] unzip(HuffmanCoding huffmanCoding) {
		byte[] coding = huffmanCoding.getCoding();
		Map<Byte, String> dictionary = huffmanCoding.getDictionary();
		
		//获得压缩后的二进制字符串
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i < coding.length;i++) {
			String bitString = byteToBitString(coding[i]);
			if(i == coding.length - 1) { //如果是最后一位，则需要裁剪
				bitString = bitString.substring(bitString.length() - (huffmanCoding.getZipBitLength() % 8));
			}
			builder.append(bitString);
		}
		
		//将dictionary字典调转key和value
		Map<String,Byte> map = new HashMap<>();
		for (Map.Entry<Byte,String> entry : dictionary.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		
		//将压缩后的二进制字符串解压成byte数组
		int pre = 0,end = 1;
		ArrayList<Byte> list = new ArrayList<Byte>();
		while(end <= builder.length()) {
			String key = builder.substring(pre, end);
			if(map.containsKey(key)) {
				list.add(map.get(key));
				pre = end;
			}
			end++;
		}
		int size = list.size();
		byte[] retArr = new byte[size];
		for (int i = 0;i < size;i++) {
			retArr[i] = list.get(i);
		}
		return retArr;
	}
	
	/**
	 * 将byte转换成二进制字符串
	 * @param b 需要转换的byte
	 * @return 转换后的String
	 */
	private String byteToBitString(byte b) {
		//不满8位则需要补位
		String str = Integer.toBinaryString(b | 256);
		//超过八位则需要裁剪
		return str.substring(str.length() - 8);
	}
	
	/***********************文件压缩和解压缩*************************/
	
	/**
	 * 文件压缩方法
	 * @param from 需要压缩的文件地址
	 * @param to 压缩后的文件地址
	 * @throws IOException
	 */
	public void fileZip(String from, String to) throws IOException {
		File ff = new File(from);
		if(!ff.exists()) {
			throw new IOException(from + "地址的文件不存在，请检查地址是否正确！");
		}
		try(FileInputStream fis = new FileInputStream(ff);
			FileOutputStream fos = new FileOutputStream(to);
			ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			//读取文件并以二进制形式存放在byte数组中
			int size = fis.available();
			byte[] fromByteArr = new byte[size];
			fis.read(fromByteArr);
			//解压
			HuffmanCoding huffmanCoding = zip(fromByteArr);
			//将HuffmanCoding封装对象以对象形式写入到文件中
			oos.writeObject(huffmanCoding);;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void fileUnzip(String from, String to) throws IOException {
		File ff = new File(from);
		if(!ff.exists()) {
			throw new IOException(from + "地址的文件不存在，请检查地址是否正确！");
		}
		try(FileInputStream fio = new FileInputStream(ff);
			ObjectInputStream oio = new ObjectInputStream(fio);
			FileOutputStream foo = new FileOutputStream(to)) {
			//读取解压文件，获得HuffmanCoding封装对象
			HuffmanCoding huffmanCoding = (HuffmanCoding) oio.readObject();
			//获得解压后的byte数组
			byte[] unzip = unzip(huffmanCoding);
			//写入到文件中
			foo.write(unzip);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		String test = "i like like like java do you like       java";
		HuffmanZip huffmanZip = new HuffmanZip();
		HuffmanCoding huffmanCoding = huffmanZip.zip(test.getBytes());
		byte[] unzipBitArr = huffmanZip.unzip(huffmanCoding);
		StringBuilder builder = new StringBuilder();
		for (byte b : unzipBitArr) {
			builder.append((char)b);
		}
		System.out.println(builder);
		
//		HuffmanZip huffmanZip = new HuffmanZip();
//		try {
////			huffmanZip.fileZip("D:\\MyDownloads\\text.txt", "D:\\MyDownloads\\text.zip");
//			huffmanZip.fileUnzip("D:\\MyDownloads\\text.zip", "D:\\MyDownloads\\unzip.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}

//压缩完成后的byte数组数据和字典数据的封装类
class HuffmanCoding implements Serializable {
	private byte[] coding;
	private Map<Byte,String> dictionary;
	private int zipBitLength;
	public int getZipBitLength() {
		return zipBitLength;
	}
	public void setZipBitLength(int zipBitLength) {
		this.zipBitLength = zipBitLength;
	}
	public byte[] getCoding() {
		return coding;
	}
	public void setCoding(byte[] coding) {
		this.coding = coding;
	}
	public Map<Byte, String> getDictionary() {
		return dictionary;
	}
	public void setDictionary(Map<Byte, String> dictionary) {
		this.dictionary = dictionary;
	}
	public HuffmanCoding(byte[] coding, Map<Byte, String> dictionary, int zipBitLength) {
		super();
		this.coding = coding;
		this.dictionary = dictionary;
		this.zipBitLength = zipBitLength;
	}
}

//huffman树结点
class HuffmanNode implements Comparable<HuffmanNode>{
	private byte value;
	private int weight;
	private HuffmanNode left;
	private HuffmanNode right;
	
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public HuffmanNode getLeft() {
		return left;
	}
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}
	public HuffmanNode getRight() {
		return right;
	}
	public void setRight(HuffmanNode right) {
		this.right = right;
	}
	
	public HuffmanNode(byte value, int weight) {
		super();
		this.value = value;
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "HuffmanNode [value=" + value + ", weight=" + weight + "]";
	}
	@Override
	public int compareTo(HuffmanNode node) {
		return this.weight - node.weight;
	}
}