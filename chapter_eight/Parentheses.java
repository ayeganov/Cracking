package chapter_eight;

import java.util.ArrayList;
import java.util.List;

public class Parentheses
{
    private static void parentheses(String s, int left, int right, List<String> combinations)
    {
        if(left < 0 || right < 0) throw new IllegalArgumentException();

        if(left + right == 0)
        {
            combinations.add(s);
        }
        else
        {
            if(left > 0)
            {
                parentheses(s + "(", left - 1, right, combinations);
            }
            if(right > left)
            {
                parentheses(s + ")", left, right - 1, combinations);
            }
        }
    }

    public static List<String> parentheses(int numPairs)
    {
        List<String> result = new ArrayList<>();
        parentheses("", numPairs, numPairs, result);
        return result;
    }
}
