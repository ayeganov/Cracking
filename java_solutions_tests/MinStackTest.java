package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chapter_three.MinStack;

public class MinStackTest
{

    @Test
    public void test()
    {
        MinStack min_stack = new MinStack();
        min_stack.push(0);
        min_stack.push(1);
        min_stack.push(3);

        assertEquals(0, (long)min_stack.min());

        long val = min_stack.pop();
        assertEquals(3, val);

        assertEquals(0, (long)min_stack.min());

        min_stack.push(-1);

        assertEquals(-1, (long)min_stack.min());

        min_stack.push(3);

        min_stack.push(-2);
        min_stack.push(10);

        min_stack.push(12);

        assertEquals(-2, (long)min_stack.min());

        min_stack.pop();

        assertEquals(-2, (long)min_stack.min());

        min_stack.pop();
        min_stack.pop();
        assertEquals(-1, (long)min_stack.min());
    }
}
