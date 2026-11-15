import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int index = sc.nextInt();
        int value = sc.nextInt();

        System.out.println(minimumMaxValue(n, index, value));

        sc.close();
    }

    public static long minimumMaxValue(int n, int index, int value) {

        if (value == 1) {
            return -1;
        }

        int p = value;

        for (int i = 2; i * i <= value; i++) {
            if (value % i == 0) {
                p = i;
                break;
            }
        }

        if (value - p < 2L * index) {
            return -1;
        }

        long right = n - 1L - index;

        if (right == 0) {
            return value;
        }

        return (long) value + p + 2L * (right - 1);
    }
}