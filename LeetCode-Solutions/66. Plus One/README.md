# [66. Plus One](https://leetcode.com/problems/plus-one/)

## Problem Statement

You are given a **large integer** represented as an integer array `digits`, where each `digits[i]` is the `i`th digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading `0`'s.

Increment the large integer by one and return the resulting array of digits.

### Examples

```
Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Incrementing by one gives 123 + 1 = 124.
Thus, the result should be [1,2,4].
```

```
Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
Incrementing by one gives 4321 + 1 = 4322.
Thus, the result should be [4,3,2,2].
```

```
Input: digits = [9]
Output: [1,0]
Explanation: The array represents the integer 9.
Incrementing by one gives 9 + 1 = 10.
Thus, the result should be [1,0].
```

### Constraints

- `1 <= digits.length <= 100`
- `0 <= digits[i] <= 9`
- `digits` does not contain any leading `0`'s.

---

## Approach: Add From the Last Digit and Handle the Carry

Adding one to a number works the same way as in school: start from the **last digit** and move left while there is a carry.

1. Loop `i` from the **last index to the first**.
2. If `digits[i] < 9`, simply add `1` to it. No carry is left, so **return the array right away**.
3. If `digits[i] == 9`, adding 1 makes it `10`. So set `digits[i] = 0` and **carry 1** to the next digit on the left (the loop continues).
4. If the loop finishes, **every digit was 9** (like `999`), which becomes `000` with a carry left. In that case create a **new array one size bigger**, with `1` at the front and `0`s in the rest. Java fills the new array with `0` by default, so only `result[0] = 1` is needed.

### Dry Run

`digits = [1, 2, 3]`

| i | digits[i] | Action | digits |
|---|-----------|--------|--------|
| 2 | 3 | `3 < 9`, increment and return | `[1, 2, 4]` |

Output: `[1, 2, 4]`

`digits = [1, 9, 9]` (carry moves left)

| i | digits[i] | Action | digits |
|---|-----------|--------|--------|
| 2 | 9 | not `< 9`, set to `0`, carry | `[1, 9, 0]` |
| 1 | 9 | not `< 9`, set to `0`, carry | `[1, 0, 0]` |
| 0 | 1 | `1 < 9`, increment and return | `[2, 0, 0]` |

Output: `[2, 0, 0]`

`digits = [9, 9]` (all nines)

| i | digits[i] | Action | digits |
|---|-----------|--------|--------|
| 1 | 9 | set to `0`, carry | `[9, 0]` |
| 0 | 9 | set to `0`, carry | `[0, 0]` |

The loop ends without returning, so a new array of size `3` is created with `result[0] = 1`. Output: `[1, 0, 0]`

---

## Solution (Java)

```java
class Solution {
    public int[] plusOne(int[] digits) {

        // start from the last digit
        for (int i = digits.length - 1; i >= 0; i--) {

            // no carry needed, just add 1 and return
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            // digit is 9: it becomes 0 and the carry moves left
            digits[i] = 0;
        }

        // all digits were 9 (e.g. 999 -> 1000)
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | In the worst case, every digit is visited once |
| Space | **O(1)** | The input array is updated in-place; a new array is created only when all digits are 9 (then O(n)) |

---

## Key Takeaways

- Adding one only changes the **trailing 9s** and the first digit that is less than 9.
- Returning early as soon as a digit is below 9 saves unnecessary work.
- The only time the array grows is when **all digits are 9**, and then the answer is `1` followed by zeros.
- No conversion to `int` or `long` is needed, which matters because the number can have up to 100 digits and would overflow normal integer types.
