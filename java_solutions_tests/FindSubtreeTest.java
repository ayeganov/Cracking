package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import chapter_four.BinaryTree;

public class FindSubtreeTest
{
    private BinaryTree<Integer> bt;

    @Before
    public void setUp() throws Exception
    {
        bt = new BinaryTree<>();
        bt.add(500);
        bt.add(510);
        bt.add(505);

        bt.add(490);
        bt.add(495);
        bt.add(485);
    }

    public BinaryTree<Integer> makeTree(Integer... args)
    {
        BinaryTree<Integer> new_tree = new BinaryTree<>();
        new_tree.minHeightInsert(args);
        return new_tree;
    }

    @Test
    public void test_identity()
    {
        assertTrue(bt.isSubtree(bt));
    }

    @Test
    public void test_single_node()
    {
        BinaryTree<Integer> subtree = makeTree(490);
        assertTrue(bt.isSubtree(subtree));
    }

    @Test
    public void test_left_right_side_subtree()
    {
        BinaryTree<Integer> subtree = new BinaryTree<>();
        subtree.add(510);
        subtree.add(505);
        assertTrue(bt.isSubtree(subtree));

        subtree = makeTree(485, 490, 495);
        assertTrue(bt.isSubtree(subtree));
    }

    @Test
    public void test_not_subtree()
    {
        BinaryTree<Integer> subtree = makeTree(500, 505, 510);
        assertFalse(bt.isSubtree(subtree));
    }
}
