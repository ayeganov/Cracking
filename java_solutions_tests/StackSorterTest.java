package tests;

import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Test;

import chapter_three.StackSorter;

public class StackSorterTest
{

    public boolean verifySorted(Stack<Integer> sorted)
    {
        if(sorted.size() < 2)
        {
            return true;
        }

        Integer prev = sorted.pop();
        while(!sorted.isEmpty())
        {
            int top = sorted.pop();
            assertTrue(prev <= top);
            prev = top;
        }
        return true;
    }

    @Test
    public void test_ugly_sorted_stack()
    {
        StackSorter ss = new StackSorter();
        Stack<Integer> sorted = new Stack<>();
        for(int i = 5; i > 0; i--)
        {
            sorted.push(i);
        }

        ss.sortStackUgly(sorted);
        verifySorted(sorted);
    }

    @Test
    public void test_ugly_empty_stack()
    {
        StackSorter ss = new StackSorter();
        Stack<Integer> sorted = new Stack<>();
        ss.sortStackUgly(sorted);
        verifySorted(sorted);
    }

    @Test
    public void test_ugly_single_item_stack()
    {
        StackSorter ss = new StackSorter();
        Stack<Integer> sorted = new Stack<>();
        sorted.push(1);
        ss.sortStackUgly(sorted);
        verifySorted(sorted);
    }

    @Test
    public void test_ugly_reversed_stack()
    {
        StackSorter ss = new StackSorter();
        Stack<Integer> reversed = new Stack<>();
        for(int i = 1; i <= 3; i++)
        {
            reversed.push(i);
        }

        ss.sortStackUgly(reversed);
        verifySorted(reversed);
    }

    @Test
    public void test_ugly_unsorted_stack()
    {
        StackSorter ss = new StackSorter();
        Stack<Integer> unsorted = new Stack<>();
        unsorted.push(3);
        unsorted.push(4);
        unsorted.push(1);
        unsorted.push(5);
        unsorted.push(2);

        ss.sortStackUgly(unsorted);
        verifySorted(unsorted);
    }


    @Test
    public void test_sorted_stack()
    {
        Stack<Integer> sorted = new Stack<>();
        for(int i = 5; i > 0; i--)
        {
            sorted.push(i);
        }

        sorted = StackSorter.sortStackElegant(sorted);
        verifySorted(sorted);
    }

    @Test
    public void test_empty_stack()
    {
        Stack<Integer> sorted = new Stack<>();
        sorted = StackSorter.sortStackElegant(sorted);
        verifySorted(sorted);
    }

    @Test
    public void test_single_item_stack()
    {
        Stack<Integer> sorted = new Stack<>();
        sorted.push(1);
        sorted = StackSorter.sortStackElegant(sorted);
        verifySorted(sorted);
    }

    @Test
    public void test_reversed_stack()
    {
        Stack<Integer> reversed = new Stack<>();
        for(int i = 1; i <= 3; i++)
        {
            reversed.push(i);
        }

        Stack<Integer> sorted = StackSorter.sortStackElegant(reversed);
        verifySorted(sorted);
    }

    @Test
    public void test_unsorted_stack()
    {
        Stack<Integer> unsorted = new Stack<>();
        unsorted.push(3);
        unsorted.push(4);
        unsorted.push(1);
        unsorted.push(5);
        unsorted.push(2);

        Stack<Integer> sorted = StackSorter.sortStackElegant(unsorted);
        verifySorted(sorted);
    }
}
