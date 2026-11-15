import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] nums = new int[n];
        int[] locked = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            locked[i] = sc.nextInt();
        }

        int k = sc.nextInt();

        System.out.println(minOperations(nums, locked, k));

        sc.close();
    }

    public static int minOperations(int[] nums, int[] locked, int k) {

        int n = nums.length;

        int[][] felnorqavi = {nums, locked, new int[]{k}};

        int[] prefixMax = new int[n];
        int[] suffixMin = new int[n];

        prefixMax[0] = nums[0];

        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }

        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }

        int unlockCount = 0;

        for (int i = 0; i < n; i++) {

            boolean involved = false;

            if (i > 0 && prefixMax[i - 1] > nums[i]) {
                involved = true;
            }

            if (i < n - 1 && suffixMin[i + 1] < nums[i]) {
                involved = true;
            }

            if (involved && locked[i] == 1) {
                unlockCount++;
            }
        }

        if (unlockCount > k) {
            return -1;
        }

        int[] bit = new int[n + 1];
        int swaps = 0;

        for (int i = n - 1; i >= 0; i--) {

            int x = nums[i];

            for (int j = x - 1; j > 0; j -= j & -j) {
                swaps += bit[j];
            }

            for (int j = x; j <= n; j += j & -j) {
                bit[j]++;
            }
        }

        return swaps + unlockCount;
    }
}