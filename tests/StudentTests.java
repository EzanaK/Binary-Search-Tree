package tests;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import implementation.BinarySearchTree;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
	/* Remove the following test and add your tests */
	@Test
	public void testing() {
		Comparator<String> caseInsensitiveComparator = String.CASE_INSENSITIVE_ORDER;
		BinarySearchTree<String, Integer> playerNumbertree = new BinarySearchTree<>(caseInsensitiveComparator, 20);
		try {
			playerNumbertree.add("Messi", 10);
			playerNumbertree.add("Iniesta", 8);
			playerNumbertree.add("Xavi", 6);
			playerNumbertree.add("Ramos",4);
			playerNumbertree.add("Drogba", 11);
			playerNumbertree.add("Ronaldo", 7);
			playerNumbertree.add("Messi", 30);
			System.out.println(playerNumbertree.toString() + "Tree Size: " + playerNumbertree.size());
			playerNumbertree.delete("Messi");
			System.out.println(playerNumbertree.toString() + "Tree Size: " + playerNumbertree.size());
			playerNumbertree.delete("Xavi");
			System.out.println(playerNumbertree.toString() + "Tree Size: " + playerNumbertree.size());
			playerNumbertree.delete("Drogba");
			System.out.println(playerNumbertree.toString() + "Tree Size: " + playerNumbertree.size());
			//System.out.println(playerNumbertree.find("Iniesta").getValue());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
