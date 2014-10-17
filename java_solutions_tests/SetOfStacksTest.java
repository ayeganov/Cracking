package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chapter_three.SetOfStacks;


public class SetOfStacksTest
{
    @Test
    public void test_single_stack_push()
    {
        SetOfStacks<Integer> sos = new SetOfStacks<>(5);
        int value = 1;
        assertTrue(sos.isEmpty());

        sos.push(value);

        assertFalse(sos.isEmpty());

        assertEquals(value, (long)sos.peek());
    }

    @Test
    public void test_new_stack_created_by_push()
    {
        SetOfStacks<Long> sos = new SetOfStacks<>(1);
        long value = 1;
        sos.push(value);
        assertEquals(1, sos.numStacks());

        sos.push(value);
        assertEquals(2, sos.numStacks());
    }

    @Test
    public void test_stack_removed_by_pop()
    {
        SetOfStacks<Long> sos = new SetOfStacks<>(1);
        long value = 1;
        sos.push(value);
        assertEquals(1, sos.numStacks());

        sos.push(value);
        assertEquals(2, sos.numStacks());

        sos.pop();
        assertEquals(1, sos.numStacks());
    }

    @Test
    public void test_pop_at()
    {
        SetOfStacks<Long> sos = new SetOfStacks<>(2);
        long num_items = 6;
        // next loop creates 3 stacks
        for(long i = 0; i < num_items; i++)
        {
            sos.push(i);
        }
        assertEquals(3, sos.numStacks());
        assertEquals(num_items, sos.size());
        long popped = sos.popAt(1);
        assertEquals(num_items-1, sos.size());
        assertEquals(3, popped);

        assertEquals(3, sos.numStacks());

        popped = sos.popAt(1);
        assertEquals(4, popped);
        assertEquals(2, sos.numStacks());
    }
}
