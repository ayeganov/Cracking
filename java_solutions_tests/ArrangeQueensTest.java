package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chapter_eight.QueenArrangement;
import chapter_eight.QueenArrangement.Position;

public class ArrangeQueensTest
{

    @Test
    public void test_8x8()
    {
        int N = 15;
        List<Position> queens = new ArrayList<>();
        QueenArrangement.arrangeQueens(N, new Position(0, 0), queens);
        System.out.println(queens);

        char[][] board = new char[N][N];

        for(char[] a : board)
        {
            Arrays.fill(a, '_');
        }
        for(Position p : queens)
        {
            board[p.x()][p.y()] = 'Q';
        }

        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
