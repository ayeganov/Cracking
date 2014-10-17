#include <bitset>
#include <iostream>
#include <string>

int find_first_one(int n, int start=0)
{
    int idx = start;
    while(!((n>>idx) & 1)) // is first bit 0
    {
        idx++;
    }
    return idx;
}

int find_first_zero(int n, int start=0)
{
    int idx = start;
    while((n>>idx) & 1) // is first bit 1
    {
        idx++;
    }
    return idx;
}

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

int largest(int n)
{
    if(n == 0) return 0;
    int idx = find_first_one(n);
    idx = find_first_zero(n, idx);
    n = set_bit(n, idx, true);
    return set_bit(n, idx - 1, false);
}

int smallest(int n)
{
    if(n == 0) return 0;
    int idx = find_first_zero(n);
    idx = find_first_one(n, idx);
    n = set_bit(n, idx, false);
    return set_bit(n, idx-1, true);
}

int main(int argc, char* argv[])
{
    for(int i = 1; i < argc; i++)
    {
        int n = std::stoi(argv[i]);
        int next_l = largest(n);
        int next_s = smallest(n);
        std::bitset<16> bits_n(n);
        std::bitset<16> bits_largest(next_l);
        std::bitset<16> bits_smallest(next_s);
        std::cout << "n = " << n << " next largest " << next_l << " next smallest " << next_s << std::endl;
        std::cout << "n = " << bits_n << " next largest " << bits_largest << " next smallest " << bits_smallest << std::endl;
    }
    return 0;
}
