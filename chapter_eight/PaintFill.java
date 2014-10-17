package chapter_eight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaintFill
{
    private int[][] m_matrix;
    public PaintFill(int x, int y, int fill)
    {
        m_matrix = new int[x][y];
        for(int[] a : m_matrix)
        {
            Arrays.fill(a, fill);
        }
    }

    public void printMatrix()
    {
        for(int[] a : m_matrix)
        {
            for(int v : a)
            {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    public void breakIntoQuadrants(int value)
    {
        int row = m_matrix.length / 2;
        for(int i = 0; i < m_matrix[0].length; i++)
        {
            m_matrix[row][i] = value;
        }

        int col = m_matrix[0].length / 2;
        for(int i = 0; i < m_matrix.length; i++)
        {
            m_matrix[i][col] = value;
        }
    }

    private List<int[]> getNeighbors(int x, int y)
    {
        List<int[]> neighbors = new ArrayList<>();
        for(int i = -1; i <= 1; i++)
        {
            for(int j = -1; j <= 1; j++)
            {
                if(i == 0 && j == 0)
                {
                    continue;
                }
                int[] neighbor = new int[2];
                neighbor[0] = x + i;
                neighbor[1] = y + j;
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    public void fill(int x, int y, int value)
    {
        if(x < 0 || x >= m_matrix.length || y < 0 || y >= m_matrix[0].length)
            throw new IllegalArgumentException("Coordinates must be positive values within the matrix boundaries.");
        fill(x, y, m_matrix[x][y], value);
    }

    private void fill(int x, int y, int old_value, int value)
    {
        // boundary check AND value check
        if(x < 0 || x >= m_matrix.length || y < 0 || y >= m_matrix[0].length || m_matrix[x][y] != old_value)
            return;

        m_matrix[x][y] = value;
        for(int[] neighbor : getNeighbors(x, y))
        {
            fill(neighbor[0], neighbor[1], old_value, value);
        }
    }

    public int valueAt(int x, int y)
    {
        return m_matrix[x][y];
    }
}