#include <iostream>
#include <stdexcept>
#include <string>
#include <sstream>

std::string dec_to_bin(std::string decimal, bool best_approximation=false)
{
    double number = std::stod(decimal);
    int int_part = static_cast<int>(number);
    double decimal_part = number - int_part;

    std::stringstream int_side;
    std::stringstream dec_side;

    while(int_part > 0)
    {
        int r = int_part % 2;
        int_part >>= 1;
        int_side << r;
    }

    while(decimal_part > 0)
    {
        if(dec_side.str().size() > 32)
        {
            if(best_approximation) break;
            return std::move(std::string("ERROR"));
        }
        if(decimal_part == 1)
        {
            dec_side << '1';
            break;
        }
        double r = decimal_part * 2;
        if(r >= 1)
        {
            dec_side << '1';
            decimal_part = r - 1;
        }
        else
        {
            dec_side << '0';
            decimal_part = r;
        }
    }

    return std::move(int_side.str() + "." + dec_side.str());
}

int main(int argc, char* argv[])
{
    try
    {
        for(int i = 1; i < argc; i++)
        {
            std::cout << "Decimal " << argv[i] << " in binary is " << dec_to_bin(argv[i], true) << std::endl;
        }
    }
    catch(const std::invalid_argument& e)
    {
        std::cerr << e.what() << " failed to convert string to double." << std::endl;
    }
    return 0;
}
