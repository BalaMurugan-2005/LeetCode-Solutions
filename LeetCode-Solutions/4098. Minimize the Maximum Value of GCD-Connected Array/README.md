# [4098. Minimize the Maximum Value of GCD-Connected Array](https://leetcode.com/problems/minimize-the-maximum-value-of-gcd-connected-array/)

## Problem Statement

You are given three integers `n`, `index`, and `value`.

An integer array `nums` of length `n` is called **GCD-Connected** if:

- `nums[i] > 0` for `0 <= i <= n - 1`.
- `nums[i] < nums[i + 1]` for `0 <= i <= n - 2`.
- `nums[index] = value`.
- `gcd(nums[i], nums[i + 1]) > 1` for `0 <= i <= n - 2`.

Return the **minimum possible maximum value**, `nums[n - 1]`, among all GCD-Connected arrays `nums`. If no such array exists, return `-1`.

### Examples

```
Input: n = 4, index = 2, value = 8
Output: 10
Explanation:
- The GCD-Connected array will look like: nums = [?, ?, 8, ?] since nums[2] = 8.
- Since nums[3] > 8 and gcd(8, 9) = 1, every valid GCD-Connected array must have nums[3] >= 10.
- One optimal array satisfying all conditions is [2, 4, 8, 10].
- Thus, the minimum possible value of nums[3] is 10.
```

```
Input: n = 2, index = 1, value = 3
Output: -1
Explanation:
- The GCD-Connected array will look like: nums = [?, 3] since nums[1] = 3.
- Since we need 0 < nums[0] < nums[1] = 3, there are only two possible arrays: [1, 3] and [2, 3].
  Neither of them satisfies gcd(nums[0], nums[1]) > 1.
- Therefore, no valid GCD-Connected array exists, and the answer is -1.
```

```
Input: n = 2, index = 0, value = 3
Output: 6
Explanation:
- The GCD-Connected array will look like: nums = [3, ?] since nums[0] = 3.
- Since nums[1] > 3 and gcd(3, 4) = gcd(3, 5) = 1, every valid GCD-Connected array must have nums[1] >= 6.
- One optimal array is [3, 6].
- Thus, the minimum possible value of nums[1] is 6.
```

### Constraints

- `2 <= n <= 10^9`
- `1 <= value <= 10^9`
- `0 <= index <= n - 1`

---

## Approach: Smallest Prime Factor + Math (No Array Needed)

`n` can be up to 10^9, so the array can never be built. The answer comes from a few observations, using `p` = the **smallest prime factor** of `value`.

**1. The special case `value == 1`.** `gcd(1, x) = 1` for every `x`, and `n >= 2` means `1` always has a neighbour. So the answer is `-1`.

**2. The smallest prime factor `p`.** Check `i = 2, 3, ...` while `i * i <= value`. The first `i` that divides `value` is `p`. If none divides it, `value` is prime and `p = value`.

**3. The left side (`index` elements before `value`).**

- The neighbour on the left must share a prime factor with `value`. The largest number below `value` that does this is `value - p`.
- `value - p` is always **even** (if `value` is even then `p = 2`; if `value` is odd then both `value` and `p` are odd).
- An even number `y` can have a chain of `index` elements ending at it (`..., y - 4, y - 2, y`, all even, so every neighbouring `gcd` is at least 2) only if the chain stays positive, which means `y >= 2 * index`.
- So the left side is possible only if `value - p >= 2 * index`. Otherwise return `-1`.

**4. The right side (`right = n - 1 - index` elements after `value`).**

- If `right == 0`, `value` is the last element, so the answer is just `value`.
- The first element after `value` must share a factor with `value`, so the smallest choice is `value + p`.
- `value + p` is even, and two consecutive integers are always coprime, so every later step must grow by **at least 2**. Adding `2` each time works, because all of those numbers are even.
- So the minimum last element is `value + p + 2 * (right - 1)`.

### Dry Run

**Example 1:** `n = 4`, `index = 2`, `value = 8`

| Step | Calculation | Result |
|------|-------------|--------|
| Smallest prime factor | `8 % 2 == 0` | `p = 2` |
| Left check | `value - p = 6`, `2 * index = 4`, and `6 >= 4` | possible |
| Right count | `right = 4 - 1 - 2 = 1` | 1 element after |
| Answer | `8 + 2 + 2 * (1 - 1)` | **10** |

**Example 2:** `n = 2`, `index = 1`, `value = 3`

`3` is prime, so `p = 3`. The left check is `value - p = 0`, which is less than `2 * index = 2`, so the answer is `-1`.

**Example 3:** `n = 2`, `index = 0`, `value = 3`

`p = 3`. The left check is `0 >= 0`, so it is possible. `right = 2 - 1 - 0 = 1`, so the answer is `3 + 3 + 2 * 0 = 6`.

**Last-position case:** `n = 2`, `index = 1`, `value = 15`

`p = 3`. The left check is `15 - 3 = 12 >= 2`, so it is possible. `right = 0`, so the answer is `value = 15` (for example `[3, 15]`).

---

## Solution (Java)

```java
class Solution {
    public long minimumMaxValue(int n, int index, int value) {

        // Special case: 1 has gcd 1 with every number
        if (value == 1) {
            return -1;
        }

        // Find smallest prime factor (value itself if it is prime)
        int p = value;

        for (int i = 2; i * i <= value; i++) {
            if (value % i == 0) {
                p = i;
                break;
            }
        }

        // Check left side feasibility
        if (value - p < 2L * index) {
            return -1;
        }

        long right = n - 1L - index;

        // value is already the last element
        if (right == 0) {
            return value;
        }

        return (long) value + p + 2L * (right - 1);
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(sqrt(value))** | Finding the smallest prime factor checks divisors up to the square root of `value`; everything else is a constant number of steps |
| Space | **O(1)** | Only a few variables are used; the array is never created |

---

## Key Takeaways

- With `n` up to 10^9, the problem must be solved with **math**, not by building the array.
- Two consecutive integers are always coprime, so each step between neighbours must be at least `2`.
- The smallest prime factor `p` of `value` gives the closest neighbours: `value - p` on the left and `value + p` on the right.
- A chain of **even numbers** is the cheapest way to extend to either side, because the `gcd` of two even numbers is at least `2`.
- The case `index == n - 1` needs its own check, because there is nothing to the right and the answer is `value` itself.
