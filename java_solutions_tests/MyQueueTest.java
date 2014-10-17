package tests;

import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chapter_three.MyQueue;

@RunWith(JUnit4.class)
public class MyQueueTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void test_peek_empty_queue()
    {
        exception.expect(EmptyStackException.class);
        MyQueue<Long> q = new MyQueue<>();
        q.peek();
    }

    @Test
    public void test_deque_empty_queue()
    {
        exception.expect(EmptyStackException.class);
        MyQueue<Long> q = new MyQueue<>();
        q.deque();
    }

    @Test
    public void test_enque_deque()
    {
        MyQueue<Long> q = new MyQueue<>();
        assertEquals(0, q.size());
        for(long i = 1; i <= 100; i++)
        {
            q.enque(i);
            assertEquals(i, q.size());
        }

        for(long i = 1; i <= 100; i++)
        {
            long head = q.deque();
            assertEquals(head, i);
        }
    }

    @Test
    public void test_peek()
    {
        MyQueue<Long> q = new MyQueue<>();
        assertEquals(0, q.size());
        for(long i = 1; i <= 100; i++)
        {
            q.enque(i);
            assertEquals(1, q.peek().longValue());
        }

        for(long i = 1; i <= 100; i++)
        {
            assertEquals(i, q.peek().longValue());
            q.deque();
        }
    }
}
