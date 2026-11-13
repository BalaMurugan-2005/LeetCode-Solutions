
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }
}

class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {

            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;

            carry = sum / 10;
            int digit = sum % 10;

            current.next = new ListNode(digit);
            current = current.next;

            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size of first list: ");
        int n1 = sc.nextInt();

        ListNode l1 = new ListNode(sc.nextInt());
        ListNode temp = l1;

        for (int i = 1; i < n1; i++) {
            temp.next = new ListNode(sc.nextInt());
            temp = temp.next;
        }

        System.out.print("Enter size of second list: ");
        int n2 = sc.nextInt();

        ListNode l2 = new ListNode(sc.nextInt());
        temp = l2;

        for (int i = 1; i < n2; i++) {
            temp.next = new ListNode(sc.nextInt());
            temp = temp.next;
        }

        Solution obj = new Solution();

        ListNode result = obj.addTwoNumbers(l1, l2);

        System.out.print("Result: ");

        while (result != null) {
            System.out.print(result.val);

            if (result.next != null) {
                System.out.print(" -> ");
            }

            result = result.next;
        }

        sc.close();
    }
}
