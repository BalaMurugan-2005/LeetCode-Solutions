# [67. Add Binary](https://leetcode.com/problems/add-binary/)

## Problem Statement

Given two binary strings `a` and `b`, return their sum as a binary string.

### Examples

```
Input: a = "11", b = "1"
Output: "100"
```

```
Input: a = "1010", b = "1011"
Output: "10101"
```

### Constraints

- `1 <= a.length, b.length <= 10^4`
- `a` and `b` consist only of `'0'` or `'1'` characters.
- Each string does not contain leading zeros except for the zero itself.

---

## Approach: Add Bit by Bit With a Carry

This is the same as adding two numbers on paper: start from the **rightmost bits**, add them together with the carry, write down one digit and carry the rest to the left.

1. Place a pointer at the **last character** of each string (`i` for `a`, `j` for `b`) and set `carry = 0`.
2. Repeat while there are still bits left in either string **or** a carry is left (`i >= 0 || j >= 0 || carry > 0`):
   - Take `bit1` from `a` if `i >= 0`, otherwise use `0`. Do the same for `bit2` from `b`. The conversion `charAt(i) - '0'` turns the character `'0'` or `'1'` into the number `0` or `1`.
   - Calculate `sum = bit1 + bit2 + carry`.
   - The digit to write is `sum % 2`. Add it to the **front** of `result`.
   - The new carry is `sum / 2`.
3. Return `result`.

The `carry > 0` part of the condition handles the case where the answer is **one digit longer** than both inputs (for example `"1" + "1" = "10"`).

### Dry Run

`a = "11"`, `b = "1"`

| Step | bit1 | bit2 | carry in | sum | digit (`sum % 2`) | carry out (`sum / 2`) | result |
|------|------|------|----------|-----|-------------------|-----------------------|--------|
| 1 | 1 | 1 | 0 | 2 | 0 | 1 | `"0"` |
| 2 | 1 | 0 (b is finished) | 1 | 2 | 0 | 1 | `"00"` |
| 3 | 0 (a is finished) | 0 (b is finished) | 1 | 1 | 1 | 0 | `"100"` |

Both strings are finished and the carry is `0`, so the loop stops.

Output: `"100"`

---

## Solution (Java)

```java
class Solution {
    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        String result = "";

        // continue while any bits or a carry are left
        while (i >= 0 || j >= 0 || carry > 0) {
            int bit1 = 0;
            int bit2 = 0;

            if (i >= 0) {
                bit1 = a.charAt(i) - '0';
                i--;
            }

            if (j >= 0) {
                bit2 = b.charAt(j) - '0';
                j--;
            }

            int sum = bit1 + bit2 + carry;

            // digit to write goes at the front, carry moves to the next bit
            result = (sum % 2) + result;
            carry = sum / 2;
        }

        return result;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n^2)** | The loop runs about `n` times (n is the longer length), but each `(sum % 2) + result` creates a new `String` and copies the old one, which costs O(n) each time |
| Space | **O(n)** | The result string has up to `n + 1` characters |

---

## Key Takeaways

- Binary addition is school-style addition in base 2: `digit = sum % 2` and `carry = sum / 2`.
- `charAt(i) - '0'` is the standard way to convert a digit character into an `int`.
- Using `0` for a string that has finished lets strings of different lengths be handled in the same loop.
- The `carry > 0` condition adds the extra leading `1` when needed (like `"1" + "1"`).
- Strings are immutable in Java, so adding a digit to the front each time copies the whole string. Using a `StringBuilder` (append, then reverse at the end) would make the time O(n).
