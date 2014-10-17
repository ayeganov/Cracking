package chapter_three;

import java.util.Stack;

public class StackSorter
{
    private Stack<Integer> m_buffer;

    /**
     * Creates StackSorter
     */
    public StackSorter()
    {
        m_buffer = new Stack<>();
    }

    public void sortStackUgly(Stack<Integer> to_sort)
    {
        // Nothing to do for a stack with a single item, or nothing in it.
        if(to_sort.size() < 2)
        {
            return;
        }

        while(!to_sort.isEmpty())
        {
            if(m_buffer.isEmpty() || m_buffer.peek() <= to_sort.peek())
            {
                m_buffer.push(to_sort.pop());
            }
            else
            {
                reinsertBuffer(to_sort);
            }
        }

        while(!m_buffer.isEmpty())
        {
            to_sort.push(m_buffer.pop());
        }
    }

    /**
     * Reinserts the values collected in the buffer so far in a sorted order.
     *
     * @param value - value to be inserted back into to_sort in sorted order
     * @param to_sort - stack being sorted
     */
    private void reinsertBuffer(Stack<Integer> to_sort)
    {
        int to_sort_top = to_sort.pop();
        boolean top_pushed = false;
        while(!m_buffer.isEmpty())
        {
            if(to_sort_top < m_buffer.peek() || top_pushed)
            {
                to_sort.push(m_buffer.pop());
            }
            else
            {
                to_sort.push(to_sort_top);
                top_pushed = true;
            }
        }
        if(!top_pushed)
        {
            to_sort.push(to_sort_top);
        }
    }

    public static Stack<Integer> sortStackElegant(Stack<Integer> to_sort)
    {
        Stack<Integer> result = new Stack<>();
        while(!to_sort.isEmpty())
        {
            int tmp = to_sort.pop();
            while(!result.isEmpty() && result.peek() < tmp)
            {
                to_sort.push(result.pop());
            }
            result.push(tmp);
        }
        return result;
    }
}
