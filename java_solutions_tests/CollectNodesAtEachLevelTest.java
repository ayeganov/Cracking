package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import chapter_four.BinaryTree;
import chapter_four.BinaryTree.TreeNode;

public class CollectNodesAtEachLevelTest
{

    @Test
    public void test_num_lists_equal_to_max_depth()
    {
        BinaryTree<Integer> bt = new BinaryTree<>();
        int num_elements = 10;
        Integer[] array = new Integer[num_elements];
        for(int i = 1; i <= num_elements; i++)
        {
            array[i-1] = i;
        }
        bt.minHeightInsert(array);

        Map<Integer, List<TreeNode<Integer>>> nodes = bt.getNodesAtEachLevel();

        int depth = bt.maxDepth();
        assertEquals(depth, nodes.size());
        int total_nodes = 0;
        for(List<TreeNode<Integer>> l : nodes.values())
        {
            total_nodes += l.size();
        }
        assertEquals(bt.size(), total_nodes);
    }
}
