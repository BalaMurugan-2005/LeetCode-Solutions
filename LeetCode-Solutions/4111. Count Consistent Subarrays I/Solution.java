import java.util.Scanner;

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        System.out.println(countConsistent(nums));

        sc.close();
    }

    public static int countConsistent(int[] nums) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int and_val = nums[i];
            int min_val = nums[i];

            for (int j = i; j < nums.length; j++) {
                and_val = and_val & nums[j];
                min_val = Math.min(min_val, nums[j]);

                if (and_val == min_val) {
                    count++;
                }
            }
        }

        return count;
    }
}