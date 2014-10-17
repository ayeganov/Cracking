package chapter_three;

import java.util.ArrayList;
import java.util.List;

public class MinStack
{
    private List<Integer> m_data;
    private List<Integer> m_min_indices;

    public MinStack()
    {
        m_data = new ArrayList<>();
        m_min_indices = new ArrayList<>();
    }

    private int getMinIndex()
    {
        if(m_min_indices.isEmpty())
        {
            return 0;
        }
        return m_min_indices.get(m_min_indices.size()-1);
    }

    public void push(Integer value)
    {
        m_data.add(value);
        if(value < m_data.get(getMinIndex()))
        {
            m_min_indices.add(m_data.size()-1);
        }
    }

    public int size()
    {
        return m_data.size();
    }

    public Integer pop()
    {
        if(m_data.isEmpty())
        {
            throw new ArrayIndexOutOfBoundsException("The stack is empty. Check stack size before popping.");
        }

        int top_idx = m_data.size() - 1;
        if(top_idx == getMinIndex() && !m_min_indices.isEmpty())
        {
            m_min_indices.remove(m_min_indices.size()-1);
        }
        Integer top = m_data.get(top_idx);
        m_data.remove(top_idx);
        return top;
    }

    public Integer min()
    {
        if(m_data.isEmpty())
        {
            throw new ArrayIndexOutOfBoundsException("The stack is empty - no min present.");
        }
        return m_data.get(getMinIndex());
    }
}
