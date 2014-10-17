package chapter_three;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * An implementation of a queue based on two stacks.
 *
 * @author ayeganov
 *
 * @param <E>
 */
public class MyQueue<E>
{
    private Stack<E> m_in;
    private Stack<E> m_out;

    /**
     * Create empty MyQueue.
     */
    public MyQueue()
    {
        m_in = new Stack<>();
        m_out = new Stack<>();
    }

    /**
     * Inserts the provided element into the queue.
     *
     * @param element to be inserted
     */
    public void enque(E element)
    {
        m_in.push(element);
    }

    /**
     * Makes sure the output stack is not empty.
     */
    private void ensureNotEmpty()
    {
        if(m_out.isEmpty())
        {
            if(m_in.isEmpty())
            {
                throw new EmptyStackException();
            }
            restack();
        }
    }

    /**
     * Removes and returns the first element at the head of the queue.
     *
     * @return head element of the queue
     */
    public E deque()
    {
        ensureNotEmpty();
        return m_out.pop();
    }

    /**
     * Moves all elements from the input stack to the output stack.
     */
    private void restack()
    {
        while(!m_in.isEmpty())
        {
            m_out.push(m_in.pop());
        }
    }

    /**
     * Returns the element at the head of the queue, but doesn't remove it.
     *
     * @return head element of the queue
     */
    public E peek()
    {
        ensureNotEmpty();
        return m_out.peek();
    }

    /**
     * Returns number of elements in this queue.
     */
    public int size()
    {
        return m_in.size() + m_out.size();
    }
}
