package edil.com;

import java.util.HashMap;
import java.util.PriorityQueue;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
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

public class HuffmanCode  {
	private static final int ASCII_LENGTH = 7;

	public String originalString;
	public int originalStringLength;
	private HashMap<Character, String> compressedResult;
	private HashMap<Character, Double> characterFrequency;
	private double entropy;
	private PriorityQueue<HuffmanTree> huffmanTrees;
	HuffmanTree mainTree;
	private double averageLengthBefore;
	private double averageLengthAfter;
	private boolean probabilityIsGiven;

	public HuffmanCode(String str) {
		super();
		originalString = str;
		originalStringLength = str.length();
		characterFrequency = new HashMap<Character, Double>();
		compressedResult = new HashMap<Character, String>();
		entropy = 0.0;
		averageLengthBefore = 0.0;
		averageLengthAfter = 0.0;
		huffmanTrees = new PriorityQueue<HuffmanTree>();
		probabilityIsGiven = false;


		this.buildTree();
		this.buildString(mainTree, new StringBuffer(), compressedResult);
	}

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
		entropy = 0.0;
		averageLengthBefore = 0.0;
		averageLengthAfter = 0.0;
		huffmanTrees = new PriorityQueue<HuffmanTree>();
		probabilityIsGiven = true;

		this.buildTree();
		this.buildString(mainTree, new StringBuffer(), compressedResult);
	}

	private void buildTree() {
		for (Character c : characterFrequency.keySet()) {
			huffmanTrees.offer(new HuffmanLeaf(characterFrequency.get(c), c));
		}

		assert huffmanTrees.size() >= 1; // Invariant, make sure there is at
											// least one tree exist

		while (huffmanTrees.size() >= 2) {
			HuffmanTree a = huffmanTrees.poll();
			HuffmanTree b = huffmanTrees.poll();

			huffmanTrees.offer(new HuffmanNode(a, b));
		}
		mainTree = huffmanTrees.poll();
	}

	private void buildString(HuffmanTree tree, StringBuffer prefix, HashMap<Character, String> result) {
		assert tree != null; // Invariant, make sure tree is not empty
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf) tree;

			result.put(leaf.value, prefix.toString());

		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode) tree;

			prefix.append('0');
			buildString(node.left, prefix, result);
			prefix.deleteCharAt(prefix.length() - 1);

			prefix.append('1');
			buildString(node.right, prefix, result);
			prefix.deleteCharAt(prefix.length() - 1);
		}
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