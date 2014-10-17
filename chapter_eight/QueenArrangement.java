package chapter_eight;

import java.util.List;

public class QueenArrangement
{
    public static class Position
    {
        private int m_x, m_y;

        public Position(int x, int y)
        {
            m_x = x;
            m_y = y;
        }

        @Override
        public String toString()
        {
            return "[" + m_x + ", " + m_y + "]";
        }

        public int x()
        {
            return m_x;
        }

        public int y()
        {
            return m_y;
        }
    }
    public static boolean arrangeQueens(int N, Position start, List<Position> pos)
    {
        // reached the bottom of the board - done.
        if(start.y() >= N)
            return true;

        boolean found = false;
        for(int i = 0; i < N; i++)
        {
            Position newPos = new Position(i, start.y());
            if(isValid(N, newPos, pos))
            {
                pos.add(newPos);
                if(!(found = arrangeQueens(N, new Position(0, start.y() + 1), pos)))
                {
                    pos.remove(newPos);
                }
                else
                {
                    break;
                }
            }
        }
        return found;
    }

    private static boolean isValid(int N, Position start, List<Position> pos)
    {
        for(Position p : pos)
        {
            int x1 = p.x();
            int y1 = p.y();

            int x2 = start.x();
            int y2 = start.y();
            if(x1 == x2 || y1 == y2 || Math.abs(y2-y1) == Math.abs(x2-x1)
                    || x2 >= N || y2 >= N)
                return false;
        }
        return true;
    }
}
