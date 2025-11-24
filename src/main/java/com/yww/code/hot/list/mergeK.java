package com.yww.code.hot.list;

import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class mergeK {
    /**
     * 给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 链表归并
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 使用优先队列（最小堆）
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        // 将每个链表的头节点加入队列
        for (ListNode node : lists) {
            if (node != null) {
                queue.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!queue.isEmpty()) {
            ListNode minNode = queue.poll();
            tail.next = minNode;
            tail = tail.next;

            if (minNode.next != null) {
                queue.offer(minNode.next);
            }
        }

        return dummy.next;
    }

}
