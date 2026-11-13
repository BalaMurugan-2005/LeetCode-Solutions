
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

class Solution {

    public boolean isPalindrome(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        if (fast != null) {
            slow = slow.next;
        }

        ListNode prev = null;
        ListNode current = slow;

        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        ListNode first = head;
        ListNode second = prev;

        while (second != null) {
            if (first.val != second.val) {
                return false;
            }

            first = first.next;
            second = second.next;
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter list size: ");
        int n = sc.nextInt();

        ListNode head = null;
        ListNode temp = null;

        System.out.println("Enter list elements:");

        for (int i = 0; i < n; i++) {

            int val = sc.nextInt();

            ListNode newNode = new ListNode(val);

            if (head == null) {
                head = newNode;
                temp = newNode;
            }
            else {
                temp.next = newNode;
                temp = temp.next;
            }
        }

        Solution obj = new Solution();

        boolean result = obj.isPalindrome(head);

        System.out.println("Is Palindrome: " + result);

        sc.close();
    }
}
