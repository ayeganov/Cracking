package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import chapter_four.BinaryTree;
import chapter_four.BinaryTree.TreeNode;

public class InOrderSuccessorTest
{
    private BinaryTree<Integer> bt;

    @Before
    public void setUp()
    {
        bt = new BinaryTree<>();
        int num_elements = 10;
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
        TreeNode<Integer> node = bt.findNode(5);
        TreeNode<Integer> successor = bt.inorderSuccessor(node);
        assertEquals((long)successor.getData(), 6);
    }

    @Test
    public void test_left_most_node()
    {
        TreeNode<Integer> node = bt.findNode(1);
        TreeNode<Integer> successor = bt.inorderSuccessor(node);
        assertEquals((long)successor.getData(), 2);
    }

    @Test
    public void test_right_most_node()
    {
        TreeNode<Integer> node = bt.findNode(10);
        TreeNode<Integer> successor = bt.inorderSuccessor(node);
        assertEquals(successor, null);
    }

    @Test
    public void test_in_middle_node()
    {
        TreeNode<Integer> node = bt.findNode(8);
        TreeNode<Integer> successor = bt.inorderSuccessor(node);
        assertEquals((long)successor.getData(), 9);
    }

    @Test
    public void test_bottom_node()
    {
        TreeNode<Integer> node = bt.findNode(7);
        TreeNode<Integer> successor = bt.inorderSuccessor(node);
        assertEquals((long)successor.getData(), 8);
        System.out.println(bt.getNodesAtEachLevel());
    }
}
