# [258. Add Digits](https://leetcode.com/problems/add-digits/)

## Problem Statement

Given an integer `num`, repeatedly add all its digits until the result has only one digit, and return it.

### Examples

```
Input: num = 38
Output: 2
Explanation: The process is
38 --> 3 + 8 --> 11
11 --> 1 + 1 --> 2
Since 2 has only one digit, return it.
```

```
Input: num = 0
Output: 0
```

### Constraints

- `0 <= num <= 2^31 - 1`

---

## Approach: Repeat Digit Sum Until One Digit Is Left

The problem describes the process directly, so the solution simulates it with two loops.

1. **Outer loop:** keep repeating while `num >= 10` (while the number has more than one digit).
2. **Inner loop:** add up all the digits of `num`:
   - `digit = num % 10` takes the last digit.
   - `sum += digit` adds it to the total.
   - `num /= 10` removes the last digit.
   - Continue until `num` becomes `0`.
3. Set `num = sum`. The outer loop checks again whether the result still has more than one digit.
4. When `num < 10`, return it.

A number such as `0` or `7` is already a single digit, so the outer loop never runs and it is returned as it is.

### Dry Run

`num = 38`

| Round | num at start | Digits added | sum | num after round |
|-------|--------------|--------------|-----|-----------------|
| 1 | 38 | 8 + 3 | 11 | 11 |
| 2 | 11 | 1 + 1 | 2 | 2 |

`2 < 10`, so the loop stops and the output is `2`.

For `num = 0`, `0 >= 10` is false, so the loop is skipped and `0` is returned.

---

## Solution (Java)

```java
class Solution {
    public int addDigits(int num) {

        // repeat until only one digit is left
        while (num >= 10) {
            int sum = 0;

            // add all the digits of num
            while (num != 0) {
                int digit = num % 10;
                sum += digit;
                num /= 10;
            }

            // use the digit sum as the new number
            num = sum;
        }

        return num;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(log num)** | The first round goes through all the digits of `num`; after that the number is tiny (at most 90 for a 10-digit input), so only a few more short rounds follow |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- `% 10` gets the last digit and `/ 10` removes it, which is the standard pattern for working with digits.
- The outer loop condition `num >= 10` means "more than one digit", so single-digit inputs (including `0`) are returned immediately.
- The digit sum of a number is much smaller than the number itself, so the process ends after very few rounds.
