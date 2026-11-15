# [2119. A Number After a Double Reversal](https://leetcode.com/problems/a-number-after-a-double-reversal/)

## Problem Statement

**Reversing** an integer means to reverse all its digits.

- For example, reversing `2021` gives `1202`. Reversing `12300` gives `321` as the **leading zeros are not retained**.

Given an integer `num`, **reverse** `num` to get `reversed1`, then **reverse** `reversed1` to get `reversed2`. Return `true` if `reversed2` equals `num`. Otherwise return `false`.

### Examples

```
Input: num = 526
Output: true
Explanation: Reverse num to get 625, then reverse 625 to get 526, which equals num.
```

```
Input: num = 1800
Output: false
Explanation: Reverse num to get 81, then reverse 81 to get 18, which does not equal num.
```

```
Input: num = 0
Output: true
Explanation: Reverse num to get 0, then reverse 0 to get 0, which equals num.
```

### Constraints

- `0 <= num <= 10^6`

---

## Approach: Reverse Twice and Compare

The problem can be solved by doing exactly what it says: reverse the number, reverse the result, and compare with the original.

1. Write a helper method `rev(int num)` in a small class `R` that reverses the digits using arithmetic:
   - `digit_num = temp % 10` takes the last digit.
   - `rev_num = rev_num * 10 + digit_num` adds it to the reversed number.
   - `temp /= 10` removes the last digit.
   - Repeat until `temp` becomes `0`.
2. In `isSameAfterReversals`, call `rev` on `num` to get `rev1`, then call `rev` on `rev1`.
3. Return `true` if the result equals `num`, otherwise `false`.

Leading zeros are lost naturally in the first reversal (for example `1800` becomes `0081`, which is stored as `81`), and a number that lost its trailing zeros can never match the original after the second reversal.

### Dry Run

| num | First reversal | Second reversal | Equal to num? |
|-----|----------------|-----------------|---------------|
| 526 | 625 | 526 | yes, `true` |
| 1800 | 81 (zeros dropped) | 18 | no, `false` |
| 0 | 0 (loop is skipped) | 0 | yes, `true` |

For `num = 1800`, the digits are taken as `0, 0, 8, 1`, so `rev_num` goes `0, 0, 8, 81`. The second reversal of `81` gives `18`, and `18 != 1800`.

---

## Solution (Java)

```java
class R {
    // reverses the digits of a number
    int rev(int num) {

        int rev_num = 0;
        int temp = num;
        while (temp != 0) {
            int digit_num = temp % 10;
            rev_num = (rev_num * 10) + digit_num;
            temp /= 10;
        }
        return rev_num;
    }
}

class Solution {
    public boolean isSameAfterReversals(int num) {
        R rev = new R();

        // reverse twice and compare with the original
        int rev1 = rev.rev(num);
        return rev.rev(rev1) == num;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(log num)** | Each reversal runs once per digit, and `num` has at most 7 digits |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- Reversing with `% 10` and `/ 10` drops leading zeros of the reversed number automatically.
- A number that **ends with `0`** (and is not `0` itself) loses that zero after the first reversal, so it can never come back after the second one.
- `0` is a special case: the loop does not run and `0` is returned, which equals the original, so the answer is `true`.
- Putting the reverse logic in a separate method avoids repeating the same code twice.
