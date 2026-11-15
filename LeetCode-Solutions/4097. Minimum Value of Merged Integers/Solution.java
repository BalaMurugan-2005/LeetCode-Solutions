import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i += 2) {
            int merged = Integer.parseInt(
                String.valueOf(nums[i]) + nums[i + 1]
            );

            min = Math.min(min, merged);
        }

        System.out.println(min);
        sc.close();
    }
}