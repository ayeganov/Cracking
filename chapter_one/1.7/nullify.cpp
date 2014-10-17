#include <iostream>
#include <set>

void print_matrix(int** m, int M)
{
    for(int j = 0; j < M; j++)
    {
        for(int i = 0; i < M; i++)
        {
            std::cout << m[i][j] << " ";
        }
        std::cout << '\n';
    }
}

void nullify(int** m, int n)
{
    std::set<int> x;
    std::set<int> y;

    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
            if(m[i][j] == 0)
            {
                x.insert(i);
                y.insert(j);
            }
        }
    }

    for(int i : x)
    {
        for(int j = 0; j < n; j++)
        {
            m[i][j] = 0;
        }
    }

    for(int j : y)
    {
        for(int i = 0; i < n; i++)
        {
            m[i][j] = 0;
        }
    }
}


int main(int argc, char* argv[])
{
    const int N = 13;
    auto array = new int*[N];

    for(int i = 0; i < N; i++)
    {
        array[i] = new int[N];
    }

    for(int i = 0; i < N; i++)
    {
        for(int j = 0; j < N; j++)
        {
            array[i][j] = 1;
        }
    }
    array[0][0] = 0;
    array[N-1][N-1] = 0;

    array[N/2][N/2] = 0;

    print_matrix(array, N);

    nullify(array, N);

    std::cout << "After nullification" << '\n';

    print_matrix(array, N);

    for(int i = 0; i < N; i++)
    {
        delete [] array[i];
    }

    delete [] array;

    return 0;
}
