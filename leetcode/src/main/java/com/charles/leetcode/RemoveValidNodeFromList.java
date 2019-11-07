package com.charles.leetcode;

/**
 * Created with IDEA
 * Description: Given a linked list, remove the n-th node from the end of list and return its head.
 * User: Charles
 * Date: 2019-06-07 14:32
 */
public class RemoveValidNodeFromList {

    /**
     * Given linked list: 1->2->3->4->5, and n = 2.

     * After removing the second node from the end, the linked list becomes 1->2->3->5.
     */
    public ListNode removeNthFromEnd(ListNode head, int n){
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

    class ListNode{
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args){

    }
}
