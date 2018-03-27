package com.yjb.algorithm.linkedlist;

import java.util.HashSet;
import java.util.Stack;

/**
 * 链表常见面试题汇总
 * 主要参考自：
 * https://ferguschen.github.io/2016/08/25/%E5%8D%95%E9%93%BE%E8%A1%A8%E7%AE%97%E6%B3%95%E7%9A%84java%E5%AE%9E%E7%8E%B0/
 * https://www.cnblogs.com/smyhvae/p/4782595.html
 * https://www.jianshu.com/p/a64d1ef95980
 * <p>
 * 1. 单链表倒序输出
 * 2. 查找单链表倒数第k个元素
 * 3. 获取单链表的中间元素
 * 4. 两个有序单链表合并(剑指offer：No25MergeSortedLists)
 * 5. 判断两个单链表是否相交（无环）
 * 6. 获取两个单链表相交的节点（无环）
 * 7. 判断单链表是否有环
 * 8. 求环长
 * 9. 求环起点(剑指offer：No23EntryNodeInListLoop)
 * 10. 删除单链表中指定节点(剑指offer: No18aDeleteNodeInList)
 * 11. 反转链表(剑指offer: No24ReverseList)
 * 12. 单链表归并排序
 * 13. 反序链表求和(Leetcode: 2. Add Two Numbers)
 * 14. 删除链表中重复的元素(剑指offer: No18bDeleteDuplicatedNode)
 * 15. 删除链表中重复的元素(重复元素只保留一个)
 * 16. 链表倒数第k个节点(考虑异常情况，剑指offer: No22KthNodeFromEnd)
 * 17. 删除链表倒数第n个节点(不考虑异常情况，LeetCode: 19. Remove Nth Node From End of List)
 * 18. 删除链表倒数第n个节点(考虑异常情况)
 */
public class LinkedListBasicProblems {

    /* ---------------- 节点定义 -------------- */
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /* ---------------- 1. 单链表倒序输出 -------------- */

    /**
     * 迭代
     */
    private static void printListReverse1(Node head) {
        Stack<Integer> stack = new Stack<>();
        while (head != null) {
            stack.push(head.data);
            head = head.next;
        }
        while (!stack.empty()) {
            System.out.print(stack.pop() + ", ");
        }
    }

    /**
     * 递归
     */
    private static void printListReverse2(Node head) {
        if (head != null) {
            printListReverse2(head.next);
            System.out.print(head.data + ", ");
        }
    }

    /* ---------------- 2. 查找单链表倒数第k个元素 -------------- */

    /**
     * 第1次遍历，得到链表长度 length。然后第2次遍历，找到第length-k个元素。效率较低。
     */
    private static Node getKthNode1(Node head, int k) {
        if (head == null || k == 0) {
            return null;
        }
        int len = 0;
        Node p = head;
        while (p != null) {
            len++;
            p = p.next;
        }
        if (k > len) { // 异常处理
            throw new RuntimeException("exceed length of linked list");
        }
        p = head;
        int count = len - k;
        while (count > 0) {
            p = p.next;
            count--;
        }
        return p;
    }

    /**
     * pFront先指向第k个元素，pBack指向链表的head
     * 两个指针同时移动，当pFront到null时，pBack指向倒数第k个元素。
     */
    private static Node getReKthNode2(Node head, int k) {
        if (head == null || k == 0) {
            return null;
        }

        Node pFront = head;
        while (k > 1) {
            pFront = pFront.next;
            if (pFront == null) { // 异常处理
                throw new RuntimeException("exceed length of linked list");
            }
            k--;
        }

        Node pBack = head;
        while (pFront != null) {
            pFront = pFront.next;
            pBack = pBack.next;
        }
        return pBack;
    }

    /* ---------------- 3. 获取单链表的中间元素 -------------- */

    /**
     * pFront一次走2步，pBack一次走1步。直到pFront走到最后一个节点。
     */
    private static Node getMiddleNode(Node head) {
        Node pFront = head;
        Node pBack = head;
        while (pFront != null && pFront.next != null) {
            pFront = pFront.next.next;
            pBack = pBack.next;
        }
        return pBack;
    }

    /* ---------------- 4. 两个有序单链表合并(剑指offer：No25MergeSortedLists) -------------- */

