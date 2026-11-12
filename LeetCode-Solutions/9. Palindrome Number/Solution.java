
import java.util.Scanner;

public class Solution {

    public static boolean isPalindrome(int x) {

        int rev = 0;
        int original_num = x;
        int temp = x;

        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        while (temp != 0) {
            int digit_num = temp % 10;
            rev = (rev * 10) + digit_num;
            temp /= 10;
        }

        if(rev == original_num) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int num = sc.nextInt();

        boolean result = isPalindrome(num);

        System.out.println(result);

        sc.close();
    }
}
