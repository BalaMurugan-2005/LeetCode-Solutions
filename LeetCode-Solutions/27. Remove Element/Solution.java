
import java.util.Scanner;

class Solution {

    public int removeElement(int[] nums, int val) {

        int count = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] != val) {
                nums[count++] = nums[i];
            }
        }

        return count;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        int[] nums = new int[n];

        System.out.println("Enter array elements:");

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        System.out.print("Enter value to remove: ");
        int val = sc.nextInt();

        Solution obj = new Solution();

        int result = obj.removeElement(nums, val);

        System.out.println("Remaining elements count: " + result);

        System.out.print("Array after removing element: ");

        for (int i = 0; i < result; i++) {
            System.out.print(nums[i] + " ");
        }

        sc.close();
    }
}
