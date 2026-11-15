import java.util.Scanner;

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();

        System.out.println(sumOfNumberAndReverse(num));

        sc.close();
    }

    public static boolean sumOfNumberAndReverse(int num) {
        boolean flag = false;

        if (num == 0) {
            flag = true;
        }

        for (int i = num; i >= 1; i--) {
            int rev = 0;
            int temp = i;
            int original = i;

            while (temp != 0) {
                int digit_num = temp % 10;
                rev = (rev * 10) + digit_num;
                temp /= 10;
            }

            int result = i + rev;

            if (result == num) {
                flag = true;
            }
        }

        return flag;
    }
}