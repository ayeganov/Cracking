#include <iostream>

/**
An array A[1...n] contains all the integers from 0 to n except for one number which is
missing. In this problem, we cannot access an entire integer in A with a single opera-
tion. The elements of A are represented in binary, and the only operation we can use
to access them is “fetch the jth bit of A[i]”, which takes constant time. Write code to
find the missing integer. Can you do it in O(n) time?
*/

/**
 * Fetches the bit at a given index.
 *
 * @param n - number to fetch a bit from
 * @param idx - index of the bit to fetch
 * @return the requested bit
 */
int fetch_bit(int n, int idx)
{
    int mask = 1 << idx;
    return n & mask;
}

/**
 * Iterates over the array of sequential numbers, and finds the number which is
 * missing from the sequence.
 *
 * @param a - an array of integers
 * @param n - count of numbers in the array
 * @return the missing number, or -1 to indicate all numbers are present.
 */
int find_missing_number(int* a, int n)
{
    int current = 0;
    int expected = 0;
    for(int i = 0; i < n; i++)
    {
        if(fetch_bit(a[i], 0) != expected)
        {
            return current;
        }
        expected = !expected;
        current++;
    }
    // special case - all numbers are present in the list
    return -1;
}

int main(int argc, char* argv[])
{
    int a[] = {0,1,2,4,5,6,7,8,9,11};
    std::cout << "missing number: " << find_missing_number(a, sizeof(a)) << std::endl;
    return 0;
}
