#include <string>
#include <iostream>
#include <string.h>

void reverse(char* s)
{
    if(!s) return;
    int start = 0;
    int end = strlen(s) - 1;
    while(start < end)
    {
        std::swap(s[start], s[end]);
        start++;
        end--;
    }
}

int main(int argc, char* argv[])
{
    if (argc < 2)
    {
        std::cerr << "You must provide at least one string." << std::endl;
    }
    else
    {
        for(int i = 1; i < argc; i++)
        {
            std::string original = std::string(argv[i]);
            reverse(argv[i]);
            std::cout << "\"" << original << "\" reversed = " << "\"" << argv[i] << "\"" << '\n';
        }
        std::cout << std::endl;
    }
    return 0;
}
