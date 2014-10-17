package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import chapter_eight.SubsetsOfSet;

public class SubsetsOfSetTest
{

    @Test
    public void test_abc()
    {
        String string = "abcdefghijklm";
        SubsetsOfSet<Character> sos = new SubsetsOfSet<>(string);

        long start = System.currentTimeMillis();
        List<List<Character>> subsets = sos.allSubsets();
        System.out.println("My time: " + (System.currentTimeMillis() - start));

        assertEquals((long)Math.pow(2, string.length()), subsets.size());
    }

    @Test
    public void test_abc_book()
    {
        String string = "abcdefghijklm";
        List<Character> chars = new ArrayList<>();
        for(Character c : string.toCharArray())
        {
            chars.add(c);
        }

        long start = System.currentTimeMillis();
        List<List<Character>> book_subsets = SubsetsOfSet.getSubsets(chars, 0);
        System.out.println("Their time: " + (System.currentTimeMillis() - start));

        assertEquals((long)Math.pow(2, string.length()), book_subsets.size());
    }

    @Test
    public void test_123()
    {
        SubsetsOfSet<Integer> sos = new SubsetsOfSet<>(1, 2, 3);
        List<List<Integer>> subsets = sos.allSubsets();
        assertEquals((long)Math.pow(2, 3), subsets.size());
    }
}