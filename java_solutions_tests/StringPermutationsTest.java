package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import chapter_eight.PermutationsOfString;

public class StringPermutationsTest
{

    private long factorial(int n)
    {
        long fact = 1;
        for(int i = 1; i <= n; i++)
        {
            fact *= i;
        }
        return fact;
    }

    @Test
    public void test_bunch_of_strings()
    {
        String abc = "abcdef";
        for(int i = 0; i < abc.length(); i++)
        {
            String s = abc.substring(i);
            List<String> perms = PermutationsOfString.permute(s);
            assertEquals(factorial(s.length()), perms.size());
        }
    }
}
