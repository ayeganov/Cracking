package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chapter_eight.GiveChange;

public class GiveChangeTest
{

    @Test
    public void test_25_cents()
    {
        int change = GiveChange.calcChange(25);
        assertEquals(13, change);
    }

    @Test
    public void test_25_cents_book()
    {
        int change = GiveChange.makeChange(25, 25);
        assertEquals(13, change);
    }

    @Test
    public void test_25_cents_iterative()
    {
        int change = GiveChange.getChange(25);
        assertEquals(13, change);
    }
}
