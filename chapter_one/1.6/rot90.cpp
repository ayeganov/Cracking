#include <iostream>


const int M = 5;

void print_matrix(int m[][M], int M)
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

void rot90(int m[][M], int M)
{
    int r = M - 1;
    for(int j = 0; j <= M/2; j++)
    {
        for(int i = j; i <= M/2; i++)
        {
            if(i == j && i == M/2) break;
            int a = m[i][j];
            int b = m[r-j][r-(r-i)];
            int c = m[i][r-i];
            int d = m[r-i][r-j];

            std::cout << '\n';
            std::cout << "a=" << a << '\n';
            std::cout << "b=" << b << '\n';
            std::cout << "c=" << c << '\n';
            std::cout << "d=" << d << '\n';
            std::cout << '\n';

            m[i][j] = c;
            m[r-j][r-(r-i)] = a;
            m[i][r-i] = d;
            m[r-i][r-j] = b;

            print_matrix(m, M);
        }
    }
}

// This is taken from the book - I ran out of time solving this one.
void rotate(int matrix[][M], int n)
{
    for (int layer = 0; layer < n / 2; ++layer)
    {
        int first = layer;
        int last = n - 1 - layer;
        for(int i = first; i < last; ++i)
        {
            int offset = i - first;
            int top = matrix[first][i]; // save top
            // left -> top
            matrix[first][i] = matrix[last-offset][first];
            // bottom -> left
            matrix[last-offset][first] = matrix[last][last - offset];
            // right -> bottom
            matrix[last][last - offset] = matrix[i][last];
            // top -> right
            matrix[i][last] = top; // right <- saved top
        }
    }
}


int main(int argc, char* argv[])
{
    int m[M][M] = {{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};

    print_matrix(m, M);
//    rotate(m, M);
    rot90(m, M);
    std::cout << '\n';
    print_matrix(m, M);


    return 0;
}
