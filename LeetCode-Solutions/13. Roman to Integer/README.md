# [13. Roman to Integer](https://leetcode.com/problems/roman-to-integer/)

## Problem Statement

Roman numerals are represented by seven different symbols: `I`, `V`, `X`, `L`, `C`, `D` and `M`.

| Symbol | Value |
|--------|-------|
| I | 1 |
| V | 5 |
| X | 10 |
| L | 50 |
| C | 100 |
| D | 500 |
| M | 1000 |

For example, `2` is written as `II`, `12` is written as `XII` (`X + II`), and `27` is written as `XXVII` (`XX + V + II`).

Roman numerals are usually written largest to smallest from left to right. However, four is not `IIII`; it is written as `IV`, because the one is before the five so we subtract it. The same applies to nine, written as `IX`. There are six instances where subtraction is used:

- `I` can be placed before `V` (5) and `X` (10) to make 4 and 9.
- `X` can be placed before `L` (50) and `C` (100) to make 40 and 90.
- `C` can be placed before `D` (500) and `M` (1000) to make 400 and 900.

Given a roman numeral, convert it to an integer.

### Examples

```
Input: s = "III"
Output: 3
Explanation: III = 3.
```

```
Input: s = "LVIII"
Output: 58
Explanation: L = 50, V = 5, III = 3.
```

```
Input: s = "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
```

### Constraints

- `1 <= s.length <= 15`
- `s` contains only the characters `'I'`, `'V'`, `'X'`, `'L'`, `'C'`, `'D'`, `'M'`.
- It is guaranteed that `s` is a valid roman numeral in the range `[1, 3999]`.

---

## Approach: Convert to Values, then Compare Neighbours

Normally Roman numerals are added left to right. The only exception is the subtraction case (like `IV`, `IX`, `XC`), where a **smaller value comes before a larger one**.

1. **Convert each character to its number** using a `switch` and store the values in an array `nums`. For `"MCMXCIV"` this gives `[1000, 100, 1000, 10, 100, 1, 5]`.
2. **Loop from left to right** (up to the second last element) and compare each value with the next one:
   - If `nums[i] < nums[i+1]`, this is a subtraction case, so **subtract** `nums[i]` from the sum.
   - Otherwise, **add** `nums[i]` to the sum.
3. The **last value is always added**, because there is nothing after it to compare with.

### Dry Run

`s = "MCMXCIV"` gives `nums = [1000, 100, 1000, 10, 100, 1, 5]`

| i | nums[i] | nums[i+1] | Action | sum |
|---|---------|-----------|--------|-----|
| 0 | 1000 | 100 | 1000 >= 100, add | 1000 |
| 1 | 100 | 1000 | 100 < 1000, subtract | 900 |
| 2 | 1000 | 10 | add | 1900 |
| 3 | 10 | 100 | 10 < 100, subtract | 1890 |
| 4 | 100 | 1 | add | 1990 |
| 5 | 1 | 5 | 1 < 5, subtract | 1989 |

Add the last value `5`: `1989 + 5 = 1994`

---

## Solution (Java)

```java
class Solution {
    public int romanToInt(String s) {

        // Step 1: convert each roman character to its integer value
        int nums[] = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
            }
        }

        // Step 2: add or subtract based on the next value
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1])
                sum -= nums[i];   // subtraction case like IV, IX, XC
            else
                sum += nums[i];
        }

        // Step 3: the last value is always added
        return sum + nums[nums.length - 1];
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | One pass to convert and one pass to calculate, where n is the length of the string |
| Space | **O(n)** | The `nums` array stores one value per character |

---

## Key Takeaways

- The only special rule is: **if a symbol is smaller than the one after it, subtract it; otherwise add it.**
- This rule handles all six subtraction cases (`IV`, `IX`, `XL`, `XC`, `CD`, `CM`) without checking each one separately.
- The last character is always added, so the loop runs only up to `length - 1`.
