package chapter_four;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;



public class BinaryTree<T extends Comparable<T>>
{
    public static class TreeNode<T extends Comparable<T>>
    {
        private T m_data;
        private TreeNode<T> m_left;
        private TreeNode<T> m_right;
        private TreeNode<T> m_parent;

        /**
         * Constructs a TreeNode with the given data and provided children.
         * @param data - data to be stored in the node.
         * @param left - left child node
         * @param right - right child node
         */
        public TreeNode(T data, TreeNode<T> left, TreeNode<T> right)
        {
            m_data = data;
            m_left = left;
            m_right = right;
        }

        /**
         * Constructs a TreeNode with the given data and no children.
         * @param data - data to be stored in the node.
         */
        public TreeNode(T data)
        {
            this(data, null, null);
        }

        /**
         * Set parent node.
         *
         * @param parent node
         */
        public void setParent(TreeNode<T> parent)
        {
            m_parent = parent;
        }

        /**
         * Access left child node.
         * @return
         */
        public TreeNode<T> getLeft()
        {
            return m_left;
        }

        public TreeNode<T> getRight()
        {
            return m_right;
        }

        public TreeNode<T> getParent()
        {
            return m_parent;
        }

        public T getData()
        {
            return m_data;
        }

        @Override
        public String toString()
        {
            return "" + m_data;
        }
    }

    private TreeNode<T> m_root;
    private int m_size;

    /**
     * Constructs an empty binary tree.
     */
    public BinaryTree()
    {
        m_root = null;
        m_size = 0;
    }

    /**
     * Constructs a binary tree with a single root node.
     * @param data - data to be stored in the root node.
     */
    public BinaryTree(T data)
    {
        m_root = new TreeNode<T>(data);
        m_size = 0;
    }

    /**
     * Searches the tree for the node containing the given data.
     * @param data - data the node must contain
     *
     * @return node containing the data
     */
    public TreeNode<T> findNode(T data)
    {
        return findNode(m_root, data);
    }

    private TreeNode<T> findNode(TreeNode<T> root, T data)
    {
        if(root == null)
            return null;

        if(root.getData().equals(data))
            return root;
        else
        {
            TreeNode<T> left_result = findNode(root.getLeft(), data);
            if(left_result != null)
                return left_result;
            return findNode(root.getRight(), data);
        }
    }

    /**
     * Returns you the root of the tree - be nice to it and don't change stuff.
     * If these were C++ I'd return a const reference, so you wouldn't be
     * able to do anything in the first place, but I digress.
     */
    public TreeNode<T> getRoot()
    {
        return m_root;
    }

    /**
     * Returns number of elements in this tree.
     *
     * @return number of elements
     */
    public int size()
    {
        return m_size;
    }

    /**
     * Add given data to the tree.
     * @param data - new data to be stored in the tree.
     */
    public void add(T data)
    {
        if(data == null)
            throw new NullPointerException("Storing nulls in this tree is not allowed.");
        m_root = add(m_root, data);
    }

    /**
     * Searches for a place in the tree where data should be stored and creates a node for it.
     * @param node - node to check for available spot
     * @param data - new data to be stored in the tree
     */
    private TreeNode<T> add(TreeNode<T> node, T data)
    {
        if(node == null)
        {
            m_size++;
            return new TreeNode<T>(data);
        }

        int comparison = data.compareTo(node.getData());
        if(comparison <= 0)
        {
            node.m_left = add(node.getLeft(), data);
            node.m_left.setParent(node);
        }
        else
        {
            node.m_right = add(node.getRight(), data);
            node.m_right.setParent(node);
        }
        return node;
    }

    /**************************************** PROBLEM 4.1 *******************************************/
    /**
     * Checks if this is tree is balanced.
     * Balanced definition: Difference in depth between any two leaf nodes can't exceed 1.
     */
    public boolean isBalanced()
    {
        return (maxDepth() - minDepth()) <= 1;
    }

