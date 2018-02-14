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
 * 4. 两个有序单链表合并
 * 5. 判断两个单链表是否相交
 * 6. 获取两个单链表相交的结点
 * 7. 判断单链表是否有环
 * 8. 判断有环单链表中环的长度
 * 9. 获取单链表中，环的起始结点
 * 10. 删除单链表中指定结点
 * 11. 反转链表
 * 12. 单链表归并排序
 * 13. 反序链表求和(LeetCode: 2. Add Two Numbers)
 * 14. 删除链表倒数第n个节点（LeetCode: 19. Remove Nth Node From End of List）
 * 15. 删除链表中重复的元素(剑指offer: No18bDeleteDuplicatedNode)
 * 16. 删除链表中重复的元素(重复元素只保留一个)
 */
public class LinkedListBasicProblems {

    /* ---------------- 节点定义 -------------- */
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            next = null;
        }
    }

    /* ---------------- 1. 单链表倒序输出 -------------- */
    // 单链表的倒序输出即从链表表尾逐个输出到表头。
    // 因为单链表的指针域是单向的，所以要找到表尾的话，需要遍历整个链表。
    // 这个算法可以有两种实现：1、迭代实现，2、递归实现。

    /**
     * 迭代实现
     * <p>
     * 迭代实现关键是考虑倒序输出正好符合栈的先进后出的特点——先遍历到的元素最后输出。
     * 这样，可以遍历一遍，将数据存储在栈中，然后由栈逐个pop即可。
     *
     * @param head 待倒序输出的链表
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
     * 递归实现
     * <p>
     * 递归实现即递归地往下找，直到找到最后一个元素，再回溯输出即可。
     * 但是，递归实现有个很大的缺陷，就是当链表元素特别多的时候，递归层次太深，容易造成栈溢出。
     *
     * @param head 待倒序输出的链表
     */
    private static void printListReverse2(Node head) {
        if (head != null) {
            printListReverse2(head.next);
            System.out.print(head.data + ", ");
        }
    }

    /* ---------------- 2. 查找单链表倒数第k个元素 -------------- */
    // 查找单链表中倒数第k个元素，仍然有两种实现：1、通过获取链表的长度实现。2、通过两个指针实现。

    /**
     * 通过获取链表的长度的实现
     * <p>
     * 第1次遍历，得到链表长度 length。然后第2次遍历，找到第length-k个元素就是了。这种方法效率较低。
     *
     * @param head 待查找的链表
     * @param k    倒数第k个元素
     * @return Node 返回倒数第k个结点。若k=0或链表为null，则返回null，若k大于链表长度，则抛RuntimeException异常
     */
    private static Node getKthNode1(Node head, int k) {
        if (head == null || k == 0) {
            return null;
        }
        int length = 0;
        Node ptr = head;
        while (ptr != null) {
            length++;
            ptr = ptr.next;
        }
        //这里做一步异常处理
        if (k > length) {
            throw new RuntimeException("exceed length of linked list");
        }
        ptr = head;
        int count = length - k;
        while (count > 0) {
            ptr = ptr.next;
            count--;
        }
        return ptr;
    }

    /**
     * 通过两个指针实现
     * <p>
     * 如果不允许遍历整个链表，或者要提高效率，就采用这种思路：
     * 定义两个指针，pFront先指向第k个元素，pBack指向链表的head
     * 两个指针同时移动遍历，当pFront遍历到链表尾部之后，pBack正好是指向倒数第k个元素。
     *
     * @param head 待查找的结点
     * @param k    倒数第k个元素
     * @return Node 返回倒数第k个结点。若k=0或链表为null，则返回null，若k大于链表长度，则抛RuntimeException异常
     */
    private static Node getReKthNode2(Node head, int k) {
        if (head == null || k == 0) {
            return null;
        }

        Node pFront = head;
        while (k > 1) {
            pFront = pFront.next;
            if (pFront == null) {
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
     * 思路：仍然是两个指针，只是pFront一次走2步，pBack一次走1步。直到pFront走到最后一个结点。
     *
     * @param head 待查找的链表
     * @return Node 链表的中间结点。 若链表为null，则返回null
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

    /* ---------------- 4. 两个有序单链表合并 -------------- */
    // 两个单链表head1和head2都各自有序，要求将其合并，得到的结果依然有序。
    // 此问题仍然有两种实现：1、归并排序的思想合并； 2、递归合并

    /**
     * 归并排序的思想合并
     * <p>
     * 思想：用归并排序merge方法的思想。
     *
     * @param head1 第1个有序链表
     * @param head2 第2个有序链表
     * @return Node 归并之后的结果链表 若两个链表都为null，则返回null
     */
    private static Node mergeSortedLinkedlist1(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node dummyHead = new Node(0);

        Node p1 = head1;
        Node p2 = head2;
        Node p = dummyHead;

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

        return dummyHead.next;
    }

    /**
     * 递归合并
     * <p>
     * 用递归的方法代码更简洁，也可读性也很强。
     * 但是，两个链表比较长的时候，递归层次也可能会很深，结果造成栈溢出。
     *
     * @param head1 第1个有序链表
     * @param head2 第2个有序链表
     * @return Node 归并之后的结果链表 ，若两个链表都为null，则返回null
     */
    private static Node mergeSortedLinkedlist2(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        Node head;
        if (head1.data < head2.data) {
            head = head1;
            head.next = mergeSortedLinkedlist2(head1.next, head2);
        } else {
            head = head2;
            head.next = mergeSortedLinkedlist2(head1, head2.next);
        }
        return head;
    }

    /* ---------------- 5. 判断两个单链表是否相交 -------------- */
    // 如果两个链表相交于某一节点，那么在这个相交节点之后的所有节点都是两个链表所共有的。
    // 相当于“Y”字型的两个链表。

    /**
     * 思路：只需要判断两个链表的最后一个节点是否相同即可。
     * 这样，只需要比较一次，但两个链表都遍历到尾部，时间复杂度为O(len1+len2)
     *
     * @param head1 链表1
     * @param head2 链表2
     * @return boolean。 true：两个链表相交； false：两个链表不相交
     */
    private static boolean isLinkedListIntersect(Node head1, Node head2) {
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

    /* ---------------- 6. 获取两个单链表相交的结点 -------------- */
    // 若两个链表相交，则获取相交处的结点。 即“Y”的交点

    /**
     * 思路：如果有交点，则两个链表从交点到尾部一定是相同的元素，而且这段距离小于或等于最短的链表。
     * <p>
     * 可以定义两个指针pFront和pBack，pBack指向较短链表的首部，pFront指向较长链表的lenLong- lenShort位置
     * 两个指针同时移动，直到找到相同结点。
     *
     * @param head1 链表1
     * @param head2 链表2
     * @return Node 返回两个链表相交的结点。若没有相交，则返回null
     */
    public static Node getCommonNode(Node head1, Node head2) {
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

    /* ---------------- 7. 判断单链表是否有环 -------------- */
    // 如果链表有环，单用一个指针是永远无法遍历到尾部的。那么，判断是否有环的思路，仍然是两个指针。

    /**
     * 如果快的指针走到NULL，说明无环；而fast==slow相遇，则证明肯定存在环。
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

    /* ---------------- 8. 判断有环单链表中，环的长度 -------------- */

    /**
     * https://www.cnblogs.com/hiddenfox/p/3408931.html
     * <p>
     * 方法一（网上都是这个答案）：
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
     * 方法二：
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

    /* ---------------- 9. 获取单链表中，环的起始结点 -------------- */

    /**
     * https://www.cnblogs.com/hiddenfox/p/3408931.html
     * <p>
     * 上面链接中关于这道题的解释是错的，应该要推公式的。
     * <p>
     * 当两个结点相遇的时候，将slow再重新指向head，slow和fast一起以slow的速度往后移动，
     * 当再次相遇的时候，该结点就是环的起点。
     *
     * @param head 待处理的链表
     * @return Node 环的起始结点。 若没有环，则返回null
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
        while (slow != fast) { //二者相遇在环的起始结点，则退出
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 配合HashSet一次遍历
     * <p>
     * 思想：
     * 可以在遍历的时候，将链表结点放入HashSet中，当遍历时发现有此元素，则该结点就是环的起始结点。
     * 这种方法很简洁，但是当结点比较多的时候，空间开销太大。
     *
     * @param head 待处理的链表
     * @return Node 环的起始结点。 若没有环，则返回null
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

    /* ---------------- 10. 删除单链表中指定结点 -------------- */
    // 这个问题有个特殊要求，就是要求时间复杂度O(1)
    // 常规的思路：对于这样一段链表：n0 -> n1 -> n2 -> n3 -> n4 如果要删除结点n2，可以让n1指向n3。
    // 然后释放n2。这样的话需要找到结点n1，时间复杂度为O(n)

    /**
     * 思想：对于单链表，可以将n3复制到n2，然后删除n3即可。
     * 这样的方法在java中，如果是删除链表最后一个元素，仍然需要找前面的元素。
     * <p>
     * Note:
     * 1. 这个方法和文本上的不一样，书上的没有返回值，这个因为JAVA引用传递的原因，
     * 如果删除的结点是头结点，如果不采用返回值的方式，那么头结点永远删除不了
     * 2. 输入的待删除结点必须是待链表中的结点，否则会引起错误，这个条件由用户进行保证
     *
     * @param head        链表的表头
     * @param toBeDeleted 待删除的结点
     * @return 删除后的头结点
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

    /* ---------------- 11. 反转链表 -------------- */

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
        if (head.next == null) {
            return head;
        }
        Node reverseNode = reverse2(head.next);
        head.next.next = head;
        head.next = null;
        return reverseNode;
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
        return mergeSortedLinkedlist1(left, right);
    }

    /* ---------------- 13. 反序链表求和(LeetCode: No2AddTwoNumbers) -------------- */
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

    /* ---------------- 14. 删除链表倒数第n个节点（LeetCode: 19. Remove Nth Node From End of List） -------------- */

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
            head = head.next;
            return head;
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

    /* ---------------- 15. 删除链表中重复的元素(剑指offer No18bDeleteDuplicatedNode) -------------- */
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
            Node p1 = p.next;
            while (p1 != null && p1.data == p.data) {
                p1 = p1.next;
            }
            pre.next = p1;
        }
        return dummy.next;
    }

    /* ---------------- 16. 删除链表中重复的元素(重复元素只保留一个) -------------- */
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
}
