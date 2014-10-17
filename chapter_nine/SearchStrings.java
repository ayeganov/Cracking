package chapter_nine;

public class SearchStrings
{
    private static int findString(String[] a, String term, int low, int high)
    {
        if(high < low) return -1;
        int mid = low + (high - low) / 2;
        if(a[mid].equals(""))
        {
            int left = findString(a, term, low, mid-1);
            if(left > -1) return left;
            return findString(a, term, mid+1, high);
        }
        int compare = term.compareTo(a[mid]);
        if(compare == 0)
            return mid;
        else if(compare > 0)
            return findString(a, term, mid+1, high);
        else
            return findString(a, term, low, mid-1);
    }

    public static int findString(String[] a, String term)
    {
        if(a == null || term == null) return -1;
        if(term == "")
        {
            for(int i = 0; i < a.length; i++)
            {
                if(a[i].equals("")) return i;
            }
            return -1;
        }
        return findString(a, term, 0, a.length-1);
    }
}
