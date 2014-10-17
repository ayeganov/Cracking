package chapter_eight;

import java.util.ArrayList;
import java.util.List;

public class PermutationsOfString
{
    public static List<String> permute(String s)
    {
        if(s == null) return null;
        if(s.length() == 0)
        {
            List<String> res = new ArrayList<String>();
            res.add("");
            return res;
        }
        List<String> result = new ArrayList<String>();
        char first = s.charAt(0);
        String rest = s.substring(1);

        List<String> subPerms = permute(rest);

        for(String perm : subPerms)
        {
            for(int i = 0; i <= perm.length(); i++)
            {
                result.add(insertAt(first, perm, i));
            }
        }
        return result;
    }

    public static String insertAt(char c, String word, int idx)
    {
        String left = word.substring(0, idx);
        String right = word.substring(idx);
        return left + c + right;
    }
}
