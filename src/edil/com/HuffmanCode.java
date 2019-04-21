package edil.com;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

abstract class HuffmanTree implements Comparable<HuffmanTree>  {
	public final double frequency;

	public HuffmanTree(double freq) {
		frequency = freq;
	}

	public int compareTo(HuffmanTree tree) {
		return Double.compare(frequency, tree.frequency);
	}
}

class HuffmanLeaf extends HuffmanTree {
	public final char value;

	public HuffmanLeaf(double freq, char val) {
		super(freq);
		value = val;
	}
}

class HuffmanNode extends HuffmanTree {
	public final HuffmanTree left, right;

	public HuffmanNode(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;
	}
}

public class HuffmanCode {
	private static final int ASCII_LENGTH = 7;

	public String originalString;
	public int originalStringLength;
	private HashMap<Character, String> compressedResult;
	private HashMap<String, Character> decodingTable;
	private HashMap<Character, Double> characterFrequency;
	private PriorityQueue<HuffmanTree> huffmanTrees;
	HuffmanTree mainTree;


	//constructor with string
	public HuffmanCode(String str) {
		super();
		originalString = str;
		originalStringLength = str.length();
		characterFrequency = new HashMap<Character, Double>();
		compressedResult = new HashMap<Character, String>();
        decodingTable = new HashMap<String, Character>();
		huffmanTrees = new PriorityQueue<HuffmanTree>();


		this.calculateFrequency();
		this.buildTree();
		this.buildString(mainTree, new StringBuffer(), compressedResult, decodingTable);

	}

	//constructor with string and probablity
	public HuffmanCode(String str, HashMap<Character, Double> probablity) {
		super();
		originalString = str;
		originalStringLength = str.length();

		characterFrequency = new HashMap<Character, Double>();

		double checkPoint = 0;
		for (Character c : originalString.toCharArray()) {
			checkPoint += probablity.get(c);
			characterFrequency.put(c, originalStringLength * probablity.get(c));
		}

		assert checkPoint == 1.0; // Invariant, make sure sum of probabilities
									// is 1

		compressedResult = new HashMap<Character, String>();
		huffmanTrees = new PriorityQueue<HuffmanTree>();
		decodingTable = new HashMap<String,Character> ();

		this.buildTree();
		this.buildString(mainTree, new StringBuffer(), compressedResult, decodingTable);

	}


	private void buildTree() {

		//add all alphabtes to the tree
		for (Character c : characterFrequency.keySet()) {
			huffmanTrees.offer(new HuffmanLeaf(characterFrequency.get(c), c));
		}

		//check size > 1
		assert huffmanTrees.size() >= 1; // Invariant, make sure there is at
											// least one tree exist

		// while there are two or more nodes in the tree
		while (huffmanTrees.size() >= 2) {

			//pull two min probability node
			HuffmanTree a = huffmanTrees.poll();
			HuffmanTree b = huffmanTrees.poll();

			// create new node with probability (a + b)
			huffmanTrees.offer(new HuffmanNode(a, b));
		}

		//get the last nodes
		mainTree = huffmanTrees.poll();
	}

	private void buildString(HuffmanTree tree, StringBuffer prefix, HashMap<Character, String> result, HashMap<String,Character> decodeTable) throws NullPointerException {

		//check if the tree is null
		assert tree != null; // Invariant, make sure tree is not empty

		//if the node is instance of Leaf
		if (tree instanceof HuffmanLeaf) {

			//add it to the result map
			HuffmanLeaf leaf = (HuffmanLeaf) tree;
			result.put(leaf.value, prefix.toString());
			decodeTable.put(prefix.toString(), leaf.value);

			//if the node is instance of Node
		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode) tree;

			//left leaf
			prefix.append('0');
			buildString(node.left, prefix, result, decodeTable);
			prefix.deleteCharAt(prefix.length() - 1);

			//right leaf
			prefix.append('1');
			buildString(node.right, prefix, result, decodeTable);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	private void calculateFrequency() {
		for (Character c : originalString.toCharArray()) {
			if (characterFrequency.containsKey(c)) {
				characterFrequency.put(c, new Double(characterFrequency.get(c) + 1.0));
			} else {
				characterFrequency.put(c, 1.0);
			}
		}

		for (Map.Entry<Character, String> entry : compressedResult.entrySet()) {
			char key = entry.getKey();
			String code = entry.getValue();
			decodingTable.put(code,key);
		}

		this.decodingTable = decodingTable;
	}

	public String decoding(String encoded) throws NullPointerException {

		String decoded = "", s = "";
		StringBuilder decodeBuilder = new StringBuilder();
		char decodedChar = 'a';

        //System.out.println(encoded);
        //System.out.println(decodingTable);

        for(char c : encoded.toCharArray()){

			s += c;


			if(decodingTable.containsKey(s)) {

				decodeBuilder.append(decodingTable.get(s));

				s = "";
			}

		}

		decoded = decodeBuilder.toString();
		return decoded;
	}

    public HashMap<String, Character> getDecodingTable() {
        return decodingTable;
    }

    public void setDecodingTable(HashMap<String, Character> decodingTable) {
        this.decodingTable = decodingTable;
    }

    @SuppressWarnings("unchecked")
	public HashMap<Character, Double> getCharacterFrequency() {
		return (HashMap<Character, Double>) characterFrequency.clone();
	}

	@SuppressWarnings("unchecked")
	public HashMap<Character, String> getCompressedResult() {
		return (HashMap<Character, String>) compressedResult.clone();
	}



}