    /**
     * 归并
     */
    private static Node mergeSortedLists1(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node dummy = new Node(0);

        Node p1 = head1;
        Node p2 = head2;
        Node p = dummy;

        while (p1 != null && p2 != null) {
            if (p1.data < p2.data) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }

        if (p1 != null) {
            p.next = p1;
        }
        if (p2 != null) {
            p.next = p2;
        }

        return dummy.next;
    }

    /**
     * 递归
     */
    private static Node mergeSortedLists2(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node head;
        if (head1.data < head2.data) {
            head = head1;
            head.next = mergeSortedLists2(head1.next, head2);
        } else {
            head = head2;
            head.next = mergeSortedLists2(head1, head2.next);
        }
        return head;
    }

    /* ---------------- 5. 判断两个单链表是否相交（无环） -------------- */

    /**
     * 判断两个链表的最后一个节点是否相同
     */
    private static boolean isLinkedListsIntersect(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return false;
        }
        while (head1.next != null) {
            head1 = head1.next;
        }
        while (head2.next != null) {
            head2 = head2.next;
        }
        return head1 == head2;
    }

    /* ---------------- 6. 获取两个单链表相交的节点（无环） -------------- */

    /**
     * pBack指向较短链表的首部，pFront指向较长链表的lenLong- lenShort位置
     * 两个指针同时移动，直到找到相同节点。
     */
    public static Node getCommonNode1(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        int len1 = 1;
        Node p1 = head1;
        while (p1.next != null) {
            len1++;
            p1 = p1.next;
        }

        int len2 = 1;
        Node p2 = head1;
        while (p2.next != null) {
            len2++;
            p2 = p2.next;
        }

        if (p1 != p2) {
            return null; //不相交
        }

        Node pFront;
        Node pBack;
        int lenDiff;

        //确定head1和heda2哪个比较长，pBack指向短的链表表头
        if (len1 < len2) {
            pFront = head2;
            pBack = head1;
            lenDiff = len2 - len1;
        } else {
            pFront = head1;
            pBack = head2;
            lenDiff = len1 - len2;
        }

        //让pFront指向lenLong-lenShort的位置
        for (int i = 0; i < lenDiff; i++) {
            pFront = pFront.next;
        }

        //两个指针同时向链表尾部移动，直到找到交点。
        while (pFront != pBack) {
            pFront = pFront.next;
            pBack = pBack.next;
        }

        return pFront;
    }


    /**
     * https://github.com/CyC2018/InnterviewNotes/blob/master/notes/剑指%20offer%20题解.md
     * <p>
     * 设 A 的长度为 a + c，B 的长度为 b + c，其中 c 为尾部公共部分长度，可知 a + c + b = b + c + a。
     * <p>
     * 当访问 A 链表的指针访问到链表尾部时，令它从链表 B 的头部开始访问链表 B；
     * 同样地，当访问 B 链表的指针访问到链表尾部时，令它从链表 A 的头部开始访问链表 A。
     * 这样就能控制访问 A 和 B 两个链表的指针能同时访问到交点。
     */
    private static Node getCommonNode2(Node head1, Node head2) {
        Node p1 = head1, p2 = head2;
        while (p1 != p2) {
            p1 = (p1 == null) ? head2 : p1.next;
            p2 = (p2 == null) ? head1 : p2.next;
        }
        return p1;
    }

    /* ---------------- 7. 判断单链表是否有环 -------------- */

    /**
     * 快指针走到null，说明无环
     * fast==slow相遇，说明有环
     */
    private static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /* ---------------- 8. 求环长 -------------- */

    /**
     * https://www.cnblogs.com/hiddenfox/p/3408931.html
     * <p>
     * 第一次相遇后，让slow,fast继续走，记录到下次相遇时循环了几次。
     * 因为当fast第二次到达相遇点时，fast走了一圈，slow走了半圈，
     * 而当fast第三次到达相遇点时，fast走了两圈，slow走了一圈，正好还在相遇点相遇。
     */
    private static int getCycleLen1(Node head) {
        Node slow = head;
        Node fast = head;
        int count = 0;
        int meetCount = 0;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (meetCount == 1) {
                count++;
            }
            if (slow == fast) {
                meetCount++;
            }
            if (meetCount == 2) {
                return count;
            }
        }
        return 0;
    }

    /**
     * https://www.cnblogs.com/hiddenfox/p/3408931.html
     * <p>
     * 第一次相遇后，让fast停着不走了，slow继续走，记录到下次相遇时循环了几次。
     */
    private static int getCycleLen2(Node head) {
        Node slow = head;
        Node fast = head;
        boolean met = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                met = true;
                break;
            }
        }
        int count = 0;
        if (met) {
            do {
                slow = slow.next;
                count++;
            } while (slow != fast);
        }
        return count;
    }

    /* ---------------- 9. 求环起点 -------------- */

    /**
     * https://www.cnblogs.com/hiddenfox/p/3408931.html
     * <p>
     * 上面链接中关于这道题的解释是错的，应该要推公式的。
     * <p>
     * 当两个节点相遇的时候，将slow再重新指向head，slow和fast一起以slow的速度往后移动，
     * 当再次相遇的时候，该节点就是环的起点。
     */
    private static Node detectCycle1(Node head) {
        Node slow = head;
        Node fast = head;

        while (true) {
            if (fast == null || fast.next == null) {
                return null; //遇到null了，说明不存在环
            }
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                break; //第一次相遇
            }
        }

        slow = head; //slow从头开始走，
        while (slow != fast) { //二者相遇在环的起始节点，则退出
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * HashSet + 一次遍历
     * <p>
     * 空间开销大
     */
    private static Node detectCycle2(Node head) {
        if (head == null) {
            return null;
        }
        HashSet<Node> set = new HashSet<>();
        Node p = head;
        while (p.next != null) {
            set.add(p);
            p = p.next;
            if (set.contains(p)) {
                return head;
            }
        }
        return null;
    }

    /* ---------------- 10. 删除单链表中指定节点(剑指offer: No18aDeleteNodeInList) -------------- */

    /**
     * 将toBeDeleted的下一个节点复制到toBeDeleted，然后删除toBeDeleted的下一个节点。
     * 如果是删除链表最后一个元素，仍需找toBeDeleted的上一个节点。
     * <p>
     * Note:
     * 输入的待删除节点必须是待链表中的节点，否则会出错
     */
    private static Node deleteNode(Node head, Node toBeDeleted) {
        if (head == null || toBeDeleted == null) {
            return head;
        }
        if (toBeDeleted == head) {
            return head.next;
        }
        Node next = toBeDeleted.next;
        if (next != null) {
            toBeDeleted.data = next.data;
            toBeDeleted.next = next.next;
            return head;
        }
        Node p = head;
        while (p.next != toBeDeleted) {
            p = p.next;
        }
        p.next = null;
        return head;
    }

    /* ---------------- 11. 反转链表(剑指offer: No24ReverseList) -------------- */

    /**
     * 非递归
     */
    private static Node reverse1(Node head) {
        Node newHead = null;
        Node p = head;
        while (p != null) {
            Node next = p.next;
            p.next = newHead;
            newHead = p;
            p = next;
        }
        return newHead;
    }

    /**
     * 递归
     */
    private static Node reverse2(Node head) {
        if (head == null || head.next == null) { // 如果一开始输入的head不为null的话head == null不用检查
            return head;
        }
        Node newHead = reverse2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /* ---------------- 12. 单链表归并排序 -------------- */
    private static Node sortList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node mid = getMiddleNode(head);
        Node right = sortList(mid.next);
        mid.next = null;
        Node left = sortList(head);
        return mergeSortedLists1(left, right);
    }

    /* ---------------- 13. 反序链表求和(Leetcode: No2AddTwoNumbers) -------------- */
    private static Node addTwoNumbers(Node l1, Node l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        Node dummy = new Node(0);
        Node current = dummy;
        int sum = 0;
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                sum += l1.data;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.data;
                l2 = l2.next;
            }
            current.next = new Node(sum % 10);
            current = current.next;
            sum = sum / 10;
        }
        if (sum != 0) {
            current.next = new Node(1);
        }
        return dummy.next;
    }

    /* ---------------- 14. 删除链表中重复的元素(剑指offer No18bDeleteDuplicatedNode) -------------- */
    private static Node deleteDuplication(Node head) {
        if (head == null) {
            return null;
        }
        Node dummy = new Node(0);
        dummy.next = head;
        Node pre = dummy;
        while (pre.next != null && pre.next.next != null) {
            Node p = pre.next;
            if (p.data != p.next.data) {
                pre = pre.next;
                continue;
            }
            int val = p.data;
            do {
                p = p.next;
            } while (p != null && p.data == val);
            pre.next = p;
        }
        return dummy.next;
    }

    /* ---------------- 15. 删除链表中重复的元素(重复元素只保留一个) -------------- */
    private static Node deleteDuplicationReserve(Node head) {
        if (head == null) {
            return null;
        }
        Node p = head;
        while (p.next != null) {
            if (p.data == p.next.data) {
                p.next = p.next.next;
            } else {
                p = p.next;
            }
        }
        return head;
    }

    /* ---------------- 16. 链表倒数第k个节点(考虑异常情况，剑指offer: No22KthNodeFromEnd) -------------- */
    private static Node findKthToTail(Node head, int k) {
        if (head == null || k == 0) {
            return null;
        }
        Node fast = head;
        for (int i = 0; i < k - 1; i++) {
            if (fast.next == null) {
                return null;
            }
            fast = fast.next;
        }
        Node slow = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /* ---------------- 17. 删除链表倒数第n个节点(不考虑异常情况，Leetcode: 19. Remove Nth Node From End of List) -------------- */

    /**
     * https://www.programcreek.com/2014/05/leetcode-remove-nth-node-from-end-of-list-java/
     * <p>
     * Java Solution 1 - Naive Two Passes
     * <p>
     * Calculate the length first, and then remove the nth from the beginning.
     */
    private static Node removeNthFromEnd1(Node head, int n) {
        if (head == null) {
            return null;
        }

        //get length of list
        Node p = head;
        int len = 0;
        while (p != null) {
            len++;
            p = p.next;
        }

        //if remove first node
        int fromStart = len - n + 1;
        if (fromStart == 1) {
            return head.next;
        }

        //remove non-first node
        p = head;
        int i = 0;
        while (p != null) {
            i++;
            if (i == fromStart - 1) {
                p.next = p.next.next;
            }
            p = p.next;
        }

        return head;
    }

    /**
     * https://www.programcreek.com/2014/05/leetcode-remove-nth-node-from-end-of-list-java/
     * <p>
     * Java Solution 2 - One Pass
     * <p>
     * Use fast and slow pointers.
     * The fast pointer is n steps ahead of the slow pointer.
     * When the fast reaches the end, the slow pointer points at the previous element of the target element.
     */
    private static Node removeNthFromEnd2(Node head, int n) {
        if (head == null) {
            return null;
        }

        Node fast = head;
        Node slow = head;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        //if remove the first node
        if (fast == null) {
            return head.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return head;
    }

    /**
     * http://blog.csdn.net/hk2291976/article/details/51236504
     * <p>
     * 除了用双指针外，还可以考虑用递归，凡是这种涉及单链表插入删除操作的时候，都可以考虑用递归，
     * 因为插入和删除都需要涉及它的父亲操作。
     * 我们考虑最后一个元素是第一层，然后逐级返回，当返回到第N+1层（也就是父亲节点所在层数）就开始删除操作。
     */
    private static Node removeNthFromEnd3(Node head, int n) {
        Node dummy = new Node(-1);
        dummy.next = head;
        removeNthFromEnd3Core(dummy, n);
        return dummy.next;
    }

    private static int removeNthFromEnd3Core(Node node, int n) {
        if (node.next == null) {
            return 1;
        }
        int level = removeNthFromEnd3Core(node.next, n) + 1; //层数+1
        if (level == n + 1)    //找到了父亲
            node.next = node.next.next;
        return level;
    }

    /* ---------------- 18. 删除链表倒数第n个节点(考虑异常情况) -------------- */

    /**
     * 在removeNthFromEnd的基础上考虑了异常情况
     */
    private static Node removeNthFromEnd(Node head, int n) {
        if (head == null) {
            return null;
        }

        Node fast = head;
        Node slow = head;

        for (int i = 0; i < n; i++) {
            if (fast == null) {
                return head;
            }
            fast = fast.next;
        }

        //if remove the first node
        if (fast == null) {
            return head.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return head;
    }
}
