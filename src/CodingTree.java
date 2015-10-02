/*
 * Mehgan Cook
 */
import java.util.PriorityQueue;


public class CodingTree {
	MyHashTable<String, Integer> mht;
	MyHashTable<String, String> codes;
	private String myMessage;
	private PriorityQueue<Tree> myTrees;



	public CodingTree(String fulltext) {
		mht = new MyHashTable<String, Integer>(32768);
		codes = new MyHashTable<String, String>(32768);
		myMessage = fulltext;
		myTrees = new PriorityQueue<Tree>();
		createTable(fulltext);
		createTrees();
		createSingleTree();
	}

	private void createTable(String message) {
		int i = 0;
		while (i < message.length()) {
			boolean flag = false;
			String temp = "";
			while ((message.charAt(i) >= 'a' && message.charAt(i) <= 'z') || 
					(message.charAt(i) >= 'A' && message.charAt(i) <= 'Z') ||
					(message.charAt(i) >= '0' && message.charAt(i) <= '9') ||
					(message.charAt(i) == '\'') || (message.charAt(i) == '-')) {
				temp += message.charAt(i);
				i++;
				flag = true;
			}
			if (flag) {
				if(!mht.containsKey(temp)) {
					mht.put(temp, 1);
				} else {
					int j = mht.get(temp) + 1;
					mht.put(temp, j);
				} 
			} else {
				String s = message.charAt(i) + "";
				i++;
				if(!mht.containsKey(s)) {
					mht.put(s, 1);
				} else {
					int j = mht.get(s) + 1;
					mht.put(s, j);
				}
			}
		}
	}
	private void createTrees() {
		int i = 0;
		while (i < mht.size()) {
			if (mht.getLocation(i) != null) {
				String word = mht.getLocation(i).getKey();
				int value = mht.getLocation(i).getValue();
				Tree tree = new Tree(value, word);
				myTrees.add(tree);
				i++;
			} else {
				i++;
			}
		}
	}

	private void createSingleTree() {	
		while (myTrees.size() != 1) {
			Tree temp1 = myTrees.poll();
			Tree temp2 = myTrees.poll();
			Tree tree = new Tree(temp1, temp2);
			myTrees.add(tree);
		}
	}

	public MyHashTable<String, String> codes() {
		Node root = myTrees.peek().root;
		traverse(root, "");
		return codes;
	}

	private void traverse(Node n, String path) {
		if (n.left != null) traverse(n.left, path+"0");
		if (n.right != null) traverse(n.right, path+"1");
		if (n.left == null && n.right == null) { 
			n.code = path;
			codes.put(n.element, n.code);
		}
	}

	public String bits() {
		StringBuilder binary = new StringBuilder();	
		int i = 0;
		while (i < myMessage.length()) {
			boolean flag = false;
			String temp = "";
			while ((myMessage.charAt(i) >= 'a' && myMessage.charAt(i) <= 'z') || 
					(myMessage.charAt(i) >= 'A' && myMessage.charAt(i) <= 'Z') ||
					(myMessage.charAt(i) >= '0' && myMessage.charAt(i) <= '9') ||
					(myMessage.charAt(i) == '\'') || (myMessage.charAt(i) == '-')) {
				temp += myMessage.charAt(i);
				i++;
				flag = true;
			}
			if (flag) {
				String value = codes.get(temp);
				binary.append(value);
			} else {
				String s = myMessage.charAt(i) + "";
				String value = codes.get(s);
				binary.append(value);
				i++;
			}
		}
		return binary.toString();
	}

	private class Tree implements Comparable<Tree> {
		Node root; 

		public Tree(Tree t1, Tree t2) {
			root = new Node();
			root.left = t1.root;
			root.right = t2.root;
			root.weight = t1.root.weight + t2.root.weight;
		}

		public Tree(int weight, String element) {
			root = new Node(weight, element);
		}

		@Override
		public int compareTo(Tree t) {
			return root.weight - t.root.weight;
		}
	}

	private class Node {
		String element;
		int weight;
		Node left;
		Node right;
		String code = "";

		public Node() {			
		}

		public Node(int weight, String element) {
			this.weight = weight;
			this.element = element;
		}
	}
}















