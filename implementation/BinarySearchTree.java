package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		root = null;
		treeSize = 0;
		this.maxEntries = maxEntries;
		this.comparator = comparator;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (key == null || value == null) {
			throw new IllegalArgumentException("Invalid arguments!");
		} else if (isFull()) {
			throw new TreeIsFullException("Tree is full!");
		} else {
			if (find(key) == null) {
				Node node = new Node(key, value);
				addToTree(root, node);
			} else {
				Node node = findAux(root, key);
				node.value = value;
			}
			return this;
		}
	}

	private Node addToTree(Node rootAux, Node node) {
		if (rootAux == null) {
			if (rootAux == root) {
				root = node;
			}
			rootAux = node;
			treeSize++;
			return node;
		} else if (comparator.compare(node.key, rootAux.key) < 0) {
			rootAux.left = addToTree(rootAux.left, node);
			return rootAux;

		} else {
			rootAux.right = addToTree(rootAux.right, node);
			return rootAux;
		}
	}

	public String toString() {
		if (isEmpty()) {
			return "EMPTY TREE";
		} else {
			return toStringAux(root);
		}
	}

	private String toStringAux(Node rootAux) {
		if (rootAux == null) {
			return "";
		} else {
			return (toStringAux(rootAux.left) + "{" + rootAux.key.toString() + ":" + rootAux.value.toString() + "}"
					+ toStringAux(rootAux.right));
		}
	}

	/* Provided: NO modify */
	public boolean isEmpty() {
		return root == null;
	}

	/* Provided: NO modify */
	public int size() {
		return treeSize;
	}

	/* Provided: NO modify */
	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty!");
		} else {
			Node node = getMinimumNode(root);
			return new KeyValuePair<>(node.key, node.value);
		}
	}

	private Node getMinimumNode(Node rootAux) {
		if (rootAux.left == null) {
			return rootAux;
		} else {
			return getMinimumNode(rootAux.left);
		}
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty!");
		} else {
			Node node = getMaximumNode(root);
			return new KeyValuePair<>(node.key, node.value);
		}
	}

	private Node getMaximumNode(Node rootAux) {
		if (rootAux.right == null) {
			return rootAux;
		} else {
			return getMaximumNode(rootAux.right);
		}
	}

	public KeyValuePair<K, V> find(K key) {
		Node node = findAux(root, key);
		if (node == null) {
			return null;
		}
		return new KeyValuePair<>(node.key, node.value);
	}

	private Node findAux(Node rootAux, K key) {
		if (rootAux == null) {
			return null;
		} else {
			if (comparator.compare(key, rootAux.key) == 0) {
				return rootAux;
			} else if (comparator.compare(key, rootAux.key) < 0) {
				return findAux(rootAux.left, key);
			} else {
				return findAux(rootAux.right, key);
			}
		}
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (key == null) {
			throw new IllegalArgumentException("Invalid arguments!");
		} else if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty!");
		} else {
			Node deleteNode = findAux(root, key);
			if (deleteNode == null) {
				return null;
			} else {
				Node replacementNode = null;
				if (deleteNode.right == null) {
					if (deleteNode.left == null) {
						removeNode(root, deleteNode.key);
						return this;
					} else {
						replacementNode = getMaximumNode(deleteNode.left);
					}
				} else {
					replacementNode = getMinimumNode(deleteNode.right);
				}
				K replacementKey = replacementNode.key;
				V replacementValue = replacementNode.value;
				if (replacementNode.left == null && replacementNode.right == null) {
					removeNode(root, replacementNode.key);
					treeSize--;
				} else {
					delete(replacementNode.key);
				}
				deleteNode.key = replacementKey;
				deleteNode.value = replacementValue;
				return this;
			}
		}
	}

	private void removeNode(Node rootAux, K key) {
		if (rootAux == null) {
			return;
		} else {
			if (comparator.compare(key, rootAux.key) == 0) {
				rootAux = null;
			} else if (comparator.compare(key, rootAux.key) < 0) {
				if (comparator.compare(key, rootAux.left.key) == 0) {
					rootAux.left = null;
				} else {
					removeNode(rootAux.left, key);
				}
			} else {
				if (comparator.compare(key, rootAux.right.key) == 0) {
					rootAux.right = null;
				} else {
					removeNode(rootAux.right, key);
				}
			}
		}
	}

	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Invalid argument!");
		} else {
			processInorderAux(root, callback);
		}
	}
	
	private void processInorderAux(Node rootAux, Callback<K, V> callback) {
		if (rootAux != null) {
			if (rootAux.left != null) {
				processInorderAux(rootAux.left, callback);
			}
			callback.process(rootAux.key, rootAux.value);
			if (rootAux.right != null) {
				processInorderAux(rootAux.right, callback);
			}
		}
	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if (lowerLimit == null || upperLimit == null || comparator.compare(lowerLimit, upperLimit) > 0) {
			throw new IllegalArgumentException("Invalid arguments!");
		} else {
			BinarySearchTree<K, V> bst = new BinarySearchTree<>(comparator, maxEntries);
			return subTreeAux(root, bst, lowerLimit, upperLimit);
		}
	}
	
	private BinarySearchTree<K, V> subTreeAux(Node rootAux, BinarySearchTree<K, V> bst, K lowerLimit, K upperLimit) {
		if (rootAux == null) {
			return bst;
		} else {
			if (comparator.compare(rootAux.key, lowerLimit) < 0) {
				subTreeAux(rootAux.right, bst, lowerLimit, upperLimit);
			} else if (comparator.compare(rootAux.key, upperLimit) > 0) {
				subTreeAux(rootAux.left, bst, lowerLimit, upperLimit);
			} else {
				Node node = new Node(rootAux.key, rootAux.value);
				bst.addToTree(bst.root, node);
				subTreeAux(rootAux.left, bst, lowerLimit, upperLimit);
				subTreeAux(rootAux.right, bst, lowerLimit, upperLimit);
			}
			return bst;
		}
	}

	public TreeSet<V> getLeavesValues() {
		TreeSet<V> treeSet = new TreeSet<V>();
		return getLeavesValuesAux(root, treeSet);
	}
	
	private TreeSet<V> getLeavesValuesAux(Node rootAux, TreeSet<V> treeSet) {
		if (rootAux != null) {
			if (rootAux.left == null && rootAux.right == null) {
				treeSet.add(rootAux.value);
			} else {
				getLeavesValuesAux(rootAux.left, treeSet);
				getLeavesValuesAux(rootAux.right, treeSet);
			}
		}
		return treeSet;
	}
	
}
