import java.util.Scanner;

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int size = sc.nextInt();
        int[] flowerbed = new int[size];

        for (int i = 0; i < size; i++) {
            flowerbed[i] = sc.nextInt();
        }

        int n = sc.nextInt();

        System.out.println(canPlaceFlowers(flowerbed, n));

        sc.close();
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        boolean flag = false;

        for (int i = 0; i < flowerbed.length; i++) {
            boolean leftempty = false;
            boolean rightemprt = false;

            if (i == 0 || flowerbed[i - 1] == 0) {
                leftempty = true;
            }

            if (i == flowerbed.length - 1 || flowerbed[i + 1] == 0) {
                rightemprt = true;
            }

            if (flowerbed[i] == 0 && leftempty && rightemprt) {
                flowerbed[i] = 1;
                n--;

                if (n == 0) break;
            }
        }

        if (n <= 0) {
            flag = true;
        }

        return flag;
    }
}