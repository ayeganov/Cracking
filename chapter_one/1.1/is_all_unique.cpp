#include <string>
#include <iostream>

bool is_all_unique(std::string s)
{
    for(int i = 0; i < s.size(); i++)
    {
        for(int j = i + 1; j < s.size(); j++)
        {
            if ((s[i] ^ s[j]) == 0)
            {
                return false;
            }
        }
    }
    return true;
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
            std::string unique = " contains all unique characters.";
            std::string not_unique = " contains duplicate characters.";
            std::string result = is_all_unique(argv[i]) ? unique : not_unique;
            std::cout << "\"" << argv[i] << "\"" << result;
        }
        std::cout << std::endl;
    }
    return 0;
}
