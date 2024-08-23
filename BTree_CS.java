
/*
 * Name: Conrad Sadler
 * Project: 2-3 tree
 * Date: 05/07/2024
 * 
 * This class creates a 2-3 tree and gives it functionality to insert into ,  print out ,  and search for
 */
public class BTree_CS
{
	private TreeNode_CS root;
	
	public BTree_CS() 
	{
		this.root = new TreeNode_CS();	
	}
	/**
	 * subLevelSplit() splits the tree below the second level(first level is the root). subLevelSplit() also inserts values into the tree while maintaining 2-3 Tree rules below the second level
	 * @param currentNode - the TreeNode_CS in the 2-3 Tree where the inserted value needs to be inserted
	 * @param value - the value to be inserted into the 2-3 Tree
	 * @param savePreviousCurrentNode - the successor TreeNode_CS to currentNode 
	 * @param theNewNode - the newly created node after the last split
	 * @return TreeNode_CS in the 2-3 Tree where the splitting has ended
	 */
	public void subLevelSplit(TreeNode_CS currentNode ,  int value) 
	{
		boolean split = true;  //LCV
		int promote = -1;  //holds the number that is promoted to the next level up on 2-3 Tree
		TreeNode_CS theNewNode = null;  //holds the last valueNode from the past loop
		TreeNode_CS savePreviousCurrentNode = null;  //holds the last currentNode from the last loop
		// Beginning of splitting
		while(split == true) 
		{
			TreeNode_CS valueNode = new TreeNode_CS(); // new node
			
			if(currentNode == null)  //if currentNode is null we are at the top of the tree so ass the star on-top and return the top
			{
				valueNode.setValueOne(promote);
				if(theNewNode.getValueOne() < promote) 
				{
					valueNode.setLeftChild(theNewNode);
					theNewNode.setPrevious(valueNode);
					valueNode.setMiddleChild(savePreviousCurrentNode);
					savePreviousCurrentNode.setPrevious(valueNode);
				}
				else 
				{
					valueNode.setRightChild(theNewNode);
					theNewNode.setPrevious(valueNode);
					valueNode.setLeftChild(savePreviousCurrentNode);
					savePreviousCurrentNode.setPrevious(valueNode);
				}
				this.root = valueNode;
				// ending loop because at the top of the 2-3 Tree
				split = false;
			}
			else 
			{
				if(currentNode.getValueOne() < value && value <=  currentNode.getValueTwo()) 
				{
					//middle value OXO
					valueNode.setValueOne(currentNode.getValueOne());  //value one is new node
					promote = value;
					currentNode.setValueOne(currentNode.getValueTwo());  //moving over value two
					currentNode.setValueTwo(-1);  //erasing value two		
				}
				else if(value < currentNode.getValueOne() && currentNode.getValueTwo() !=  -1) 
				{
					valueNode.setValueOne(value);
					promote = currentNode.getValueOne();
					currentNode.setValueOne(currentNode.getValueTwo());
					currentNode.setValueTwo(-1);
				}
				else if(value < currentNode.getValueOne() && currentNode.getValueTwo() == -1) 
				{
					currentNode.setValueTwo(currentNode.getValueOne());
					currentNode.setValueOne(value);
					currentNode.setRightChild(currentNode.getMiddleChild());
					currentNode.setMiddleChild(savePreviousCurrentNode);
					currentNode.setLeftChild(theNewNode);
					theNewNode.setPrevious(currentNode);
					savePreviousCurrentNode.setPrevious(currentNode);
					
					split = false;
				}
				else if(value >=  currentNode.getValueOne() && currentNode.getValueTwo() == -1) 
				{
					currentNode.setValueTwo(value);
					currentNode.setRightChild(savePreviousCurrentNode);
					savePreviousCurrentNode.setPrevious(currentNode);
					currentNode.setMiddleChild(theNewNode);
					theNewNode.setPrevious(currentNode);
					
					split = false;
				}
				else 
				{
					valueNode.setValueOne(currentNode.getValueOne());
					promote = currentNode.getValueTwo();
					currentNode.setValueOne(value);
					currentNode.setValueTwo(-1);
				}
				//Ending promotion
				if(split == true) 
				{
					// Doing the slide
					if(currentNode.getLeftChild() !=  null && currentNode.getLeftChild().getValueOne() < promote) 
					{
						if(currentNode.getLeftChild().getValueOne() < valueNode.getValueOne()) 
						{
							valueNode.setLeftChild(currentNode.getLeftChild());
							currentNode.getLeftChild().setPrevious(valueNode);
							currentNode.setLeftChild(null);
						}
						else 
						{
							valueNode.setMiddleChild(currentNode.getLeftChild());
							currentNode.getLeftChild().setPrevious(valueNode);
							currentNode.setLeftChild(null);
						}
					}
					if(currentNode.getMiddleChild() !=  null && currentNode.getMiddleChild().getValueOne() < promote) 
					{
						valueNode.setMiddleChild(currentNode.getMiddleChild());
						currentNode.getMiddleChild().setPrevious(valueNode);
						currentNode.setMiddleChild(null);
					}
					
					if(currentNode.getLeftChild() == null) 
					{
						currentNode.setLeftChild(currentNode.getMiddleChild());
						currentNode.setMiddleChild(currentNode.getRightChild());
						currentNode.setRightChild(null);
					}
					// End of slide

					// Beginning of slide with the previous node created
					if(theNewNode !=  null) 
					{
						if(theNewNode.getValueOne() < promote) 
						{
							if(theNewNode.getValueOne() < valueNode.getValueOne()) 
							{
								valueNode.setLeftChild(theNewNode);
								theNewNode.setPrevious(valueNode);
							}
							else 
							{
								valueNode.setMiddleChild(theNewNode);
								theNewNode.setPrevious(valueNode);
							}
						}
						else 
						{
							if(theNewNode.getValueOne() < currentNode.getValueOne()) 
							{
								currentNode.setLeftChild(theNewNode);
								theNewNode.setPrevious(currentNode);
							}
							else 
							{
								currentNode.setMiddleChild(theNewNode);
								theNewNode.setPrevious(currentNode);
							}
						}
					}
					// End of the slide with the previous node created
					
					// level x of 2-3 Tree done
					
					// assigning the valueNode to previous node(theNewNode)
					theNewNode = valueNode;
					// saving currentNode before moving to the next currentNode
					savePreviousCurrentNode = currentNode;
					// value it be inserted equals promote
					value = promote;
					// moving up-to the new level
					currentNode = currentNode.getPrevious();
				}
			}
		}
		// End of splitting
	}
	/**
	 * insert() will add a value to a non full TreeNode_CS ,  but when the tree needs to be split insert() calls split()
	 * @param value - the value to be inserted into the tree
	 */
	public void insert(int value) 
	{
		boolean inserted = false;
		TreeNode_CS currentNode = root;
		
		// traverse the 2-3 Tree and insert value if there is a empty spot. If there is not a empty spot call split()
		while(inserted == false) 
		{
			if(value < currentNode.getValueOne() || currentNode.getValueOne() == -1) //< =  to handle same value
			{
				if(currentNode.getLeftChild() !=  null) 
				{
					currentNode = currentNode.getLeftChild();
				}
				else 
				{
					if(currentNode.getValueTwo() !=  -1) 
					{
						split(currentNode ,  currentNode.getPrevious() ,  0 , value);
						inserted = true;
					}
					else 
					{
						currentNode.setValueTwo(currentNode.getValueOne());
						currentNode.setValueOne(value);
						inserted = true;
					}
				}
			}
			else if(value >=  currentNode.getValueOne() && value <=  currentNode.getValueTwo()) 
			{
				if(currentNode.getMiddleChild() !=  null) 
				{
					currentNode = currentNode.getMiddleChild();
				}
				else 
				{
					split(currentNode ,  currentNode.getPrevious() ,  1 , value);
					inserted = true;
				}
			}
			else 
			{
				if(currentNode.getRightChild() !=  null) 
				{
					currentNode = currentNode.getRightChild();
				}
				else if(currentNode.getMiddleChild() !=  null) 
				{
					currentNode = currentNode.getMiddleChild();
				}
				else 
				{
					if(currentNode.getValueTwo() !=  -1) 
					{
						split(currentNode ,  currentNode.getPrevious() ,  2 , value); //split for 8
						inserted = true;
					}
					else 
					{
						currentNode.setValueTwo(value);
						inserted = true;
					}
				}
			}
		}
		// end of 2-3 Tree traversal and insert
	}
	/**
	 * split() will split the 2-3 Tree root if at or before level two(one level below the root) otherwise it will call subLevelSplit()
	 * @param currentNode - the TreeNode_CS in the 2-3 Tree where the inserted value needs to be inserted 
	 * @param previousTreeNode - the predecessor TreeNode_CS to currentNode
	 * @param action - used to save the spot where split() was called in insert to split() does not have to do the logic again
	 * @param value - the value to be inserted into the 2-3 Tree
	 */
	public void split(TreeNode_CS currentNode ,  TreeNode_CS previousTreeNode ,  int action , int value) 
	{
		if(currentNode.getPrevious() !=  null && previousTreeNode.getValueTwo() !=  -1) 
		{
			subLevelSplit(currentNode , value);
		}
		else 
		{
			TreeNode_CS splitLeft = new TreeNode_CS();
			TreeNode_CS splitRight = new TreeNode_CS();
			int leftValue = -1;
			int rightValue = -1;
			
			//Decision code
			if(action == 0) 
			{
				//value far left think x|o|o
				leftValue = value;
				rightValue = currentNode.getValueTwo();
				value = currentNode.getValueOne();
				currentNode.setValueTwo(-1);
			}
			else if(action == 1) 
			{
				//value middle o|x|o
				leftValue = currentNode.getValueOne();
				currentNode.setValueOne(value);
				rightValue = currentNode.getValueTwo();
				currentNode.setValueTwo(-1);
			}
			else 
			{
				//value far right think o|o|x
				leftValue = currentNode.getValueOne();
				rightValue = value;
				value = currentNode.getValueTwo();
				currentNode.setValueOne(currentNode.getValueTwo());
				currentNode.setValueTwo(-1);
			}
			//Decision code
			
			if(previousTreeNode == null) 
			{
				splitLeft.setValueOne(leftValue);
				currentNode.setLeftChild(splitLeft);
				splitLeft.setPrevious(currentNode);
				splitRight.setValueOne(rightValue);
				currentNode.setMiddleChild(splitRight);
				splitRight.setPrevious(currentNode);
			}
			else 
			{
				if(value > previousTreeNode.getValueOne()) 
				{
					previousTreeNode.setValueTwo(value);
					splitLeft.setValueOne(leftValue);
					previousTreeNode.setMiddleChild(splitLeft);
					splitLeft.setPrevious(previousTreeNode);
					splitRight.setValueOne(rightValue);
					previousTreeNode.setRightChild(splitRight);
					splitRight.setPrevious(previousTreeNode);
				}
				else
				{
					previousTreeNode.setValueTwo(previousTreeNode.getValueOne());
					previousTreeNode.setValueOne(value);
					previousTreeNode.setRightChild(previousTreeNode.getMiddleChild());
					splitLeft.setValueOne(leftValue);
					previousTreeNode.setLeftChild(splitLeft);
					splitLeft.setPrevious(previousTreeNode);
					splitRight.setValueOne(rightValue);
					previousTreeNode.setMiddleChild(splitRight);
					splitRight.setPrevious(previousTreeNode);
				}
			}
		}
	}
	/**
	 * printInorder() prints out the 2-3 Tree values in ascending order
	 * @param currentNode - the TreeNode_CS that printInorder() is processing
	 * @param selectChild - ensures that currentNode's children are checked from left -> middle -> right  (INITIALLY ARG. MUST BE 0)
	 */
	public void printInorder(TreeNode_CS currentNode , int selectChild)
	{
		if(currentNode !=  null) 
		{
			if(selectChild == 0) 
			{
				printInorder(currentNode.getLeftChild() , 0);
				selectChild++;
			}
			// Beginning of Printing
			if(currentNode.getValueTwo() == -1) 
			{
				System.out.print(currentNode.getValueOne() + " ,  ");
			}
			else if(currentNode.getRightChild() !=  null) 
			{
				System.out.print(currentNode.getValueOne() + " ,  ");
				printInorder(currentNode.getMiddleChild() , 0);  //going down the middle child before printing the second value in currentNode. This maintains order
				selectChild++; // increments selectChild to 2 which directs the next child as the right child once the middle child and children have been processed
				System.out.print(currentNode.getValueTwo() + " ,  ");
			}
			else
			{
				System.out.print(currentNode.getValueOne() + " ,  " + currentNode.getValueTwo() + " ,  ");
			}
			// End of Printing
			if(selectChild == 1) 
			{
				printInorder(currentNode.getMiddleChild() , 0);
				selectChild++;
			}
			if(selectChild == 2) 
			{
				printInorder(currentNode.getRightChild()  ,  0);
				selectChild++;
			}
		}
	}
	/**
	 * printPostOrder() prints out the 2-3 Tree values in descending order
	 * @param currentNode - the TreeNode_CS that printInorder() is processing
	 * @param selectChild - ensures that currentNode's children are checked from Right -> middle -> Left (INITIALLY ARG. MUST BE 2)
	 */
	public void printPostOrder(TreeNode_CS currentNode , int selectChild)
	{
		if(currentNode !=  null) 
		{
			if(selectChild == 2) 
			{
				printPostOrder(currentNode.getRightChild()  ,  2);
				selectChild--;
			}
			if(selectChild == 1 && currentNode.getRightChild() == null) //if the currentNode has three children ,  then currentNode's values must be printed out in order with middle child's values 
			{
				printPostOrder(currentNode.getMiddleChild()  ,  2);
				selectChild--;
			}
			// Beginning of Printing
			if(currentNode.getValueTwo() == -1) 
			{
				System.out.print(currentNode.getValueOne() + " ,  ");
			}
			//Printing in post order if current node has three children
			else if(currentNode.getRightChild() !=  null) 
			{
				System.out.print(currentNode.getValueTwo() + " ,  ");
				printPostOrder(currentNode.getMiddleChild()  ,  2);  
				selectChild--;
				System.out.printf(currentNode.getValueOne() + " ,  ");
			}
			else
			{
				System.out.print(currentNode.getValueTwo() + " ,  " + currentNode.getValueOne() + " ,  ");
			}
			// End of Printing
			if(selectChild == 0) 
			{
				printPostOrder(currentNode.getLeftChild() , 2);
				selectChild--;
			}
		}
	}
	/**
	 * treeSearch() searches for a value and if the value is found it returns the TreeNode_CS where the value is stored
	 * @param currentNode - the TreeNode_CS that treeSearch() is processing
	 * @param value - the value to search for
	 * @return - null if the value is not found ,  else the TreeNode_CS where the value is stored
	 */
	public TreeNode_CS treeSearch(TreeNode_CS currentNode ,  int value)
	{
		TreeNode_CS saveNode = null;
		if(currentNode !=  null) 
		{
			if(value == currentNode.getValueOne() || currentNode.getValueTwo() == value) 
			{
				return currentNode;
				
			} 
			else 
			{
				if(currentNode.getValueOne() > value) 
				{
					// go to the left
					saveNode = treeSearch(currentNode.getLeftChild() , value);
				}
				else if(currentNode.getValueTwo() > value) 
				{
					// go to the middle
					saveNode = treeSearch(currentNode.getMiddleChild() , value);
				}
				else 
				{
					// go to the right
					saveNode = treeSearch(currentNode.getRightChild() , value);
				}
			}
		}
		return saveNode;
	}
	public TreeNode_CS getRoot() 
	{
		return root;
	}
	public void setRoot(TreeNode_CS root) 
	{
		this.root = root;
	}
	
}