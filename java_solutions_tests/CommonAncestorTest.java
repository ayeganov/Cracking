package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import chapter_four.BinaryTree;
import chapter_four.BinaryTree.TreeNode;

public class CommonAncestorTest
{
    private BinaryTree<Integer> bt;

    @Before
    public void setUp()
    {
        bt = new BinaryTree<>();
        int num_elements = 100;
        Integer[] array = new Integer[num_elements];
        for(int i = 1; i <= num_elements; i++)
        {
            array[i-1] = i;
        }

        bt.minHeightInsert(array);
    }

    @Test
    public void test_root_node()
    {
        TreeNode<Integer> root = bt.findNode(50);
        TreeNode<Integer> leftMost = bt.findNode(1);
        TreeNode<Integer> rightMost = bt.findNode(100);
        TreeNode<Integer> ancestor = bt.commonAncestor(leftMost, rightMost);

        assertEquals(root, ancestor);
    }

    @Test
    public void test_non_root_node()
    {
        TreeNode<Integer> root = bt.findNode(75);
        TreeNode<Integer> left = bt.findNode(70);
        TreeNode<Integer> right = bt.findNode(90);
        TreeNode<Integer> ancestor = bt.commonAncestor(left, right);

        assertEquals(root, ancestor);
    }
}
