#include <iostream>
#include <bitset>

int bit_substring(int n, int m, int i, int j)
{
    unsigned int max = -1;
    // j + 1 because indices are inclusive
    int left = max - ((1 << j + 1) - 1);
    int right = (1 << i) - 1;

    int n_mask = left | right;
    int m_mask = ~n_mask;

    // only take from m what will fit within the specified range
    return (n & n_mask) | ((m << i) & m_mask);
}

int main(int argc, char* argv[])
{
    std::bitset<32> n(1024);
    std::bitset<32> m(21);
    int i = 2;
    int j = 6;

    std::cout << "N = " << n << " and M = " << m << std::endl;
    std::cout << "N as int: " << (int)n.to_ulong() << " M as int: " << (int)m.to_ulong() << std::endl;
    std::bitset<32> result(bit_substring(1024, 21, i, j));
    std::cout << "bit_substring of N and M: " << result << " with i = " << i << " and j = " << j << std::endl;
    return 0;
}
