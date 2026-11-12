# [9. Palindrome Number](https://leetcode.com/problems/palindrome-number/)

## Problem Statement

Given an integer `x`, return `true` if `x` is a palindrome, and `false` otherwise.

### Examples

```
Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.
```

```
Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-.
Therefore it is not a palindrome.
```

```
Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
```

### Constraints

- `-2^31 <= x <= 2^31 - 1`

**Follow up:** Could you solve it without converting the integer to a string?

---

## Approach: Reverse the Number

A number is a palindrome if it is equal to its own reverse. This solution builds the reverse using only arithmetic, so no string conversion is needed (this answers the follow-up).

1. **Quick rejections:**
   - If `x` is **negative**, return `false` (the `-` sign can never match at the end).
   - If `x` ends with `0` and is not `0` itself (like `10`, `120`), return `false`, because its reverse would start with `0`, which is not possible for a normal number.
2. Keep a copy of the original number in `original_num`.
3. **Reverse the digits** using a loop:
   - `digit_num = temp % 10` gets the last digit.
   - `rev = rev * 10 + digit_num` appends that digit to the reversed number.
   - `temp /= 10` removes the last digit.
4. Compare `rev` with `original_num`. If they are equal, it is a palindrome.

### Dry Run

`x = 121`

| Step | temp | digit_num | rev |
|------|------|-----------|-----|
| Start | 121 | - | 0 |
| 1 | 121 | 1 | 0 x 10 + 1 = 1 |
| 2 | 12 | 2 | 1 x 10 + 2 = 12 |
| 3 | 1 | 1 | 12 x 10 + 1 = 121 |
| End | 0 | - | 121 |

`rev (121) == original_num (121)` so the output is `true`.

`x = 10` is rejected immediately by the check `x % 10 == 0 && x != 0`, so the output is `false`.

---

## Solution (Java)

```java
public class Solution {

    public static boolean isPalindrome(int x) {

        int rev = 0;
        int original_num = x;

        int temp = x;

        // negative numbers and numbers ending with 0 (except 0) can't be palindromes
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        // reverse the number digit by digit
        while (temp != 0) {
            int digit_num = temp % 10;
            rev = (rev * 10) + digit_num;
            temp /= 10;
        }

        // palindrome if the reverse equals the original
        if (rev == original_num) {
            return true;
        } else {
            return false;
        }
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(log n)** | The loop runs once per digit, and the number of digits is about log10(n) |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- `% 10` extracts the last digit and `/ 10` removes it. This is the core pattern for digit-based problems.
- `rev = rev * 10 + digit` is the standard way to build a reversed number.
- Early checks for negatives and trailing zeros handle the tricky edge cases quickly.
- Solves the follow-up: no `String` conversion is used.
