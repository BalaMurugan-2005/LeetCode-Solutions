# [121. Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)

## Problem Statement

You are given an array `prices` where `prices[i]` is the price of a given stock on the `i`th day.

You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return `0`.

### Examples

```
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
```

```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.
```

### Constraints

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^4`

---

## Approach: One Pass, Track the Lowest Price So Far

The best profit for a selling day is **today's price minus the lowest price seen before it**. So we only need to remember the lowest price so far while scanning the array once from left to right.

1. Set `lowestPrice = prices[0]` (the cheapest price seen so far) and `highest_profit = 0` (the best profit so far, `0` means no transaction).
2. For each day `i` starting from `1`:
   - If `prices[i]` is lower than `lowestPrice`, update `lowestPrice`. This day is now the best day to buy.
   - Calculate `profit = prices[i] - lowestPrice`, the profit if we sell today.
   - If `profit` is bigger than `highest_profit`, update `highest_profit`.
3. Return `highest_profit`.

Since the lowest price is always from today or an earlier day, we never sell before we buy. If prices only go down, every `profit` is `0`, so the answer stays `0`.

### Dry Run

`prices = [7, 1, 5, 3, 6, 4]`, start with `lowestPrice = 7` and `highest_profit = 0`

| i | prices[i] | lowestPrice | profit (`prices[i] - lowestPrice`) | highest_profit |
|---|-----------|-------------|------------------------------------|----------------|
| 1 | 1 | 1 (new low) | 0 | 0 |
| 2 | 5 | 1 | 4 | 4 |
| 3 | 3 | 1 | 2 | 4 |
| 4 | 6 | 1 | 5 | 5 |
| 5 | 4 | 1 | 3 | 5 |

Output: `5`

For `prices = [7, 6, 4, 3, 1]`, the lowest price keeps dropping, so every profit is `0` and the output is `0`.

---

## Solution (Java)

```java
class Solution {
    public int maxProfit(int[] prices) {
        int lowestPrice = prices[0];
        int highest_profit = 0;
        int profit = 0;

        for (int i = 1; i < prices.length; i++) {

            // a cheaper day to buy is found
            if (lowestPrice > prices[i]) {
                lowestPrice = prices[i];
            }

            // profit if we sell today
            profit = prices[i] - lowestPrice;

            // keep the best profit seen so far
            if (profit > highest_profit) {
                highest_profit = profit;
            }
        }

        return highest_profit;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Single pass through the array |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- For each possible selling day, the best buying day is the **cheapest day before it**, so there is no need to compare every pair of days (which would be O(n^2)).
- Keeping `highest_profit` at `0` at the start handles the "no profit" case automatically.
- Updating `lowestPrice` first and then calculating the profit is safe, because on a new low the profit is `0`.
- Only two running values are needed (`lowestPrice` and `highest_profit`), so no extra array is used.
