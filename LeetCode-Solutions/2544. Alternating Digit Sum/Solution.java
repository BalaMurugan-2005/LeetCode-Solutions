import java.util.Scanner;

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        System.out.println(alternateDigitSum(n));

        sc.close();
    }

    public static int alternateDigitSum(int n) {
        int res = 0;
        int sign = 1;

        while (n != 0) {
            res += n % 10 * sign;
            sign = -sign;
            n /= 10;
        }

        return res * -sign;
    }
}