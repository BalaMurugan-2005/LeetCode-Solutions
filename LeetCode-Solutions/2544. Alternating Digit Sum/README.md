# [2544. Alternating Digit Sum](https://leetcode.com/problems/alternating-digit-sum/)

## Problem Statement

You are given a positive integer `n`. Each digit of `n` has a sign according to the following rules:

- The **most significant digit** is assigned a **positive** sign.
- Each other digit has an **opposite sign** to its adjacent digits.

Return the sum of all digits with their corresponding sign.

### Examples

```
Input: n = 521
Output: 4
Explanation: (+5) + (-2) + (+1) = 4.
```

```
Input: n = 111
Output: 1
Explanation: (+1) + (-1) + (+1) = 1.
```

```
Input: n = 886996
Output: 0
Explanation: (+8) + (-8) + (+6) + (-9) + (+9) + (-6) = 0.
```

### Constraints

- `1 <= n <= 10^9`

---

## Approach: Extract Digits From the Right, Fix the Sign at the End

Digits are easiest to take from the **right side** with `n % 10`, but the sign rule starts from the **left side** (the most significant digit must be positive). The solution handles this in two steps:

1. **Add digits from right to left with alternating signs.** Start with `sign = 1`. For each digit:
   - `res += digit * sign`
   - flip the sign: `sign = -sign`
   - remove the digit: `n /= 10`
   
   This gives the **rightmost** digit a `+` sign, the next one a `-`, and so on.
2. **Correct the overall sign.** The most significant digit must be `+`, but it only got `+` if the number has an **odd** number of digits. After the loop, `sign` has been flipped once for every digit, so:
   - **Odd number of digits:** `sign` ends as `-1`, and the leftmost digit already had `+`. Return `res * -sign = res * 1`, so `res` is unchanged.
   - **Even number of digits:** `sign` ends as `+1`, and the leftmost digit got `-`, so all signs are reversed. Return `res * -sign = res * -1`, which fixes them.

So `res * -sign` always gives the answer with the most significant digit positive.

### Dry Run

`n = 521` (3 digits, odd)

| Step | n | digit | sign used | res | sign after |
|------|---|-------|-----------|-----|------------|
| 1 | 521 | 1 | +1 | 1 | -1 |
| 2 | 52 | 2 | -1 | -1 | +1 |
| 3 | 5 | 5 | +1 | 4 | -1 |

Return `res * -sign = 4 * -(-1) = 4`

`n = 10` (2 digits, even)

| Step | n | digit | sign used | res | sign after |
|------|---|-------|-----------|-----|------------|
| 1 | 10 | 0 | +1 | 0 | -1 |
| 2 | 1 | 1 | -1 | -1 | +1 |

Here the leftmost digit `1` got a `-` sign, so `res = -1`. Return `res * -sign = -1 * -(1) = 1`, which matches `(+1) + (-0) = 1`.

---

## Solution (Java)

```java
class Solution {
    public int alternateDigitSum(int n) {
        int res = 0;
        int sign = 1;

        // take digits from right to left, alternating the sign
        while (n != 0) {
            res += n % 10 * sign;
            sign = -sign;
            n /= 10;
        }

        // fix the signs so that the most significant digit is positive
        return res * -sign;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(log n)** | The loop runs once per digit, and `n` has at most 10 digits |
| Space | **O(1)** | Only two integer variables are used |

---

## Key Takeaways

- `% 10` and `/ 10` give the digits from right to left, but the sign rule starts from the left, so the sign must be corrected at the end.
- After the loop, `sign` has been flipped once per digit, so it tells whether the number had an odd or even number of digits.
- `res * -sign` turns the result into the correct one in both cases, with no need to count the digits separately.
- No string conversion is used, so the digits are never stored.
