
import java.util.Scanner;

class Solution {

    public int searchInsert(int[] nums, int target) {

        int count = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == i) {
                count++;
                break;
            }
            else if (nums[i] < target) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        int[] nums = new int[n];

        System.out.println("Enter sorted array elements:");

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        System.out.print("Enter target: ");
        int target = sc.nextInt();

        Solution obj = new Solution();

        int result = obj.searchInsert(nums, target);

        System.out.println("Search Insert Position: " + result);

        sc.close();
    }
}
