
import java.util.Scanner;

class Solution {

    public int maxArea(int[] height) {

        int max = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {

            int area = (right - left) * Math.min(height[left], height[right]);
            max = Math.max(max, area);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        int[] height = new int[n];

        System.out.println("Enter height values:");

        for (int i = 0; i < n; i++) {
            height[i] = sc.nextInt();
        }

        Solution obj = new Solution();

        int result = obj.maxArea(height);

        System.out.println("Maximum Area: " + result);

        sc.close();
    }
}
