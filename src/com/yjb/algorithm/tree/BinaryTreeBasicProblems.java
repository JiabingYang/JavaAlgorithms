package com.yjb.algorithm.tree;

import java.util.*;

/**
 * 二叉树常见面试题汇总
 * 主要参考自：
 * https://www.programcreek.com
 * https://www.jianshu.com/p/0190985635eb
 * <p>
 * 1. 求二叉树的深度（树高、深度）
 * 2. 求二叉树的深度最小的叶子节点的深度
 * 3. 求二叉树中节点的个数
 * 4. 求二叉树中叶子节点的个数
 * 5. 求二叉树中第k层节点的个数
 * 6. 判断二叉树是否是平衡二叉树
 * 7. 判断二叉树是否是完全二叉树
 * 8. 两个二叉树是否完全相同
 * 9. 两个二叉树是否互为镜像
 * 10. 翻转二叉树or镜像二叉树
 * 11. 求两个二叉树的最低公共祖先节点
 * 12. 二叉树的前序遍历
 * 13. 二叉树的中序遍历
 * 14. 二叉树的后序遍历
 * 15. 二叉树的层次遍历
 * 16. 二叉树的竖直遍历
 * 17. 节点是否在二叉树中
 * 18. 二叉查找树的搜索区间
 * 19. 二叉查找树中插入节点
 * 20. 二叉树内两个节点的最长距离
 * 21. Unique Binary Search Trees
 * 22. Path Sum
 * 23. Path Sum II
 * 24. 前序遍历和中序遍历构造二叉树
 * 25. 后序遍历和中序遍历构造二叉树
 * 26. Validate Binary Search Tree
 * 27. Invert Binary Tree
 */
public class BinaryTreeBasicProblems {

