package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chapter_three.ArrayStack;

public class ArrayStackTest
{

    @Test
    public void test_is_empty()
    {
        ArrayStack<Integer> as = new ArrayStack<>(10, 3);
        for(int i = 0; i < 3; i++)
        {
            assertTrue(as.isEmpty(0));
        }
    }

    @Test
    public void test_single_stack()
    {
        ArrayStack<Integer> as = new ArrayStack<>(10, 1);
        assertTrue(as.isEmpty(0));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_wrong_capacity()
    {
        new ArrayStack<>(0, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_wrong_stack_number()
    {
        new ArrayStack<>(10, 0);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void test_pop_empty_stack()
    {
        ArrayStack<Integer> as = new ArrayStack<>(10, 2);
        as.pop(1);
    }

    @Test(expected=OutOfMemoryError.class)
    public void test_stack_out_of_memory()
    {
        ArrayStack<Integer> as = new ArrayStack<>(1, 1);
        as.push(0, 1);
        as.push(0, 1);
    }
}
