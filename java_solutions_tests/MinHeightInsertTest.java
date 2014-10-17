package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chapter_four.BinaryTree;

public class MinHeightInsertTest
{

    @Test
    public void test_insert_empty_array()
    {
        BinaryTree<Integer> bt = new BinaryTree<>();

        Integer[] array = new Integer[0];
        assertEquals(bt.size(), 0);
        bt.minHeightInsert(array);
        assertEquals(bt.size(), 0);
    }

    @Test
    public void test_insert_single_value_array()
    {
        BinaryTree<Integer> bt = new BinaryTree<>();

        Integer[] array = new Integer[1];
        array[0] = 1;
        assertEquals(bt.size(), 0);
        bt.minHeightInsert(array);
        assertEquals(bt.size(), 1);
    }

    public static int binlog( int bits ) // returns 0 for bits=0
    {
        int log = 0;
        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }

    @Test
    public void test_insert_multiple_value_array()
    {
        BinaryTree<Integer> bt = new BinaryTree<>();
        int num_elements = 10;
        Integer[] array = new Integer[num_elements];
        for(int i = 0; i < num_elements; i++)
        {
            array[i] = i;
        }
        assertEquals(bt.size(), 0);
        bt.minHeightInsert(array);
        assertEquals(bt.size(), num_elements);

        int log2 = binlog(num_elements);
        assertEquals(log2+1, bt.maxDepth());
    }
}
