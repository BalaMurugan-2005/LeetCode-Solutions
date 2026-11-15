import java.util.Scanner;

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int num = sc.nextInt();

        System.out.println(countDigits(num));

        sc.close();
    }

    public static int countDigits(int num) {

        int count = 0;
        int temp = num;

        while (temp > 0) {

            int digit = temp % 10;

            if (num % digit == 0) {
                count++;
            }

            temp = temp / 10;
        }

        return count;
    }
}