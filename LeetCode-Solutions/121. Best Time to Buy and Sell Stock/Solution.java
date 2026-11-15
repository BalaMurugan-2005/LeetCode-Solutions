import java.util.Scanner;

class Solution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] prices = new int[n];

        for (int i = 0; i < n; i++) {
            prices[i] = sc.nextInt();
        }

        System.out.println(maxProfit(prices));

        sc.close();
    }

    public static int maxProfit(int[] prices) {
        int lowestPrice = prices[0];
        int highest_profit = 0;
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {

            if (lowestPrice > prices[i]) {
                lowestPrice = prices[i];
            }

            profit = prices[i] - lowestPrice;

            if (profit > highest_profit) {
                highest_profit = profit;
            }
        }

        return highest_profit;
    }
}