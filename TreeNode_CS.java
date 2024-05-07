/*
 * Name: Conrad Sadler
 * Project: 2-3 tree
 * Date: 05/07/2024
 * 
 * This class creates a TreeNode that contains pointers to children, keys, and predecessor pointer
 */
public class TreeNode_CS 
{	
	protected int valueOne;
	protected int valueTwo;
	protected TreeNode_CS previous;
	protected TreeNode_CS leftChild;
	protected TreeNode_CS middleChild;
	protected TreeNode_CS rightChild;
	
	public TreeNode_CS() 
	{
		this.valueOne = -1;
		this.valueTwo = -1;
		this.leftChild = null;
		this.middleChild = null;
		this.rightChild = null;
		this.previous = null;
	}

	public int getValueOne() 
	{
		return valueOne;
	}

	public void setValueOne(int valueOne) 
	{
		this.valueOne = valueOne;
	}

	public int getValueTwo() 
	{
		return valueTwo;
	}

	public void setValueTwo(int valueTwo) 
	{
		this.valueTwo = valueTwo;
	}

	public TreeNode_CS getPrevious() 
	{
		return previous;
	}

	public void setPrevious(TreeNode_CS previous) 
	{
		this.previous = previous;
	}

	public TreeNode_CS getLeftChild() 
	{
		return leftChild;
	}

	public void setLeftChild(TreeNode_CS leftChild) 
	{
		this.leftChild = leftChild;
	}

	public TreeNode_CS getMiddleChild() 
	{
		return middleChild;
	}

	public void setMiddleChild(TreeNode_CS middleChild) 
	{
		this.middleChild = middleChild;
	}

	public TreeNode_CS getRightChild() 
	{
		return rightChild;
	}

	public void setRightChild(TreeNode_CS rightChild) 
	{
		this.rightChild = rightChild;
	}	
}
