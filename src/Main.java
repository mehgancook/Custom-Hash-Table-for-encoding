/*
 * Mehgan Cook
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.BitSet;
import java.util.Scanner;


public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, NullPointerException {
		String text = "";
		long startTime = System.currentTimeMillis();
		NumberFormat nf = NumberFormat.getInstance();
		MyHashTable<String, String> myCodes = new MyHashTable<>(32768);
		PrintStream output1 = new PrintStream(new File("codes.txt"));
		PrintStream output2 = new PrintStream(new File("compressed.txt"));
		try {
			text = new Scanner( new File(".\\src\\WarAndPeace.txt") ).useDelimiter("\\A").next();
		//	text = new Scanner( new File(".\\src\\WarOfTheWorlds.txt") ).useDelimiter("\\A").next();
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
		CodingTree tree = new CodingTree(text);

		//writes to the code file.
		myCodes = tree.codes();
		output1.println(myCodes.toString());

		// creates the compressed file.
		String message = tree.bits();
		final BitSet bitSet = new BitSet();
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '0') {
				bitSet.set(i, false);
			} else {
				bitSet.set(i, true);
			}
		}
		output2.write(bitSet.toByteArray());

		//statistics of the code.
		//counts characters in War and Peace
		myCodes.stats();
		System.out.println();
		int j = 0;
		while (j < text.length()) {
			j++;
		}
		System.out.println("size before compression = " + nf.format(j));		
		System.out.println("size after compression =  " + nf.format(bitSet.toByteArray().length));
		System.out.printf("compression ratio = %.2f percent\n", ((double)bitSet.toByteArray().length / j * 100));
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Running time = "  + totalTime + " milliseconds");

	//	testHashTable();
	} 

public static void testHashTable() {
	MyHashTable<String, Integer> mht = new MyHashTable<String, Integer>(3);
	mht.put("key", 10);
	int i = mht.get("key");
	mht.put("ldy", 10);
	int j = mht.get("ldy");
	mht.put("ket", 15);
	int k = mht.get("ket");
	mht.put("key", 20);
	int l = mht.get("key");
	System.out.println(i);
	System.out.println(j);
	System.out.println(k);
	System.out.println(l);
	if (mht.containsKey("key")) {
		int m = mht.get("key");
		System.out.println(m);
		mht.put("key", ++m);
	}
	int n = mht.get("key");
	System.out.println(n);
	mht.stats();
}
}