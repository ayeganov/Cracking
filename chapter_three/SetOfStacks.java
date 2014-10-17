package chapter_three;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class SetOfStacks<E>
{
    private int m_capacity;
    private List<Stack<E>> m_stacks;

    /**
     * Creates an empty SetOfStacks.
     */
    public SetOfStacks(int capacity)
    {
        m_capacity = capacity;
        m_stacks = new ArrayList<>();
        m_stacks.add(new Stack<E>());
    }

    private Stack<E> getTopStack()
    {
        return m_stacks.get(m_stacks.size()-1);
    }

    private void removeTopStack()
    {
        if(m_stacks.size() > 1)
        {
            m_stacks.remove(m_stacks.size()-1);
        }
    }


    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return  the object at the top of this stack (the last item
     *          of the <tt>Vector</tt> object).
     * @throws  EmptyStackException  if this stack is empty.
     */
    public E peek()
    {
        Stack<E> top = getTopStack();
        return top.peek();
    }

    public void push(E e)
    {
        Stack<E> top = getTopStack();
        if(top.size() >= m_capacity)
        {
            top = new Stack<>();
            top.add(e);
            m_stacks.add(top);
        }
        else
        {
            top.add(e);
        }
    }

    public E pop()
    {
        Stack<E> top = getTopStack();
        E value = null;

        if(top.size() <= 0)
        {
            throw new IndexOutOfBoundsException("Can't pop items from an empty stack.");
        }
        value = top.pop();

        if(top.isEmpty())
        {
            removeTopStack();
        }
        return value;
    }

    public E popAt(int idx)
    {
        if(idx >= m_stacks.size() || idx < 0)
        {
            throw new IndexOutOfBoundsException("Stack index is invalid.");
        }

        // Get the value first
        Stack<E> stack = m_stacks.get(idx);
        E val = stack.pop();
        Stack<E> prev = stack;

        // Now balance the stacks in front of the popped one
        for(int i = idx + 1; i < m_stacks.size(); i++) {
            Stack<E> next = m_stacks.get(i);
            E bottom = next.get(0);
            next.remove(0);
            prev.push(bottom);
            prev = next;
        }
        // check last stack is not empty
        Stack<E> top = getTopStack();
        if(top.isEmpty())
        {
            removeTopStack();
        }
        return val;
    }

    public int size()
    {
        int max_size = m_stacks.size() * m_capacity;
        return max_size + getTopStack().size() - m_capacity;
    }

    /**
     * Test if this stack has any elements in it.
     *
     * @return True if stack is empty, False otherwise
     */
    public boolean isEmpty()
    {
        Stack<E> top = getTopStack();
        return m_stacks.size() == 1 && top.size() == 0;
    }

    /**
     * Returns number of stacks in this set.
     *
     * @return  number of stacks
     */
    public int numStacks()
    {
        return m_stacks.size();
    }
}
