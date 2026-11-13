
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {

    public ListNode deleteDuplicates(ListNode head) {

        ListNode node = head;

        if (node == null) {
            return node;
        }

        while (node.next != null) {

            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter list size: ");
        int n = sc.nextInt();

        ListNode head = null;
        ListNode temp = null;

        System.out.println("Enter sorted list elements:");

        for (int i = 0; i < n; i++) {

            int val = sc.nextInt();

            ListNode newNode = new ListNode(val);

            if (head == null) {
                head = newNode;
                temp = newNode;
            } else {
                temp.next = newNode;
                temp = temp.next;
            }
        }

        Solution obj = new Solution();

        ListNode result = obj.deleteDuplicates(head);

        System.out.println("List after removing duplicates:");

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

        sc.close();
    }
}
