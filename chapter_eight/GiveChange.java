package chapter_eight;


public class GiveChange
{
    public enum Coin
    {
        QUARTER(25),
        DIME(10),
        NICKEL(5),
        CENT(1);
        public final int value;

        private Coin(int val)
        {
            value = val;
        }
    }

    // Iterative solution
    public static int getChange(int n)
    {
        int changeWays = 0;
        for(int q = 0; q <= n / Coin.QUARTER.value; q++)
        {
            int after_quarters = n - q * Coin.QUARTER.value;
            for(int d = 0; d <= after_quarters / Coin.DIME.value; d++)
            {
                int after_dimes = after_quarters - d * Coin.DIME.value;
                for(int f = 0; f <= after_dimes / Coin.NICKEL.value; f++)
                {
                    changeWays++;
                }
            }
        }
        return changeWays;
    }

    // Recursive solution (my initial solution was based on the same principle, but was much uglier)
    // after looking at the book's solution I beautified mine.
    private static int calcChange(int n, Coin coin)
    {
        Coin next;
        switch(coin)
        {
        case QUARTER:
            next = Coin.DIME;
            break;
        case DIME:
            next = Coin.NICKEL;
            break;
        case NICKEL:
            next = Coin.CENT;
            break;
        default:
            return 1;
        }

        int change = 0;

        for(int i = 0; coin.value * i <= n; i++)
        {
            change += calcChange(n - i * coin.value, next);
        }
        return change;
    }

    public static int calcChange(int n)
    {
        return calcChange(n, Coin.QUARTER);
    }

    // Book solution
    public static int makeChange(int n, int denom)
    {
        int next_denom = 0;
        switch(denom)
        {
        case 25:
            next_denom = 10;
            break;
        case 10:
            next_denom = 5;
            break;
        case 5:
            next_denom = 1;
            break;
        case 1:
            return 1;
        }

        int ways = 0;
        for(int i = 0; i * denom <= n; i++)
        {
            ways += makeChange(n - i * denom, next_denom);
        }
        return ways;
    }
}
