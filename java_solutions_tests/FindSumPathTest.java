package tests;

import org.junit.Before;
import org.junit.Test;

import chapter_four.BinaryTree;

public class FindSumPathTest
{
    private BinaryTree<Integer> bt;

    @Before
    public void setUp() throws Exception
    {
        bt = new BinaryTree<>();
        int num_elements = 1000000;
        Integer[] array = new Integer[num_elements];
        for(int i = 1; i <= num_elements; i++)
        {
            array[i-1] = i;
        }

        bt.minHeightInsert(array);
    }

    @Test
    public void test_119()
    {
        long start = System.currentTimeMillis();
        bt.findSum(17);
        System.out.println("Their method: " + (System.currentTimeMillis() - start));

        System.out.println();

        start = System.currentTimeMillis();
        bt.findSumPath(17);
        System.out.println("My method: " + (System.currentTimeMillis() - start));
    }

}