    /**
     * Calculates the minimum depth of the tree
     * @return minimum depth of the tree
     */
    public int minDepth()
    {
        return minDepth(m_root, 0);
    }

    private int minDepth(TreeNode<T> root, int depth)
    {
        if(root == null)
            return depth;
        return 1 + Math.min(minDepth(root.getLeft(), depth+1), minDepth(root.getRight(), depth+1));
    }

    /**
     * Calculates the maximum depth of the tree
     * @return maximum depth of the tree
     */
    public int maxDepth()
    {
        return maxDepth(m_root, 0);
    }

    private int maxDepth(TreeNode<T> root, int depth)
    {
        if(root == null)
            return depth;
        return Math.max(maxDepth(root.getLeft(), depth + 1), maxDepth(root.getRight(), depth + 1));
    }

    /*******************************************************************************************************/

    /**************************** PROBLEM 4.3 ***************************************/
    /**
     * Inserts values in the sorted array into the tree with minimum height.
     */
    public void minHeightInsert(T[] array)
    {
        if(array.length == 0)
            return;
        binaryBreakdownInsert(array, 0, array.length-1);
    }

    private void binaryBreakdownInsert(T[] array, int low, int high)
    {
        int mid = low + (high - low) / 2;
        if(high < low)
            return;
        add(array[mid]);
        binaryBreakdownInsert(array, low, mid-1);
        binaryBreakdownInsert(array, mid+1, high);
    }
    /********************************************************************************/

    /****************************** PROBLEM 4.4 *********************************************/
    /**
     * Iterates over the whole binary search tree and returns a
     * map with a linked list mapped to the depth of the tree.
     */
    public Map<Integer, List<TreeNode<T>>> getNodesAtEachLevel()
    {
        Map<Integer, List<TreeNode<T>>> lists = new HashMap<>();
        collectAllNodes(m_root, lists, 0);
        return lists;
    }

    private void collectAllNodes(TreeNode<T> root, Map<Integer, List<TreeNode<T>>> lists, int depth)
    {
        if(root == null)
            return;
        if(lists.containsKey(depth))
        {
            lists.get(depth).add(root);
        }
        else
        {
            List<TreeNode<T>> nodes = new LinkedList<>();
            nodes.add(root);
            lists.put(depth, nodes);
        }

        collectAllNodes(root.getLeft(), lists, depth + 1);
        collectAllNodes(root.getRight(), lists, depth + 1);
    }

    /****************************************************************************************/

    /****************************** PROBLEM 4.5 *********************************************/
    public TreeNode<T> inorderSuccessor(TreeNode<T> node)
    {
        /** Lambda for finding left-most node. */
        Function<TreeNode<T>, TreeNode<T>> leftMost = (n) -> {
            if(n == null)
                return null;
            while(n.getLeft() != null)
            {
                n = n.getLeft();
            }
            return n;
        };

        if(node != null)
        {
            if(node.getRight() != null || node.getParent() == null)
                return leftMost.apply(node.getRight());
            else
            {
                TreeNode<T> p = node.getParent();
                while(p != null && p.getRight() == node)
                {
                    node = p;
                    p = node.getParent();
                }
                return p;
            }
        }
        return null;
    }
    /******************************************************************************************/

    /****************************** PROBLEM 4.6 *********************************************/
    /**
     * Finds first ancestor node of the two given nodes.
     * @param n1 - first node
     * @param n2 - second node
     * @return ancestor node
     */
    public TreeNode<T> commonAncestor(TreeNode<T> n1, TreeNode<T> n2)
    {
        int depth_n1 = findDepth(n1, 0);
        int depth_n2 = findDepth(n2, 0);

        while(depth_n1 != depth_n2)
        {
            if(depth_n1 > depth_n2)
            {
                n1 = n1.getParent();
                depth_n1--;
            }
            else
            {
                n2 = n2.getParent();
                depth_n2--;
            }
        }
        while(n1.getParent() != n2.getParent())
        {
            n1 = n1.getParent();
            n2 = n2.getParent();
        }
        return n1.getParent();
    }

