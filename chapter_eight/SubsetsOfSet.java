package chapter_eight;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SubsetsOfSet<T>
{
    private List<List<T>> m_set;

    /**
     * Finds all subsets of a string.
     * @param s - string
     */
    public SubsetsOfSet(String s)
    {
        if(s == null)
            throw new IllegalArgumentException("You must provide a valid string.");

        m_set = new ArrayList<>();
        for(Character c : s.toCharArray())
        {
            List<T> charset = new ArrayList<>();
            charset.add((T)c);
            m_set.add(charset);
        }
    }

    /**
     * Finds all subsets of a list of numbers.
     * @param numbers - list of numbers
     */
    public SubsetsOfSet(int... numbers)
    {
        m_set = new ArrayList<>();
        for(Integer i : numbers)
        {
            List<T> intset = new ArrayList<>();
            intset.add((T) i);
            m_set.add(intset);
        }
    }

    /**
     * Finds all subsets of the given set.
     * @return a set of all subsets
     */
    public List<List<T>> allSubsets()
    {
        List<List<T>> result = allSubsets(m_set);
        // add the empty set
        result.add(new ArrayList<T>());
        return result;
    }

    /**
     * Main function for finding the subsets. It uses a strategy of dividing the
     * set into two subsets then combining those two subsets to produce the final result.
     * @param s - original set of items.
     * @return Set with all possible subsets of s
     */
    private List<List<T>> allSubsets(List<List<T>> s)
    {
        if(s.size() < 2)
            return s;
        else
            return combine(allSubsets(s.subList(0, 1)), allSubsets(s.subList(1, s.size())));
    }

    /**
     * The item combiner - takes two sets and produces the combination of the both.
     * @param left - left side of a set
     * @param right - right side of a set
     * @return combined set of left and right sets
     */
    private List<List<T>> combine(List<List<T>> left, List<List<T>> right)
    {
        List<List<T>> combination = new ArrayList<>();
        for(List<T> l : left)
        {
            combination.add(l);
            for(List<T> r : right)
            {
                combination.add(r);
                List<T> lr = new ArrayList<>();
                lr.addAll(l);
                lr.addAll(r);
                combination.add(lr);
            }
        }
        return combination;
    }

    /**
     * Solution from the book.
     * @param set - initial "set" of items
     * @param index - starting index
     * @return list of all possible subsets
     */
    public static <T> List<List<T>> getSubsets(List<T> set, int index) {
        List<List<T>> allsubsets;
        if (set.size() == index)
        {
            allsubsets = new ArrayList<List<T>>();
            allsubsets.add(new ArrayList<T>()); // Empty set
        }
        else
        {
            allsubsets = getSubsets(set, index + 1);
            T item = set.get(index);
            List<List<T>> moresubsets = new ArrayList<>();
            for (List<T> subset : allsubsets)
            {
                List<T> newsubset = new ArrayList<T>();
                newsubset.addAll(subset); //
                newsubset.add(item);
                moresubsets.add(newsubset);
            }
            allsubsets.addAll(moresubsets);
        }
        return allsubsets;
    }

}