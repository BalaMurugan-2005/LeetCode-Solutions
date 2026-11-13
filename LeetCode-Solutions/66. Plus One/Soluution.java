
import java.util.Scanner;
import java.util.Arrays;

class Solution {

    public int[] plusOne(int[] digits) {

        for (int i = digits.length - 1; i >= 0; i--) {

            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        int[] digits = new int[n];

        System.out.println("Enter digits:");

        for (int i = 0; i < n; i++) {
            digits[i] = sc.nextInt();
        }

        Solution obj = new Solution();

        int[] result = obj.plusOne(digits);

        System.out.println("Output: " + Arrays.toString(result));

        sc.close();
    }
}
