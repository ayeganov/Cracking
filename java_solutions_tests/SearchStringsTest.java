package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chapter_nine.SearchStrings;

public class SearchStringsTest
{

    @Test
    public void test()
    {
        String[] strings = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};
        assertEquals(0, SearchStrings.findString(strings, "at"));
        assertEquals(4, SearchStrings.findString(strings, "ball"));
        assertEquals(7, SearchStrings.findString(strings, "car"));
        assertEquals(10, SearchStrings.findString(strings, "dad"));
        assertEquals(-1, SearchStrings.findString(strings, "ballcar"));
        assertEquals(1, SearchStrings.findString(strings, ""));
    }
}
