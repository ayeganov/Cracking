#include <bitset>
#include <cmath>
#include <iostream>
#include <string>

/**
Write a function to determine the number of bits required to convert integer A to
integer B.
*/

int count_ones(int n)
{
    int num_ones = 0;
    while(n)
    {
        num_ones = n & 1 ? num_ones + 1 : num_ones;
        n >>= 1;
    }
    return num_ones;
}

// my understanding of the problem - how many extra bits are required for
// converting one integer into another.
int convert_diff(int a, int b)
{
    return std::abs(count_ones(a) - count_ones(b));
}

// Book solution - actually calculates how many bits are different between two
// integers, which is a completely different problem.
int bit_swap_required(int a, int b)
{
    int count = 0;
    for(int c = a ^ b; c != 0; c >>= 1)
    {
        count += (c & 1);
    }
    return count;
}

int main(int argc, char* argv[])
{
    for(int i = 1; i < argc - 1; i += 2)
    {
        int a = std::stoi(argv[i]);
        int b = std::stoi(argv[i+1]);
        int difference = convert_diff(a, b);

        std::bitset<16> bits_a(a);
        std::bitset<16> bits_b(b);
        std::cout << "a = " << a << " b = " << b << " difference " << difference << std::endl;
        std::cout << "a = " << bits_a << " b = " << bits_b << std::endl;
        std::cout << "Book answer: " << bit_swap_required(a, b) << std::endl;
    }
    return 0;
}
