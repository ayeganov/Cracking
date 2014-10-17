package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chapter_four.DFS;

public class PathBetweenNodesTest
{

    @Test
    public void test_start_equal_goal()
    {
        DFS.GraphNode<Integer> start = new DFS.GraphNode<Integer>(1);
        DFS.GraphNode<Integer> goal = start;

        assertTrue(DFS.pathExists(start, goal));
    }

    @Test
    public void test_goal_is_left_child()
    {
        DFS.GraphNode<Integer> goal = new DFS.GraphNode<Integer>(2);
        DFS.GraphNode<Integer> start = new DFS.GraphNode<Integer>(1, goal, null);
        assertTrue(DFS.pathExists(start, goal));
    }

    @Test
    public void test_goal_is_right_child()
    {
        DFS.GraphNode<Integer> goal = new DFS.GraphNode<Integer>(2);
        DFS.GraphNode<Integer> start = new DFS.GraphNode<Integer>(1, null, goal);
        assertTrue(DFS.pathExists(start, goal));
    }

    @Test
    public void test_goal_not_present_simple()
    {
        DFS.GraphNode<Integer> start = new DFS.GraphNode<Integer>(1);
        DFS.GraphNode<Integer> goal = new DFS.GraphNode<Integer>(2);
        assertFalse(DFS.pathExists(start, goal));
    }

    @Test
    public void test_goal_not_present_complex()
    {
        DFS.GraphNode<String> goal = new DFS.GraphNode<>("G");
        DFS.GraphNode<String> a = new DFS.GraphNode<>("A");
        DFS.GraphNode<String> b = new DFS.GraphNode<>("B");
        DFS.GraphNode<String> c = new DFS.GraphNode<>("C");
        DFS.GraphNode<String> d = new DFS.GraphNode<>("D");
        DFS.GraphNode<String> e = new DFS.GraphNode<>("E");
        DFS.GraphNode<String> f = new DFS.GraphNode<>("F");
        DFS.GraphNode<String> h = new DFS.GraphNode<>("H");

        a.setLeft(b);
        a.setRight(c);
        b.setLeft(d);
        b.setRight(e);
        c.setLeft(f);
        c.setRight(h);
        e.setRight(f);

        assertFalse(DFS.pathExists(a, goal));
    }

    @Test
    public void test_goal_present_complex()
    {
        DFS.GraphNode<String> goal = new DFS.GraphNode<>("G");
        DFS.GraphNode<String> a = new DFS.GraphNode<>("A");
        DFS.GraphNode<String> b = new DFS.GraphNode<>("B");
        DFS.GraphNode<String> c = new DFS.GraphNode<>("C");
        DFS.GraphNode<String> d = new DFS.GraphNode<>("D");
        DFS.GraphNode<String> e = new DFS.GraphNode<>("E");
        DFS.GraphNode<String> f = new DFS.GraphNode<>("F");
        DFS.GraphNode<String> h = new DFS.GraphNode<>("H");

        a.setLeft(b);
        a.setRight(c);
        b.setLeft(d);
        b.setRight(e);
        c.setLeft(f);
        c.setRight(h);
        e.setLeft(goal);
        e.setRight(f);
        h.setLeft(goal);

        assertTrue(DFS.pathExists(a, goal));
    }
}
