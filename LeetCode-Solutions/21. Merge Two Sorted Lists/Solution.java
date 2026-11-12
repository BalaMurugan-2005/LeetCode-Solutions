
import java.util.Scanner;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }
}

class Solution {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode dummy = new ListNode(0);
        ListNode temp = dummy;

        while (list1 != null && list2 != null) {

            if (list1.val <= list2.val) {
                temp.next = list1;
                list1 = list1.next;
            }
            else {
                temp.next = list2;
                list2 = list2.next;
            }

            temp = temp.next;
        }

        if (list1 != null) {
            temp.next = list1;
        }

        if (list2 != null) {
            temp.next = list2;
        }

        return dummy.next;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size of List 1: ");
        int n1 = sc.nextInt();

        ListNode list1 = new ListNode(0);
        ListNode temp1 = list1;

        System.out.println("Enter List 1 elements:");

        for (int i = 0; i < n1; i++) {
            temp1.next = new ListNode(sc.nextInt());
            temp1 = temp1.next;
        }

        System.out.print("Enter size of List 2: ");
        int n2 = sc.nextInt();

        ListNode list2 = new ListNode(0);
        ListNode temp2 = list2;

        System.out.println("Enter List 2 elements:");

        for (int i = 0; i < n2; i++) {
            temp2.next = new ListNode(sc.nextInt());
            temp2 = temp2.next;
        }

        Solution obj = new Solution();

        ListNode result = obj.mergeTwoLists(list1.next, list2.next);

        System.out.println("Merged List:");

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

        sc.close();
    }
}