    /* ---------------- 节点定义 -------------- */
    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }
    }

    /* ---------------- 1. 求二叉树的深度（树高、深度） -------------- */
    private static int getDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getDepth(node.left), getDepth(node.right)) + 1;
    }

    /* ---------------- 2. 求二叉树的深度最小的叶子节点的深度 -------------- */

    /**
     * 递归
     */
    private static int getMinDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getMinDepth(root.left);
        int right = getMinDepth(root.right);
        return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right) + 1;
    }

    /**
     * https://www.programcreek.com/2013/02/leetcode-minimum-depth-of-binary-tree-java/
     * <p>
     * BFS
     */
    private static int getMinDepthBfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> counts = new LinkedList<>();
        queue.add(root);
        counts.add(1);
        while (!queue.isEmpty()) {
            TreeNode v = queue.remove();
            int count = counts.remove();
            if (v.left == null && v.right == null) {
                return count;
            }
            if (v.left != null) {
                queue.add(v.left);
                counts.add(count + 1);
            }
            if (v.right != null) {
                queue.add(v.right);
                counts.add(count + 1);
            }
        }
        return 0;
    }

    /**
     * https://www.programcreek.com/2013/02/leetcode-minimum-depth-of-binary-tree-java/
     * <p>
     * 我的解法
     */
    private static int getMinDepthBfs1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int count = 0;
        while (!queue.isEmpty()) {
            count++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode v = queue.remove();
                if (v.left == null && v.right == null) {
                    return count;
                }
                if (v.left != null) {
                    queue.add(v.left);
                }
                if (v.right != null) {
                    queue.add(v.right);
                }
            }
        }
        return 0;
    }

    /* ---------------- 3. 求二叉树中节点的个数 -------------- */
    private static int getNumOfTreeNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = getNumOfTreeNodes(root.left);
        int right = getNumOfTreeNodes(root.right);
        return left + right + 1;
    }

    /* ---------------- 4. 求二叉树中叶子节点的个数 -------------- */
    private static int getNumOfLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return getNumOfLeaves(root.left) + getNumOfLeaves(root.right);
    }

    /* ---------------- 5. 求二叉树中第k层节点的个数 -------------- */
    private static int getNumOfKthLevelNodes(TreeNode root, int k) {
        if (root == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            return 1;
        }
        int left = getNumOfKthLevelNodes(root.left, k - 1);
        int right = getNumOfKthLevelNodes(root.right, k - 1);
        return left + right;
    }

    /* ---------------- 6. 判断二叉树是否是平衡二叉树 -------------- */

    private static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (Math.abs(getDepth(root.left) - getDepth(root.right)) > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    /* ---------------- 7. 判断二叉树是否是完全二叉树 -------------- */

    /**
     * 1. 当一个结点有右孩子，但是没有左孩子，直接返回false
     * 2. 当一个节点有左孩子无右孩子或没有孩子，那么接下来要遍历的节点必须是叶子结点。（叶子结点左右孩子为空）
     */
    private static boolean isComplete(TreeNode root) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean hasLeftNoRightOrNoChild = false;
        while (!queue.isEmpty()) {
            TreeNode v = queue.remove();
            if (hasLeftNoRightOrNoChild) {
                if (v.left != null || v.right != null) {
                    return false;
                }
            } else {
                if (v.left != null && v.right != null) {
                    queue.add(v.left);
                    queue.add(v.right);
                } else if (v.left != null) {
                    queue.add(v.left);
                    hasLeftNoRightOrNoChild = true;
                } else if (v.right != null) {
                    return false;
                } else {
                    hasLeftNoRightOrNoChild = true;
                }
            }
        }
        return true;
    }

    /* ---------------- 8. 两个二叉树是否完全相同 -------------- */
    private static boolean isSame(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.value != t2.value) {
            return false;
        }
        return isSame(t1.left, t2.left) && isSame(t1.right, t2.right);
    }

    /* ---------------- 9. 两个二叉树是否互为镜像 -------------- */
    private static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.value != t2.value) {
            return false;
        }
        return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    /* ---------------- 10. 翻转二叉树or镜像二叉树 -------------- */
    private static TreeNode mirror(TreeNode root) {
        if (root == null) {
            return null;
        }
        root.left = mirror(root.right);
        root.right = mirror(root.left);
        return root;
    }

    /* ---------------- 11. 求两个二叉树的最低公共祖先节点 -------------- */

    /**
     * https://www.programcreek.com/2014/07/leetcode-lowest-common-ancestor-of-a-binary-tree-java/
     */
    private static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode l = lowestCommonAncestor(root.left, p, q);
        TreeNode r = lowestCommonAncestor(root.right, p, q);
        if (l != null && r != null) {
            return root;
        }
        if (l == null && r == null) {
            return null;
        }
        return l == null ? r : l;
    }

    /* ---------------- 12. 二叉树的前序遍历 -------------- */

    /**
     * 递归
     */
    private static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private static void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.value);
        preorderTraversal(root.left, result);
        preorderTraversal(root.right, result);
    }

    /**
     * https://www.programcreek.com/2012/12/leetcode-solution-for-binary-tree-preorder-traversal-in-java/
     * <p>
     * 非递归
     */
    private static List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            result.add(node.value);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }

    /* ---------------- 13. 二叉树的中序遍历 -------------- */

    /**
     * 递归
     */
    private static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private static void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left, result);
        result.add(root.value);
        inorderTraversal(root.right, result);
    }

    /**
     * https://www.programcreek.com/2012/12/leetcode-solution-of-binary-tree-inorder-traversal-in-java/
     * <p>
     * 非递归
     */
    private static List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.left != null) {
                stack.push(node.left);
                node.left = null;
            } else {
                result.add(node.value);
                stack.pop();
                if (node.right != null) {
                    stack.push(node.right);
                }
            }
        }
        return result;
    }

    /* ---------------- 14. 二叉树的后序遍历 -------------- */

    /**
     * 递归
     */
    private static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result;
    }

    private static void postorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left, result);
        postorderTraversal(root.right, result);
        result.add(root.value);
    }

    /**
     * https://www.programcreek.com/2012/12/leetcode-solution-of-iterative-binary-tree-postorder-traversal-in-java/
     * <p>
     * 非递归
     */
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.left == null && node.right == null) {
                result.add(stack.pop().value);
            } else {
                if (node.right != null) {
                    stack.push(node.right);
                    node.right = null;
                }
                if (node.left != null) {
                    stack.push(node.left);
                    node.left = null;
                }
            }
        }
        return result;
    }

    /* ---------------- 15. 二叉树的层次遍历 -------------- */
    private static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.value);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    /* ---------------- 16. 二叉树的竖直遍历 -------------- */
    private static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        // level and list
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        LinkedList<Integer> level = new LinkedList<>();
        queue.offer(root);
        level.offer(0);
        int minLevel = 0;
        int maxLevel = 0;
        while (!queue.isEmpty()) {
            TreeNode v = queue.poll();
            int l = level.poll();
            //track min and max levels
            minLevel = Math.min(minLevel, l);
            maxLevel = Math.max(maxLevel, l);
            if (map.containsKey(l)) {
                map.get(l).add(v.value);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(v.value);
                map.put(l, list);
            }
            if (v.left != null) {
                queue.offer(v.left);
                level.offer(l - 1);
            }
            if (v.right != null) {
                queue.offer(v.right);
                level.offer(l + 1);
            }
        }
        for (int i = minLevel; i <= maxLevel; i++) {
            if (map.containsKey(i)) {
                result.add(map.get(i));
            }
        }
        return result;
    }

    /* ---------------- 17. 节点是否在二叉树中 -------------- */
    private static boolean containsNode(TreeNode root, TreeNode node) {
        if (root == null || node == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        return containsNode(root.left, node) || containsNode(root.right, node);
    }

    /* ---------------- 18. 二叉查找树的搜索区间 -------------- */

    /**
     * 给定两个值 k1 和 k2（k1 < k2）和一个二叉查找树的根节点。
     * 找到树中所有值在 k1 到 k2 范围内的节点。
     * 即打印所有x (k1 <= x <= k2) 其中 x 是二叉查找树的中的节点值。
     * 返回所有升序的节点值。
     */
    private static List<Integer> searchRange(TreeNode root, int k1, int k2) {
        List<Integer> inorder = new ArrayList<>();
        if (root == null) {
            return inorder;
        }
        inorder.addAll(searchRange(root.left, k1, k2));
        if (root.value >= k1 && root.value <= k2)
            inorder.add(root.value);
        inorder.addAll(searchRange(root.right, k1, k2));
        return inorder;
    }

    /* ---------------- 19. 二叉查找树中插入节点 -------------- */
    private static TreeNode insert(TreeNode root, TreeNode node) {
        if (root == null) {
            return node;
        }
        if (node.value < root.value) {
            root.left = insert(root.left, node);
        } else if (node.value > root.value) {
            root.right = insert(root.right, node);
        }
        return root;
    }

    /* ---------------- 20. 二叉树内两个节点的最长距离 -------------- */

    /**
     * 定义“距离”为两个节点之间边的个数
     */
    private static int getMaxDistance(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lh = 0, rh = 0;
        if (root.left != null) {
            lh = getDepth(root.left); // 左子树最远的叶子节点与root的距离
        }
        if (root.right != null) {
            rh = getDepth(root.right); // 右子树最远的叶子节点与root的距离
        }
        return Math.max(Math.max(getMaxDistance(root.left), getMaxDistance(root.right)), lh + rh);
    }

    /* ---------------- 21. Unique Binary Search Trees -------------- */

    /**
     * https://www.programcreek.com/2014/05/leetcode-unique-binary-search-trees-java/
     * <p>
     * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
     * <p>
     * Analysis
     * <p>
     * Let count[i] be the number of unique binary search trees for i. The number of trees are determined by the number of subtrees which have different root node. For example,
     * <p>
     * i=0, count[0]=1 //empty tree
     * <p>
     * i=1, count[1]=1 //one tree
     * <p>
     * i=2, count[2]=count[0]*count[1] // 0 is root
     * + count[1]*count[0] // 1 is root
     * <p>
     * i=3, count[3]=count[0]*count[2] // 1 is root
     * + count[1]*count[1] // 2 is root
     * + count[2]*count[0] // 3 is root
     * <p>
     * i=4, count[4]=count[0]*count[3] // 1 is root
     * + count[1]*count[2] // 2 is root
     * + count[2]*count[1] // 3 is root
     * + count[3]*count[0] // 4 is root
     * ..
     * ..
     * ..
     * <p>
     * i=n, count[n] = sum(count[0..k]*count[k+1...n]) 0 <= k < n-1
     * Use dynamic programming to solve the problem.
     */
    private static int numTrees(int n) {
        int[] counts = new int[n + 2];
        counts[0] = 1;
        counts[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                counts[i] += counts[j] * counts[i - j - 1];
            }
        }
        return counts[n];
    }

    /* ---------------- 22. Path Sum -------------- */

    /**
     * https://www.programcreek.com/2013/01/leetcode-path-sum/
     * <p>
     * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all
     * the values along the path equals the given sum.
     * <p>
     * 递归
     */
    private static boolean hasPathSum(TreeNode root, int sum) {
        if (root == null)
            return false;
        if (root.value == sum && (root.left == null && root.right == null))
            return true;
        return hasPathSum(root.left, sum - root.value)
                || hasPathSum(root.right, sum - root.value);
    }

    /**
     * https://www.programcreek.com/2013/01/leetcode-path-sum/
     * <p>
     * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all
     * the values along the path equals the given sum.
     * <p>
     * 非递归
     */
    private static boolean hasPathSum1(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> pathSums = new LinkedList<>();
        queue.add(root);
        pathSums.add(root.value);
        while (!queue.isEmpty()) {
            TreeNode v = queue.poll();
            int pathSum = pathSums.poll();
            if (v.left == null && v.right == null && pathSum == sum) {
                return true;
            }
            if (v.left != null) {
                queue.add(v.left);
                pathSums.add(pathSum + v.left.value);
            }
            if (v.right != null) {
                queue.add(v.right);
                pathSums.add(pathSum + v.right.value);
            }
        }
        return false;
    }

    /* ---------------- 23. Path Sum II -------------- */

    /**
     * https://www.programcreek.com/2014/05/leetcode-path-sum-ii-java/
     * <p>
     * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
     */
    private static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        List<Integer> path = new ArrayList<>();
        path.add(root.value);
        dfs(root, sum - root.value, result, path);
        return result;
    }

    private static void dfs(TreeNode t, int sum, List<List<Integer>> result, List<Integer> path) {
        if (t.left == null && t.right == null && sum == 0) {
            result.add(new ArrayList<>(path));
        }

        //search path of left node
        if (t.left != null) {
            path.add(t.left.value);
            dfs(t.left, sum - t.left.value, result, path);
            path.remove(path.size() - 1);
        }

        //search path of right node
        if (t.right != null) {
            path.add(t.right.value);
            dfs(t.right, sum - t.right.value, result, path);
            path.remove(path.size() - 1);
        }
    }

    /* ---------------- 24. 前序遍历和中序遍历构造二叉树 -------------- */
    private static TreeNode buildTree(int[] preOrder, int[] inOrder) {
        return construct(preOrder, inOrder, 0, preOrder.length - 1, 0, inOrder.length);
    }

    private static TreeNode construct(int[] preOrder, int[] inOrder, int preStart,
                                      int preEnd, int inStart, int inEnd) {
        if (preOrder == null || inOrder == null || preOrder.length == 0 ||
                inOrder.length == 0 || preStart > preEnd || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[preStart]);
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == root.value) {
                root.left = construct(preOrder, inOrder, preStart + 1, preStart + i - inStart, inStart, i - 1);
                root.right = construct(preOrder, inOrder, preStart + i - inStart + 1, preEnd, i + 1, inEnd);
                return root;
            }
        }
        return root;
    }

    /* ---------------- 25. 后序遍历和中序遍历构造二叉树 -------------- */
    private static TreeNode buildTree1(int[] postOrder, int[] inOrder) {
        return construct(postOrder, inOrder, 0, postOrder.length - 1, 0, inOrder.length);
    }

    private static TreeNode construct1(int[] postOrder, int[] inOrder, int postStart,
                                       int postEnd, int inStart, int inEnd) {
        if (postOrder == null || inOrder == null || postOrder.length == 0 ||
                inOrder.length == 0 || postStart > postEnd || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(postOrder[postEnd]);
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == root.value) {
                root.left = construct1(postOrder, inOrder, postStart, postStart + i - inStart - 1, inStart, i - 1);
                root.right = construct1(postOrder, inOrder, postStart + i - inStart, postEnd - 1, i + 1, inEnd);
                return root;
            }
        }
        return root;
    }

    /* ---------------- 26. Validate Binary Search Tree -------------- */

    /**
     * https://www.programcreek.com/2012/12/leetcode-validate-binary-search-tree-java/
     * <p>
     * Assume a BST is defined as follows:
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * <p>
     * All values on the left sub tree must be less than root,
     * and all values on the right sub tree must be greater than root.
     * So we just check the boundaries for each node.
     * <p>
     * 递归
     */
    private static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    private static boolean isValidBST(TreeNode p, double min, double max) {
        if (p == null)
            return true;
        if (p.value <= min || p.value >= max)
            return false;
        return isValidBST(p.left, min, p.value) && isValidBST(p.right, p.value, max);
    }

    /**
     * https://www.programcreek.com/2012/12/leetcode-validate-binary-search-tree-java/
     * <p>
     * 非递归
     */
    private static boolean isValidBST1(TreeNode root) {
        if (root == null)
            return true;
        Queue<BNode> queue = new LinkedList<>();
        queue.add(new BNode(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        while (!queue.isEmpty()) {
            BNode v = queue.poll();
            if (v.node.value <= v.left || v.node.value >= v.right) {
                return false;
            }
            if (v.node.left != null) {
                queue.offer(new BNode(v.node.left, v.left, v.node.value));
            }
            if (v.node.right != null) {
                queue.offer(new BNode(v.node.right, v.node.value, v.right));
            }
        }
        return true;
    }

    //define a BNode class with TreeNode and it's boundaries
    private static class BNode {
        TreeNode node;
        double left;
        double right;

        BNode(TreeNode node, double left, double right) {
            this.node = node;
            this.left = left;
            this.right = right;
        }
    }

    /* ---------------- 27. Invert Binary Tree -------------- */

    /**
     * https://www.programcreek.com/2014/06/leetcode-invert-binary-tree-java/
     * <p>
     * 递归
     */
    private static TreeNode invertTree(TreeNode root) {
        if (root != null) {
            invertTreeHelper(root);
        }
        return root;
    }

    private static void invertTreeHelper(TreeNode root) {
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        if (root.left != null) {
            invertTreeHelper(root.left);
        }
        if (root.right != null) {
            invertTreeHelper(root.right);
        }
    }

    /**
     * https://www.programcreek.com/2014/06/leetcode-invert-binary-tree-java/
     * <p>
     * 非递归
     */
    private static TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode v = queue.poll();
            if (v.left != null) {
                queue.offer(v.left);
            }
            if (v.right != null) {
                queue.offer(v.right);
            }
            TreeNode temp = v.left;
            v.left = v.right;
            v.right = temp;
        }
        return root;
    }
}
