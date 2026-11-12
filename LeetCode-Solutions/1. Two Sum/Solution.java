
import java.util.Scanner;
import java.util.Arrays;

class Solution {

    public int[] twoSum(int[] nums, int target) {

        int n = nums.length;

        int[] index = new int[n];

        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - 1 - i; j++) {

                if (nums[j] > nums[j + 1]) {

                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;

                    temp = index[j];
                    index[j] = index[j + 1];
                    index[j + 1] = temp;
                }
            }
        }

        int left = 0;
        int right = n - 1;

        while (left < right) {

            int sum = nums[left] + nums[right];

            if (sum == target) {
                return new int[] {
                    index[left],
                    index[right]
                };
            }

            if (sum < target) {
                left++;
            }
            else {
                right--;
            }
        }

        return new int[] {-1, -1};
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

        System.out.print("Enter target: ");
        int target = sc.nextInt();

        Solution obj = new Solution();

        int[] result = obj.twoSum(nums, target);

        System.out.println("Output: " + Arrays.toString(result));

        sc.close();
    }
}
