#include <functional>
#include <iostream>
#include <string>

long double nthfib(int n)
{
    if(n <= 0) return 0;
    if(n <= 2) return 1;
    if(n == 3) return 2;
    // Subtract 1 since we cover those cases above and start the sequence with
    // the computation of the 3rd number
    n -= 1;
    std::function<long double(int, long double, long double)> fib;
    fib = [&fib] (int n, long double current, long double prev) {
        if (!n) return current;
        return fib(n-1, current + prev, current);
    };
    return fib(n, 1, 0);
}

int main(int argc, char* argv[])
{

    for(int i = 1; i < argc; i++)
    {
        int n = std::stoi(argv[i]);
        std::cout << n << "th fibonacci number is " << nthfib(n) << std::endl;
    }
    return 0;
}
