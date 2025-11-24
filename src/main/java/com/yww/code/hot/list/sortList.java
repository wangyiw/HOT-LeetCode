package com.yww.code.hot.list;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class sortList {
    public ListNode sortList(ListNode head) {
        // 如果链表为空或只有一个节点，直接返回
        if (head == null || head.next == null) {
            return head;
        }

        // 计算链表长度
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }

        // 冒泡排序：外层循环 n-1 次
        for (int i = 0; i < length - 1; i++) {
            ListNode prev = null;
            ListNode curr = head;
            ListNode next = curr.next;

            // 内层循环：比较相邻节点
            while (next != null) {
                if (curr.val > next.val) {
                    // 交换值
                    int temp = curr.val;
                    curr.val = next.val;
                    next.val = temp;
                }
                prev = curr;
                curr = next;
                next = next.next;
            }
        }

        return head;
    }

    /**
     * 合并两个已排序的链表
     * 
     * @param l1 第一个已排序的链表头
     * @param l2 第二个已排序的链表头
     * @return 合并后的新链表头
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0); // 创建一个哑节点，方便处理头节点
        ListNode current = dummyHead;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }

        // 将剩余的节点连接到合并后的链表
        if (l1 != null) {
            current.next = l1;
        } else if (l2 != null) {
            current.next = l2;
        }

        return dummyHead.next; // 返回合并后链表的真正头节点
    }

}
