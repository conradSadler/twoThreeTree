
/*
 * Name: Conrad Sadler
 * Project: 2-3 tree
 * Date: 05/07/2024
 * 
 * This class creates a 2-3 tree and gives it functionality to insert into, print out, and search for
 */
public class BTree_CS extends TreeNode_CS
{
	private TreeNode_CS root;
	
	public BTree_CS() 
	{
		super();
		this.root = new TreeNode_CS();	
	}
	/**
	 * subLevelSplit() split the tree below the second level(first level is the root). subLevelSplit() also inserts value into the tree while maintaining 2-3 Tree rules
	 * @param currentNode - the TreeNode_CS in the 2-3 Tree where the inserted value needs to be inserted
	 * @param value - the value to be inserted into the 2-3 Tree
	 * @param savePreviousCurrentNode - the successor TreeNode_CS to currentNode 
	 * @param theNewNode - the newly created node after the last split
	 * @return - TreeNode_CS in the 2-3 Tree where the splitting has ended
	 */
	public void subLevelSplit(TreeNode_CS currentNode, int value) 
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
				valueNode.valueOne = promote;
				if(theNewNode.valueOne < promote) 
				{
					valueNode.leftChild = theNewNode;
					theNewNode.previous = valueNode;
					valueNode.middleChild = savePreviousCurrentNode;
					savePreviousCurrentNode.previous = valueNode;
				}
				else 
				{
					valueNode.rightChild = theNewNode;
					theNewNode.previous = valueNode;
					valueNode.leftChild = savePreviousCurrentNode;
					savePreviousCurrentNode.previous = valueNode;
				}
				this.root = valueNode;
				// ending loop because at the top of the 2-3 Tree
				split = false;
			}
			else 
			{
				if(currentNode.valueOne < value && value <= currentNode.valueTwo) 
				{
					//middle value OXO
					valueNode.valueOne = currentNode.valueOne;  //value one is new node
					promote = value;
					currentNode.valueOne = currentNode.valueTwo;  //moving over value two
					currentNode.valueTwo = -1;  //erasing value two		
				}
				else if(value < currentNode.valueOne && currentNode.valueTwo != -1) 
				{
					valueNode.valueOne = value;
					promote = currentNode.valueOne;
					currentNode.valueOne = currentNode.valueTwo;
					currentNode.valueTwo = -1;
				}
				else if(value < currentNode.valueOne && currentNode.valueTwo == -1) 
				{
					currentNode.valueTwo = currentNode.valueOne;
					currentNode.valueOne = value;
					currentNode.rightChild = currentNode.middleChild;
					currentNode.middleChild = savePreviousCurrentNode;
					currentNode.leftChild = theNewNode;
					theNewNode.previous = currentNode;
					savePreviousCurrentNode.previous = currentNode;
					
					split = false;
				}
				else if(value >= currentNode.valueOne && currentNode.valueTwo == -1) 
				{
					currentNode.valueTwo = value;
					currentNode.rightChild = savePreviousCurrentNode;
					savePreviousCurrentNode.previous = currentNode;
					currentNode.middleChild = theNewNode;
					theNewNode.previous = currentNode;
					
					split = false;
				}
				else 
				{
					valueNode.valueOne = currentNode.valueOne;
					promote = currentNode.valueTwo;
					currentNode.valueOne = value;
					currentNode.valueTwo = -1;
				}
				//Ending promotion
				if(split == true) 
				{
					// Doing the slide
					if(currentNode.leftChild != null && currentNode.leftChild.valueOne < promote) 
					{
						if(currentNode.leftChild.valueOne < valueNode.valueOne) 
						{
							valueNode.leftChild = currentNode.leftChild;
							currentNode.leftChild.previous = valueNode;
							currentNode.leftChild = null;
						}
						else 
						{
							valueNode.middleChild = currentNode.leftChild;
							currentNode.leftChild.previous = valueNode;
							currentNode.leftChild = null;
						}
					}
					if(currentNode.middleChild != null && currentNode.middleChild.valueOne < promote) 
					{
						valueNode.middleChild = currentNode.middleChild;
						currentNode.middleChild.previous = valueNode;
						currentNode.middleChild = null;
					}
					
					if(currentNode.leftChild == null) 
					{
						currentNode.leftChild = currentNode.middleChild;
						currentNode.middleChild = currentNode.rightChild;
						currentNode.rightChild = null;
					}
					// End of slide

					// Beginning of slide with the previous node created
					if(theNewNode != null) 
					{
						if(theNewNode.valueOne < promote) 
						{
							if(theNewNode.valueOne < valueNode.valueOne) 
							{
								valueNode.leftChild = theNewNode;
								theNewNode.previous = valueNode;
							}
							else 
							{
								valueNode.middleChild = theNewNode;
								theNewNode.previous = valueNode;
							}
						}
						else 
						{
							if(theNewNode.valueOne < currentNode.valueOne) 
							{
								currentNode.leftChild = theNewNode;
								theNewNode.previous = currentNode;
							}
							else 
							{
								currentNode.middleChild = theNewNode;
								theNewNode.previous = currentNode;
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
					currentNode = currentNode.previous;
				}
			}
		}
		// End of splitting
	}
	/**
	 * insert() will add a value to a non full TreeNode_CS, but when the tree needs to be split insert() calls split()
	 * @param value - the value to be inserted into the tree
	 */
	public void insert(int value) 
	{
		boolean inserted = false;
		TreeNode_CS currentNode = root;
		
		// traverse the 2-3 Tree and insert value if there is a empty spot. If there is not a empty spot call split()
		while(inserted == false) 
		{
			if(value < currentNode.valueOne || currentNode.valueOne == -1) //<= to handle same value
			{
				if(currentNode.leftChild != null) 
				{
					currentNode = currentNode.leftChild;
				}
				else 
				{
					if(currentNode.valueTwo != -1) 
					{
						split(currentNode, currentNode.previous, 0,value);
						inserted = true;
					}
					else 
					{
						currentNode.valueTwo = currentNode.valueOne;
						currentNode.valueOne = value;
						inserted = true;
					}
				}
			}
			else if(value >= currentNode.valueOne && value <= currentNode.valueTwo) 
			{
				if(currentNode.middleChild != null) 
				{
					currentNode = currentNode.middleChild;
				}
				else 
				{
					split(currentNode, currentNode.previous, 1,value);
					inserted = true;
				}
			}
			else 
			{
				if(currentNode.rightChild != null) 
				{
					currentNode = currentNode.rightChild;
				}
				else if(currentNode.middleChild != null) 
				{
					currentNode = currentNode.middleChild;
				}
				else 
				{
					if(currentNode.valueTwo != -1) 
					{
						split(currentNode, currentNode.previous, 2,value); //split for 8
						inserted = true;
					}
					else 
					{
						currentNode.valueTwo = value;
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
	 * @param previous - the predecessor TreeNode_CS to currentNode
	 * @param action - used to save the spot where split() was called in insert to split() does not have to do the logic again
	 * @param value - the value to be inserted into the 2-3 Tree
	 */
	public void split(TreeNode_CS currentNode, TreeNode_CS previous, int action,int value) 
	{
		if(currentNode.previous != null && previous.valueTwo != -1) 
		{
			subLevelSplit(currentNode,value);
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
				rightValue = currentNode.valueTwo;
				value = currentNode.valueOne;
				currentNode.valueTwo = -1;
			}
			else if(action == 1) 
			{
				//value middle o|x|o
				leftValue = currentNode.valueOne;
				currentNode.valueOne = value;
				rightValue = currentNode.valueTwo;
				currentNode.valueTwo = -1;
			}
			else 
			{
				//value far right think o|o|x
				leftValue = currentNode.valueOne;
				rightValue = value;
				value = currentNode.valueTwo;
				currentNode.valueOne = currentNode.valueTwo;
				currentNode.valueTwo = -1;
			}
			//Decision code
			
			if(previous == null) 
			{
				splitLeft.valueOne = leftValue;
				currentNode.leftChild = splitLeft;
				splitLeft.previous = currentNode;
				splitRight.valueOne = rightValue;
				currentNode.middleChild = splitRight;
				splitRight.previous = currentNode;
			}
			else 
			{
				if(value > previous.valueOne) 
				{
					previous.valueTwo = value;
					splitLeft.valueOne = leftValue;
					previous.middleChild = splitLeft;
					splitLeft.previous = previous;
					splitRight.valueOne = rightValue;
					previous.rightChild = splitRight;
					splitRight.previous = previous;
				}
				else
				{
					previous.valueTwo = previous.valueOne;
					previous.valueOne = value;
					previous.rightChild = previous.middleChild;
					splitLeft.valueOne = leftValue;
					previous.leftChild = splitLeft;
					splitLeft.previous = previous;
					splitRight.valueOne = rightValue;
					previous.middleChild = splitRight;
					splitRight.previous = previous;
				}
			}
		}
	}
	/**
	 * printInorder() prints out the 2-3 Tree values in ascending order
	 * @param currentNode - the TreeNode_CS that printInorder() is processing
	 * @param selectChild - ensures that currentNode's children are checked from left -> middle -> right
	 */
	public void printInorder(TreeNode_CS currentNode,int selectChild)
	{
		if(currentNode != null) 
		{
			if(selectChild == 0) 
			{
				printInorder(currentNode.leftChild,0);
				selectChild++;
			}
			// Beginning of Printing
			if(currentNode.valueTwo == -1) 
			{
				System.out.print(currentNode.valueOne + ", ");
			}
			else 
			{
				System.out.print(currentNode.valueOne + ", "+ currentNode.valueTwo + ", ");
			}
			// End of Printing
			if(selectChild == 1) 
			{
				printInorder(currentNode.middleChild,0);
				selectChild++;
			}
			if(selectChild == 2) 
			{
				printInorder(currentNode.rightChild,0);
				selectChild++;
			}
		}
	}
	/**
	 * treeSearch() searches for a value and if the value is found it returns the TreeNode_CS where the value is stored
	 * @param currentNode - the TreeNode_CS that treeSearch() is processing
	 * @param value - the value to search for
	 * @return - null if the value is not found, else the TreeNode_CS where the value is stored
	 */
	public TreeNode_CS treeSearch(TreeNode_CS currentNode, int value)
	{
		TreeNode_CS saveNode = null;
		if(currentNode != null) 
		{
			if(value == currentNode.valueOne || currentNode.valueTwo == value) 
			{
				return currentNode;
				
			} 
			else 
			{
				if(currentNode.valueOne > value) 
				{
					// go to the left
					saveNode = treeSearch(currentNode.leftChild,value);
				}
				else if(currentNode.valueTwo > value) 
				{
					// go to the middle
					saveNode = treeSearch(currentNode.middleChild,value);
				}
				else 
				{
					// go to the right
					saveNode = treeSearch(currentNode.rightChild,value);
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
