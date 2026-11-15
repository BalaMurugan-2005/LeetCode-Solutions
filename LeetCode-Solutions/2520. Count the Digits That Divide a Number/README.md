# [2520. Count the Digits That Divide a Number](https://leetcode.com/problems/count-the-digits-that-divide-a-number/)

## Problem Statement

Given an integer `num`, return the number of digits in `num` that divide `num`.

An integer `val` divides `nums` if `nums % val == 0`.

### Examples

```
Input: num = 7
Output: 1
Explanation: 7 divides itself, hence the answer is 1.
```

```
Input: num = 121
Output: 2
Explanation: 121 is divisible by 1, but not 2. Since 1 occurs twice as a digit, we return 2.
```

```
Input: num = 1248
Output: 4
Explanation: 1248 is divisible by all of its digits, hence the answer is 4.
```

### Constraints

- `1 <= num <= 10^9`
- `num` does not contain `0` as one of its digits.

---

## Approach: Check Every Digit One by One

We take each digit of `num` and check whether it divides `num` evenly. If a digit appears more than once, it is counted each time.

1. Keep the original number in `num` and use a copy `temp` to remove digits one by one. Start with `count = 0`.
2. While `temp > 0`:
   - Take the last digit with `digit = temp % 10`.
   - If `num % digit == 0`, that digit divides `num`, so increase `count`.
   - Remove the last digit with `temp = temp / 10`.
3. Return `count`.

The original `num` must stay unchanged, because every digit is checked against the **full number**. This is why a separate `temp` variable is used.

The constraint says `num` has no `0` digits, so `num % digit` never divides by zero.

### Dry Run

`num = 121`

| temp | digit | `121 % digit` | Divides? | count |
|------|-------|---------------|----------|-------|
| 121 | 1 | 0 | yes | 1 |
| 12 | 2 | 1 | no | 1 |
| 1 | 1 | 0 | yes | 2 |

`temp` becomes `0`, so the loop stops and the output is `2`.

For `num = 7`, the only digit is `7`, and `7 % 7 == 0`, so the output is `1`.

---

## Solution (Java)

```java
class Solution {
    public int countDigits(int num) {

        int count = 0;
        int temp = num;

        while (temp > 0) {

            // last digit of the remaining number
            int digit = temp % 10;

            // check if this digit divides the original number
            if (num % digit == 0) {
                count++;
            }

            // remove the last digit
            temp = temp / 10;
        }

        return count;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(log num)** | The loop runs once per digit, and `num` has at most 10 digits |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- Use a **copy** (`temp`) to strip digits, so the original `num` is still available for the divisibility check.
- Repeated digits are counted every time they appear, because each one is checked separately.
- The "no `0` digit" guarantee means there is no risk of division by zero.
- No string conversion is needed, only `% 10` and `/ 10`.
