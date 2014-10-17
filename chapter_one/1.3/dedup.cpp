#include <string>
#include <iostream>
#include <string.h>

void _dedup(char t, char* cur)
{
    char* shift = cur;
    while(*cur)
    {
        if(*cur != t)
        {
            cur++;
            shift = cur;
            continue;
        }
        if(*shift == t && *shift)
        {
            std::cout << "Skipping " << *shift << std::endl;
            shift++;
        }
        else
        {
            std::cout << "Setting " << *cur << " to " << *shift << std::endl;
            *cur = *shift;
        }
    }
}

void dedup(char* s)
{
    char* src = s;
    while(*s)
    {
        std::cout << "next char: " << *s << std::endl;
        _dedup(*s, s + 1);
        s++;
        std::cout << src << std::endl;
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
            dedup(argv[i]);
            std::cout << "\"" << original << "\" deduped " << "\"" << argv[i] << "\"" << '\n';
        }
        std::cout << std::endl;
    }
    return 0;
}
