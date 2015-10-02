/*
 * Mehgan Cook
 */

public class MyHashTable<K, V> {

	private HashEntry<K, V>[] hashTable;
	private int size;
	private int[] histogram;
	private int entries;
	private int maxProb;
	private int count;
	private float fillPercentage;
	private float averageProb;

	public MyHashTable(int capacity) {
		size = capacity;
		histogram = new int[size];
		hashTable = new HashEntry[size];
		maxProb = 0;
		entries = 0;
		averageProb = 0;
		count = 0;
		for (int i = 0; i < histogram.length; i++) {
			histogram[i] = 0;
		}
	}
	
	public void put(K searchKey, V newValue) {
		int hash = hash(searchKey);
		int temp = 0;
		while (hashTable[hash] != null && !hashTable[hash].getKey().equals(searchKey)) {
			hash++;
			count++;
			temp++;
			if(hash >= size) {
				hash = 0;
			}
		}
		if (hashTable[hash] == null) {
			entries++;
			count++;
			histogram[temp]++;
		}
		hashTable[hash] = new HashEntry(searchKey, newValue);
		if (temp > maxProb) {
			maxProb = temp;
		}
	}
	
	public HashEntry<K,V> getLocation(int location) {
		return hashTable[location];
	}

	public V get(K searchKey)  {
		int hash = hash(searchKey);
		while (!hashTable[hash].getKey().equals(searchKey)) {
			hash++;
			if (hash >= size) {
				hash = 0;
			}
		}
		return hashTable[hash].getValue();
	}
	
	public int size() {
		return size;
	}

	public boolean containsKey(K searchKey) {
		int hash = hash(searchKey);
		while (hashTable[hash] != null && !hashTable[hash].getKey().equals(searchKey)) {
			hash++;
			if (hash >= size) {
				hash = 0;
			}
		}
		if (hashTable[hash] == null) {
			return false;
		} else {
			return true;
		}
	}

	public void stats() {
		fillPercentage = (float) entries / size;
		averageProb = (float) count / entries;
		System.out.println("Number of Entries: " + entries);
		System.out.println("Number of Buckets: " + size);
		System.out.println("Histogram of Probes: ");
		System.out.print("[" + histogram[0]);
		for (int i = 1; i < maxProb + 1; i++) {
			System.out.print(", " + histogram[i]);
		}
		System.out.println("]");
		System.out.println("Fill Percentage: " + (fillPercentage * 100) + "%");
		System.out.println("Max Linear Prob: " + (maxProb + 1));
		System.out.println("Average Linear Prob: " + averageProb);
	}

	private int hash(K key) {
		return Math.abs(key.hashCode() % size);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{");
		int i = 0;
		int k = 0;
		while (i < size) {
			if (hashTable[i] != null) {
				s.append(hashTable[i].toString());
				i++;
			} else {
				i++;
			}
		}
		s.append("}");
		return s.toString();
	}

	public class HashEntry<K, V> {

		public K key;
		public V value;

		public HashEntry(K key, V value) {
			this.key = key;
			this.setValue(value);
		}

		public K getKey() {
			return key;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "[" + key + ", " + value + "]";
		}
	}
}





