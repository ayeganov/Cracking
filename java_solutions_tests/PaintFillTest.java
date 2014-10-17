package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chapter_eight.PaintFill;

public class PaintFillTest
{
    @Test
    public void test_left_quadrant()
    {
        int length = 10;
        PaintFill pf = new PaintFill(length, length, 1);
        pf.breakIntoQuadrants(0);
        pf.fill(0, 0, 5);
        for(int i = 0; i < length; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(i < length / 2 && j < length / 2)
                {
                    assertEquals(pf.valueAt(i, j), 5);
                }
                else
                {
                    assertTrue(pf.valueAt(i, j) != 5);
                }
            }
        }
    }
}