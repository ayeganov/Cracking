#include <iostream>

#include <string.h>


void insert_string(char* t, char* s, int n)
{
    while(n > 0)
    {
        *t++ = *s++;
        n--;
    }
}

char* replace_char(char* target, char to_replace, char* replacement)
{
    int replacement_length = strlen(replacement);
    int new_len = 0;
    int target_length = 0;
    {
        char* it = target;
        while(*it)
        {
            if(*it == to_replace)
            {
                new_len += replacement_length;
            }
            else
            {
                new_len++;
            }
            it++;
            target_length++;
        }
    }

    if(target_length == new_len) return target;

    std::cout << "not equal " << "new " << new_len << " target " << target_length << '\n';
    char* replaced_string = new char[new_len];
    {
        char* target_it = target;
        char* new_string_it = replaced_string;
        while(*target_it)
        {
            if(*target_it == to_replace)
            {
                insert_string(new_string_it, replacement, replacement_length);
                new_string_it += replacement_length;
            }
            else
            {
                *new_string_it = *target_it;
                new_string_it++;
            }
            target_it++;
        }
    }
    return replaced_string;
}

char* replace_spaces(char* s)
{
    return replace_char(s, ' ', "%20");
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
            char* replacement = replace_spaces(argv[i]);
            std::cout << "\"" << original << "\" replaced " << "\"" << replacement << "\"" << '\n';
            if(replacement != argv[i])
                delete [] replacement;
        }
        std::cout << std::endl;
    }
    return 0;
}
