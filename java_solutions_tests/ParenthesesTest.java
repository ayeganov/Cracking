package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import chapter_eight.Parentheses;

public class ParenthesesTest
{

    @Test
    public void test_single_pair()
    {
        List<String> parens = Parentheses.parentheses(1);
        assertEquals(1, parens.size());
    }

    @Test
    public void test_five_pair()
    {
        List<String> parens = Parentheses.parentheses(5);
        System.out.println(parens.size());
        assertEquals(42, parens.size());
    }
}
