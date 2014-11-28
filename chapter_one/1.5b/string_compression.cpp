/**
 * Implement a method to perform basic string compression using the counts of
   repeated characters. For example, the string aabcccccaaa would become a2blc5a3.
   If the "compressed" string would not become smaller than the orig- inal string,
   your method should return the original string.
 */

#include <iostream>
#include <string>
#include <utility>

std::string compress(std::string& s)
{
    std::string compressed = "";
    char current = s[0];
    int count = 1;
    for(char c : s)
    {
        if(current != c)
        {
            compressed += current + std::to_string(count);
            current = c;
            count = 0;
        }
        ++count;
    }
    compressed += current + std::to_string(count);

    return compressed.size() < s.size() ? compressed : std::string(s);
}


int main(int argc, char* argv[])
{
    std::string test_s;
    if (argc > 1)
    {
        std::cout << "num args: " << argc << "\n";
        test_s = argv[1];
    }
    else
    {
        test_s = "aabcccccaaa";
    }

    std::string comp = compress(test_s);

    std::cout << test_s << " = " << comp << std::endl;

    return 0;
}