    /**
     * Find the depth in a tree of the given node.
     *
     * @param n - node for which we want to find depth
     * @param depth - starting depth
     * @return depth of node
     */
    private int findDepth(TreeNode<T> n, int depth)
    {
        if(n == null)
            return depth;
        return findDepth(n.getParent(), depth + 1);
    };

    /******************************************************************************************/

    /********************************* PROBLEM 4.7 ********************************************************/
    public boolean isSubtree(BinaryTree<T> other)
    {
        return isSubtree(getRoot(), other.getRoot());
    }

    private boolean isSubtree(TreeNode<T> t1, TreeNode<T> t2)
    {
        // defining null subtree as always being present in any tree
        if(t2 == null)
            return true;
        // ran out of large tree and smaller subtree still not found
        if(t1 == null)
            return false;
        if(t1.getData().equals(t2.getData()) && matchTree(t1, t2))
            return true;
        return isSubtree(t1.getLeft(), t2) || isSubtree(t1.getRight(), t2);
    }

    private boolean matchTree(TreeNode<T> t1, TreeNode<T> t2)
    {
        // Ran out of the smaller tree, haven't failed up to that point,
        // so everything checked before this node must have been matching
        if(t1 != null && t2 == null)
            return true;
        // ran out of larger tree before smaller one - not equal
        if(t1 == null && t2 != null)
            return false;
        // both trees end at this point - match is not ruled out yet
        if(t1 == null && t2 == null)
            return true;
        if(t1.getData().equals(t2.getData()))
            return matchTree(t1.getLeft(), t2.getLeft()) && matchTree(t1.getRight(), t2.getRight());
        // nodes not equal - not matching
        return false;
    }

    /***********************************************************************************************/

    /********************************* PROBLEM 4.8 ********************************************************/
    public void findSumPath(int sum)
    {
        findSumPath(getRoot(), sum);
    }

    private void findSumPath(TreeNode<T> root, int sum)
    {
        if(root == null) return;
        findSumPath(root, sum, new Stack<TreeNode<T>>());
        findSumPath(root.getLeft(), sum);
        findSumPath(root.getRight(), sum);
    }

    private void findSumPath(TreeNode<T> root, int sum, Stack<TreeNode<T>> tail)
    {
        if(root == null) return;
        tail.push(root);
        int diff = sum - (Integer)root.getData();
        if(diff == 0)
        {
            System.out.println(tail);
        }
        else if(diff > 0)
        {
            findSumPath(root.getLeft(), diff, tail);
            findSumPath(root.getRight(), diff, tail);
        }
        tail.pop();
    }

    /*************** BOOK solution ***********/
    public void findSum(int sum)
    {
        findSum(getRoot(), sum, new ArrayList<Integer>(), 0);
    }

    @SuppressWarnings("unchecked")
    private void findSum(TreeNode<T> head, int sum, ArrayList<Integer> buffer, int level)
    {
        if (head == null) return;
        int tmp = sum;
        buffer.add((Integer)head.getData());
        for (int i = level;i >- 1; i--)
        {
            tmp -= buffer.get(i);
            if (tmp == 0)
            {
                print(buffer, i, level);
            }
        }
        ArrayList<Integer> c1 = (ArrayList<Integer>) buffer.clone();
        ArrayList<Integer> c2 = (ArrayList<Integer>) buffer.clone();
        findSum(head.getLeft(), sum, c1, level + 1);
        findSum(head.getRight(), sum, c2, level + 1);
    }

    private void print(ArrayList<Integer> buffer, int level, int i2)
    {
        for (int i = level; i <= i2; i++)
        {
            System.out.print(buffer.get(i) + " ");
        }
        System.out.println();
    }
}
