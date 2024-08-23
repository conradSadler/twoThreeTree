
/*
 * Name: Conrad Sadler
 * Project: 2-3 tree
 * Date: 05/07/2024
 * 
 * Used to create and test 2-3 Tree
 */
public class TreeDriver_CS {

	public static void main(String[] args) 
	{
		BTree_CS testTree = new BTree_CS();  //creating tree
		int[] treeValues = {18,32,12,23,30,48,10,15,20,21,24,31,45,47,50,52,11,9,8,7};
		for(int value : treeValues) 
		{
			testTree.insert(value);
		}
		testTree.printInorder(testTree.getRoot(),0);  //the second argument must be zero
		System.out.println();
		testTree.printPostOrder(testTree.getRoot(),2);  //the second argument must be two
	}

}
