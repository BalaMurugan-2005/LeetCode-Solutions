# [4097. Minimum Value of Merged Integers](https://leetcode.com/problems/minimum-value-of-merged-integers/)

## Problem Statement

You are given an integer array `nums` of even length `n`.

You have to merge the elements of `nums` in pairs by merging `nums[0]` with `nums[1]`, `nums[2]` with `nums[3]`, and so on, until `nums[n - 2]` with `nums[n - 1]`.

Return the **minimum** value among all the resulting merged integers.

To merge two integers, concatenate all the decimal digits of the first integer followed by all the decimal digits of the second integer.

### Examples

```
Input: nums = [23,55,42,21]
Output: 2355
Explanation:
- Merging nums[0] = 23 and nums[1] = 55 gives 2355.
- Merging nums[2] = 42 and nums[3] = 21 gives 4221.
- The minimum value among the merged numbers is min(2355, 4221) = 2355.
```

```
Input: nums = [34,7,11,11]
Output: 347
Explanation:
- Merging nums[0] = 34 and nums[1] = 7 gives 347.
- Merging nums[2] = 11 and nums[3] = 11 gives 1111.
- The minimum value among the merged numbers is min(347, 1111) = 347.
```

```
Input: nums = [9,20]
Output: 920
Explanation:
- Merging nums[0] = 9 and nums[1] = 20 gives 920.
- There is only one merged number, so the minimum value is 920.
```

### Constraints

- `2 <= n == nums.length <= 100`
- `n` is even
- `1 <= nums[i] <= 1000`

---

## Approach: Merge Each Pair as a String and Track the Minimum

The pairs are fixed: `(nums[0], nums[1])`, `(nums[2], nums[3])`, and so on. So there is nothing to choose. We only need to build the merged number of every pair and keep the smallest one.

1. Start with `min = Integer.MAX_VALUE`, so any merged value will be smaller.
2. Loop `i` from `0` to the end of the array **in steps of 2**, so `i` is always the first element of a pair.
3. **Merge** the pair `(nums[i], nums[i + 1])` by joining their digits as text: `String.valueOf(nums[i]) + nums[i + 1]`. For example `34` and `7` become `"347"`.
4. Convert the text back to a number with `Integer.parseInt`.
5. Update `min` with `Math.min(min, merged)`.
6. Return `min`.

Since `nums[i] <= 1000`, a merged number has at most 8 digits (for example `1000` and `1000` give `10001000`), which fits easily in an `int`.

### Dry Run

`nums = [34, 7, 11, 11]`

| i | Pair | Merged text | Merged number | min after step |
|---|------|-------------|---------------|----------------|
| 0 | `(34, 7)` | `"347"` | 347 | 347 |
| 2 | `(11, 11)` | `"1111"` | 1111 | 347 (1111 is larger) |

Output: `347`

For `nums = [9, 20]`, there is a single pair, which gives `"920"`, so the output is `920`.

---

## Solution (Java)

```java
class Solution {
    public int minMergedInteger(int[] nums) {
        int min = Integer.MAX_VALUE;

        // step by 2: i is always the first element of a pair
        for (int i = 0; i < nums.length; i += 2) {

            // join the digits of the pair, then convert back to a number
            int merged = Integer.parseInt(
                String.valueOf(nums[i]) + nums[i + 1]
            );

            min = Math.min(min, merged);
        }

        return min;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | There are `n / 2` pairs, and each merge works on a string of at most 8 digits |
| Space | **O(1)** | Only a few variables and one short temporary string per pair |

---

## Key Takeaways

- The pairs are fixed by the problem, so a single loop with `i += 2` visits every pair exactly once.
- Merging two numbers means **concatenating their digits**, which is easy to do with strings: `String.valueOf(a) + b`.
- `Integer.MAX_VALUE` is a safe starting value for tracking a minimum.
- With `nums[i] <= 1000`, the merged value stays well inside the `int` range, so no `long` is needed.
