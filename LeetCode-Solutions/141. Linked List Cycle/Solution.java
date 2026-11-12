
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {

    public boolean hasCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter list size: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println(false);
            sc.close();
            return;
        }

        ListNode head = new ListNode(0);
        ListNode temp = head;

        ListNode[] nodes = new ListNode[n];

        System.out.println("Enter list elements:");

        for (int i = 0; i < n; i++) {
            nodes[i] = new ListNode(sc.nextInt());
            temp.next = nodes[i];
            temp = temp.next;
        }

        System.out.print("Enter cycle position : ");
        int pos = sc.nextInt();

        if (pos >= 0 && pos < n) {
            temp.next = nodes[pos];
        }

        Solution obj = new Solution();

        boolean result = obj.hasCycle(head.next);

        System.out.println("Has Cycle: " + result);

        sc.close();
    }
}
