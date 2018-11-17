
import java.util.Scanner;
import edu.princeton.cs.algs4.*;

public class Solution {

	// Don't modify this method.
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String cases = scan.nextLine();

		switch (cases) {
		case "loadDictionary":
			// input000.txt and output000.txt
			BinarySearchST<String, Integer> hashST = loadDictionary("/Files/t9.csv");
			while (scan.hasNextLine()) {
				String key = scan.nextLine();
				System.out.println(hashST.get(key));
			}
			break;

		case "getAllPrefixes":
			// input001.txt and output001.txt
			T9 t9 = new T9(loadDictionary("/Files/t9.csv"));
			while (scan.hasNextLine()) {
				String prefix = scan.nextLine();
				for (String each : t9.getAllWords(prefix)) {
					System.out.println(each);
				}
			}
			break;

		case "potentialWords":
			// input002.txt and output002.txt
			t9 = new T9(loadDictionary("/Files/t9.csv"));
			int count = 0;
			while (scan.hasNextLine()) {
				String t9Signature = scan.nextLine();
				for (String each : t9.potentialWords(t9Signature)) {
					count++;
					System.out.println(each);
				}
			}
			if (count == 0) {
				System.out.println("No valid words found.");
			}
			break;

		case "topK":
			// input003.txt and output003.txt
			t9 = new T9(loadDictionary("/Files/t9.csv"));
			Bag<String> bag = new Bag<String>();
			int k = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				bag.add(line);
			}
			for (String each : t9.getSuggestions(bag, k)) {
				System.out.println(each);
			}

			break;

		case "t9Signature":
			// input004.txt and output004.txt
			t9 = new T9(loadDictionary("/Files/t9.csv"));
			bag = new Bag<String>();
			k = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				for (String each : t9.t9(line, k)) {
					System.out.println(each);
				}
			}
			break;

		default:
			break;

		}
	}

	// Don't modify this method.
	public static String[] toReadFile(String file) {
		In in = new In(file);
		return in.readAllStrings();
	}

	public static BinarySearchST<String, Integer> loadDictionary(String file) {
		BinarySearchST<String, Integer>  st = new BinarySearchST<String, Integer>();
		String[] lines = toReadFile(file);
		for (int i = 0; i < lines.length; i++) {
			String[] words = lines[i].split(" ");
			for (String word : words) {
				word = word.toLowerCase();
				if (word.length() >= 1) {
					if (st.contains(word)) {
						st.put(word, st.get(word) + 1);
					} else {
						st.put(word, 1);
					}
				}
			}
		}
		return st;
	}

}

class T9 {

	TST<Integer> tst = new TST<Integer>();
	BinarySearchST<Character, String> t9Map;

	public T9(BinarySearchST<String, Integer> st) {
		for (String key : st.keys()) {
			tst.put(key, st.get(key));
		}
	}

	// get all the prefixes that match with given prefix.
	public Iterable<String> getAllWords(String prefix) {
		return tst.keysWithPrefix(prefix);
	}

	private void mapT9Values() {
		t9Map = new BinarySearchST<Character, String>();
		t9Map.put('2', "abc");
		t9Map.put('3', "def");
		t9Map.put('4', "ghi");
		t9Map.put('5', "jkl");
		t9Map.put('6', "mno");
		t9Map.put('7', "pqrs");
		t9Map.put('8', "tuv");
		t9Map.put('9', "wxyz");
	}

	public void dfs(String t9Signature, Queue<String> queue, int d, String s) {
		if (!tst.hasPrefix(s)) {
			return;
		}

		if (s.length() == t9Signature.length() && tst.contains(s)) {
			// System.out.println("In Queue : " + s);
			queue.enqueue(s);
		}

		if (d == t9Signature.length()-1) {
			return;
		}

		String t9KeyChars = t9Map.get(t9Signature.charAt(d+1));
		for (int j = 0; j < t9KeyChars.length(); j++) {
			char ch = t9KeyChars.charAt(j);
			String newString = s + ch;
			dfs(t9Signature, queue, d+1, newString);
		}
	}

	public Iterable<String> potentialWords(String t9Signature) {
		Queue<String> queue = new Queue<String>();
		mapT9Values();
		String t9KeyChars = t9Map.get(t9Signature.charAt(0));
		for (int d = 0; d < t9KeyChars.length(); d++) {
			String s = "" + t9KeyChars.charAt(d);
			dfs(t9Signature, queue, 0, s);
		}
		return queue;
	}

	// return all possibilities(words), find top k with highest frequency.
	public Iterable<String> getSuggestions(Iterable<String> words, int k) {
		MaxPQ<Pair> pq = new MaxPQ<Pair>();
		SET<String> queue = new SET<String>();
		SET<String> set = new SET<String>();
		for (String prefix : words) {
			for (String key : getAllWords(prefix)) {
				set.add(key);
			}
		}
		for (String key : set) {
			pq.insert(new Pair(key, tst.get(key)));
		}
		for (int i = 0; !pq.isEmpty() && i < k; i++) {
			String key = pq.delMax().getKey();
			int value = tst.get(key);
			queue.add(key);
		}
		return queue;
	}

	// final output
	// Don't modify this method.
	public Iterable<String> t9(String t9Signature, int k) {
		return getSuggestions(potentialWords(t9Signature), k);
	}
}


class Pair implements Comparable {
	private String key;
	private int value;

	public Pair(String key, int value) {
		this.key = key;
		this.value = value;
	}
	 
	public int getValue() {
	    return value;
	}
	 
	public void setValue(int value) {
	    this.value = value;
	}
	 
	public String getKey() {
	    return key;
	}
	 
	public void setKey(String key) {
	    this.key = key;
	}
	
	public int compareTo(Object obj) {
		Pair that = (Pair)obj;
		if (this.value < that.value) {
			return -1;
		}
		if (this.value > that.value) {
			return 1;
		}
		if (this.getKey().compareTo(that.getKey()) < 0) {
			return -1;
		}
		return 1;
	}

}