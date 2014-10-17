package chapter_two;

import java.util.HashSet;
import java.util.Set;

public class LinkedList<T extends Number> {

    public class Node {
        public T m_data;
        public Node m_next;

        public Node(T data)
        {
            m_data = data;
        }

        @Override
        public String toString()
        {
            return "{" + m_data + "}";
        }
    }

    private Node m_head;

    public LinkedList()
    {
        m_head = null;
    }

    public void add(T data)
    {
        if(m_head == null)
        {
            m_head = new Node(data);
        }
        else
        {
            Node iter = m_head;
            while(iter.m_next != null)
            {
                iter = iter.m_next;
            }
            iter.m_next = new Node(data);
        }
    }

    public int dedup_with_buffer()
    {
        int dups = 0;
        Set<T> seen = new HashSet<T>();
        Node previous = null;
        Node iter = m_head;
        while(iter != null)
        {
            if (seen.contains(iter.m_data))
            {
                previous.m_next = iter.m_next;
                dups++;
            }
            else
            {
                seen.add(iter.m_data);
                previous = iter;
            }
            iter = iter.m_next;
        }
        return dups;
    }

    public int dedup_no_buffer()
    {
        int dups = 0;
        Node current = m_head;
        while(current != null)
        {
            Node runner = current.m_next;
            Node previous = current;
            while(runner != null)
            {
                if(runner.m_data.equals(current.m_data))
                {
                    previous.m_next = runner.m_next;
                    dups++;
                }
                else
                {
                    previous = runner;
                }
                runner = runner.m_next;
            }
            current = current.m_next;
        }
        return dups;
    }

    public Node getNthNode(int n)
    {
        Node iter = m_head;
        int count = 0;
        while(iter != null)
        {
            count++;
            iter = iter.m_next;
        }

        int target_idx = count - n;
        if(target_idx == 0) return m_head;
        if(target_idx < 0) return null;
        iter = m_head;

        while(iter != null)
        {
            target_idx--;
            if(target_idx == 0) return iter;
            iter = iter.m_next;
        }
        throw new RuntimeException("Unexpected error occured.");
    }

    public int countList()
    {
        return countList(m_head, 0);
    }

    private int countList(Node node, int count)
    {
        if(node == null)
            return count;
        else
            return countList(node.m_next, count + 1);
    }

    public void recursiveReverse()
    {
        m_head = helperReverse(m_head, null);
    }

    private Node helperReverse(Node p, Node previous)
    {
        if (p == null)
            return p;
        else if (p.m_next == null)
        {
            p.m_next = previous;
            return p;
        }
        else
        {
            Node next = p.m_next;
            p.m_next = previous;
            return helperReverse(next, p);
        }
    }

    public void removeNode(Node n)
    {
        Node current = n;
        Node next = n.m_next;
        if(next == null) return;

        while(next.m_next != null)
        {
            current.m_data = next.m_data;
            current = next;
            next = next.m_next;
        }
        current.m_data = next.m_data;
        current.m_next = null;
    }

    public void print_list()
    {
        Node iter = m_head;
        while(iter != null)
        {
            System.out.print(iter.m_data + " ");
            iter = iter.m_next;
        }
        System.out.println();
    }

    @Override
    public String toString()
    {
        if(m_head == null) return "";
        Node iter = m_head;
        StringBuilder sb = new StringBuilder();
        do
        {
            sb.append("" + iter.m_data);
            if(iter.m_next != null)
            {
                sb.append("->");
            }
            iter = iter.m_next;
        }while(iter != null);
        return sb.toString();
    }

    /**
     * Adds two lists representing numbers.
     * @param other
     * @return
     */
    @SuppressWarnings("unchecked")
    public LinkedList<T> addLists(LinkedList<T> other)
    {
        LinkedList<T> ans = new LinkedList<>();
        if(other == null)
            return this;

        Node n1 = m_head;
        Node n2 = other.m_head;
        while(n1 != null || n2 != null)
        {
            int carry = 0;
            int sum = n1 != null ? (int) n1.m_data : 0;
            sum += n2 != null ? (int) n2.m_data : 0;
            sum += carry;
            sum += carry;

            if(sum >= 10)
            {
                ans.add((T)((Number)(sum - 10)));
                carry = 1;
            }
            else
            {
                ans.add((T)((Number)sum));
                carry = 0;
            }
            n1 = n1 != null ? n1.m_next : null;
            n2 = n2 != null ? n2.m_next : null;
        }
        return ans;
    }

    public Node findBeginning()
    {
        Node n1 = m_head;
        Node n2 = m_head;

        // Find meeting point
        while (n2 != null && n2.m_next != null)
        {
            n1 = n1.m_next;
            n2 = n2.m_next.m_next;
            if (n1 == n2)
            {
                break;
            }
        }
        // Error check - there is no meeting point, and therefore no loop
        if (n2 == null || n2.m_next == null)
            return null;

        /* Move n1 to Head. Keep n2 at Meeting Point. Each are k steps
		/* from the Loop Start. If they move at the same pace, they must
         * meet at Loop Start. */
        n1 = m_head;
        while (n1 != n2)
        {
            n1 = n1.m_next;
            n2 = n2.m_next;
        }
        // Now n2 points to the start of the loop.
        return n2;
    }

    public Node findLoop()
    {
        Node iter = m_head;
        Set<Integer> seen = new HashSet<>();
        while(iter != null)
        {
            if(seen.contains(System.identityHashCode(iter)))
                return iter;
            seen.add(System.identityHashCode(iter));
            iter = iter.m_next;
        }
        return null;
    }
}
