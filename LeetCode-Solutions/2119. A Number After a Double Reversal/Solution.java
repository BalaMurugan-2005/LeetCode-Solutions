import java.util.Scanner;

class R {
    int rev(int num) {

        int rev_num = 0;
        int temp = num;

        while (temp != 0) {
            int digit_num = temp % 10;
            rev_num = (rev_num * 10) + digit_num;
            temp /= 10;
        }

        return rev_num;
    }
}

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.isSameAfterReversals(num));

        sc.close();
    }

    public boolean isSameAfterReversals(int num) {
        R rev = new R();

        int rev1 = rev.rev(num);

        return rev.rev(rev1) == num;
    }
}