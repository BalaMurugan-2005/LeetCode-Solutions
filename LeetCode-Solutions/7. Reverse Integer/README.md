# [7. Reverse Integer](https://leetcode.com/problems/reverse-integer/)

## Problem Statement

Given a signed 32-bit integer `x`, return `x` with its digits reversed. If reversing `x` causes the value to go outside the signed 32-bit integer range `[-2^31, 2^31 - 1]`, then return `0`.

**Assume the environment does not allow you to store 64-bit integers (signed or unsigned).**

### Examples

```
Input: x = 123
Output: 321
```

```
Input: x = -123
Output: -321
```

```
Input: x = 120
Output: 21
```

### Constraints

- `-2^31 <= x <= 2^31 - 1`

---

## Approach: Reverse Digit by Digit With Overflow Check

The digits are reversed with the same arithmetic pattern used in the Palindrome Number problem:

- `x % 10` gives the **last digit**.
- `reverse = reverse * 10 + digit` **appends** that digit to the reversed number.
- `x /= 10` **removes** the last digit.

The main difficulty is **overflow**. We are not allowed to use `long`, so we must check **before** multiplying by 10 whether the result would leave the `int` range.

The limits are:

- `Integer.MAX_VALUE = 2147483647`, so `MAX / 10 = 214748364` and the last digit is `7`.
- `Integer.MIN_VALUE = -2147483648`, so `MIN / 10 = -214748364` and the last digit is `-8`.

Before every step:

1. **Positive overflow:** if `reverse > MAX / 10`, the next `reverse * 10` will overflow. If `reverse == MAX / 10` and `digit > 7`, adding the digit will overflow. In both cases return `0`.
2. **Negative overflow:** if `reverse < MIN / 10`, or `reverse == MIN / 10` and `digit < -8`, return `0`.
3. Otherwise it is safe, so update `reverse` and remove the digit from `x`.

**Negative numbers need no special handling.** In Java, `%` keeps the sign of the number, so for `x = -123` the digits come out as `-3`, `-2`, `-1`, and the reversed result is automatically negative.

### Dry Run

`x = -123`

| Step | x | digit | reverse after step |
|------|---|-------|--------------------|
| 1 | -123 | -3 | `0 * 10 + (-3) = -3` |
| 2 | -12 | -2 | `-3 * 10 + (-2) = -32` |
| 3 | -1 | -1 | `-32 * 10 + (-1) = -321` |
| End | 0 | - | `-321` |

Output: `-321`

`x = 120`: the first digit is `0`, so `reverse` stays `0`. Then `2` gives `2`, and `1` gives `21`. The trailing zero disappears automatically, so the output is `21`.

**Overflow example:** `x = 1534236469`. After 9 digits, `reverse = 964632435`. The next digit is `1`, and `964632435 > 214748364` (`MAX / 10`), so the function returns `0` before the overflow happens.

---

## Solution (Java)

```java
class Solution {
    public int reverse(int x) {
        int reverse = 0;

        while (x != 0) {
            int digit = x % 10;

            // check positive overflow before multiplying by 10
            if (reverse > Integer.MAX_VALUE / 10 ||
                (reverse == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }

            // check negative overflow before multiplying by 10
            if (reverse < Integer.MIN_VALUE / 10 ||
                (reverse == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }

            reverse = (reverse * 10) + digit;
            x /= 10;
        }

        return reverse;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(log \|x\|)** | The loop runs once per digit, and an `int` has at most 10 digits |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- `% 10`, `/ 10` and `reverse * 10 + digit` are the core steps of digit-reversal problems.
- Overflow must be checked **before** the multiplication, because after overflowing the value is already wrong.
- `7` and `-8` are the last digits of `2147483647` and `-2147483648`.
- Java's `%` and `/` keep the sign for negative numbers, so negative inputs need no extra code.
- Trailing zeros in the input (like in `120`) disappear without any extra handling.
