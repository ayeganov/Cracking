package chapter_three;


public class ArrayStack <E>
{
    private Object[] m_data;

    private Integer[] m_stack_pointers;
    private int m_stack_capacity;

    public ArrayStack(int stack_capacity, int num_stacks)
    {
        if (stack_capacity <= 0)
        {
            throw new IllegalArgumentException("Illegal Stack Capacity: " +
                    stack_capacity);
        }

        if(num_stacks <= 0)
        {
            throw new IllegalArgumentException("Illegal stack quantity: " + num_stacks);
        }

        m_data = new Object[stack_capacity * num_stacks];
        m_stack_pointers = new Integer[num_stacks];
        m_stack_capacity = stack_capacity;

        for(int i = 0; i < m_stack_pointers.length; i++)
        {
            m_stack_pointers[i] = i * m_stack_capacity;
        }
    }

    private void check_stack_id(int stack_id)
    {
        if(stack_id < 0 || stack_id >= m_stack_pointers.length)
        {
            throw new IllegalArgumentException("Stack IDs must be positive integers between 0 and " + (m_stack_pointers.length - 1));
        }
    }

    public void push(int stack_id, E value)
    {
        check_stack_id(stack_id);
        int limit = m_stack_capacity + m_stack_capacity * stack_id;
        if(m_stack_pointers[stack_id] >= limit)
        {
            throw new OutOfMemoryError("Stack number " + stack_id + " ran out of memory.");
        }
        m_data[m_stack_pointers[stack_id]++] = value;
    }

    public E pop(int stack_id)
    {
        check_stack_id(stack_id);
        if(isEmpty(stack_id))
        {
            throw new ArrayIndexOutOfBoundsException("Stack " + stack_id + " is empty, can't pop it.");
        }

        E value = elementData(m_stack_pointers[stack_id]);
        m_data[m_stack_pointers[stack_id]--] = null;
        return value;
    }

    public boolean isEmpty(int stack_id)
    {
        check_stack_id(stack_id);
        int pointer = m_stack_pointers[stack_id];
        int min_idx = stack_id * m_stack_capacity;
        return pointer == min_idx;
    }

    public E peek(int stack_id)
    {
        check_stack_id(stack_id);
        int pointer = m_stack_pointers[stack_id];
        return elementData(pointer);
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index)
    {
        return (E) m_data[index];
    }
}