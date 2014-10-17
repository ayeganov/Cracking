#include <bitset>
#include <iostream>
#include <string>

int set_bit(int n, int idx, bool to_one)
{
    if(to_one)
    {
        return (1 << idx) | n;
    }
    else
    {
        return (~(1 << idx)) & n;
    }
}

/**
 * Swaps the bits specified by the indices in n, based on reading the first two
 * bits in s.
 *
 * @param n - number to perform swap on
 */
int swap_bits(int n, int s, int odd_idx, int even_idx)
{
    int odd_bit = s & 2;
    int even_bit = s & 1;
    n = set_bit(n, even_idx, odd_bit > 0);
    return set_bit(n, odd_idx, even_bit > 0);
}

/**
 * Swaps even and odd bits in the number n.
 *
 * returns number with bits swapped
 */
int swap_bits(int n)
{
    int shifter = n;
    int idx = 2;
    while(shifter)
    {
        n = swap_bits(n, shifter, idx - 1, idx - 2);
        idx += 2;
        shifter >>= 2;
    }
    return n;
}

int elegant_swap_bits(int n)
{
    int odd_mask = 0xAAAAAAAA;
    int even_mask = 0x55555555;
    return ((n & odd_mask) >> 1) | ((n & even_mask) << 1);
}

int main(int argc, char* argv[])
{
    for(int i = 1; i < argc; i++)
    {
        int n = std::stoi(argv[i]);
        int swapped_n = swap_bits(n);

        std::bitset<8> bits_n(n);
        std::bitset<8> bits_swapped(swapped_n);
        std::bitset<8> elegant_swap(elegant_swap_bits(n));

        std::cout << "n = " << bits_n << " swapped " << bits_swapped << " elegant " << elegant_swap << std::endl;
    }
    return 0;
}
