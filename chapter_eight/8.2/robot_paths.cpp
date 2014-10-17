#include <iostream>
#include <vector>

class Tuple
{
public:
    Tuple(int x, int y)
    {
        m_x = x;
        m_y = y;
    }

    int x() const
    {
        return m_x;
    }

    int y() const
    {
        return m_y;
    }

    bool operator==(const Tuple& other) const
    {
        return m_x == other.m_x && m_y == other.m_y;
    }

private:
    int m_x;
    int m_y;
};

std::ostream& operator<<(std::ostream &strm, const Tuple& t)
{
  return strm << "Tuple(" << t.x() << ", " << t.y() << ")";
}


class Grid
{
public:
    Grid(int fill_value, int X, int Y)
    : m_height(X),
      m_width(Y)
    {
        m_grid = new int[X * Y];
        for(int i = 0; i < X*Y; i++)
        {
            m_grid[i] = fill_value;
        }
    }

    ~Grid()
    {
        delete [] m_grid;
    }

    int& at(int x, int y)
    {
        int idx = x * m_width + y;
        return m_grid[idx];
    }

    int height() const
    {
        return m_height;
    }

    int width() const
    {
        return m_width;
    }

private:
    int* m_grid;
    int m_height;
    int m_width;
};

/**
 * Calculate number of paths in the given grid.
 *
 * @param g - grid, where values >0 signify where one can step
 * @param start - starting coordinate
 * @param end - destination of the walk
 * @param paths - counter of paths that lead to the end
 */
void numberPaths(Grid& g, Tuple& start, Tuple& end, int& paths)
{
    if(start.x() >= g.height() || start.y() >= g.width() || !g.at(start.x(), start.y()))
    {
        return;
    }

    if(start == end)
    {
        paths++;
        return;
    }
    Tuple right(start.x() + 1, start.y());
    Tuple down(start.x(), start.y() + 1);
    numberPaths(g, right, end, paths);
    numberPaths(g, down, end, paths);
}

int getNumPaths(Grid& g, Tuple& start, Tuple& end)
{
    int paths = 0;
    numberPaths(g, start, end, paths);
    return paths;
}

int main(int argc, char* argv[])
{
    for(int i = 1; i < argc; i += 2)
    {
        int x = std::stoi(argv[i]) + 1;
        int y = std::stoi(argv[i + 1]) + 1;
        Grid g(1, x, y);

        Tuple start(0, 0);
        Tuple finish(x-1, y-1);
        std::cout << "Number of paths in (" << x - 1 << ", " << y - 1 << ") is " << getNumPaths(g, start, finish) << std::endl;
    }
    return 0;
}
