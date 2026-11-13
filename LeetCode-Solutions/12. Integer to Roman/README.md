# [12. Integer to Roman](https://leetcode.com/problems/integer-to-roman/)

## Problem Statement

Seven different symbols represent Roman numerals with the following values:

| Symbol | Value |
|--------|-------|
| I | 1 |
| V | 5 |
| X | 10 |
| L | 50 |
| C | 100 |
| D | 500 |
| M | 1000 |

Roman numerals are formed by appending the conversions of decimal place values from highest to lowest. Converting a decimal place value into a Roman numeral has the following rules:

- If the value does not start with 4 or 9, select the symbol of the maximal value that can be subtracted from the input, append that symbol to the result, subtract its value, and convert the remainder to a Roman numeral.
- If the value starts with 4 or 9 use the **subtractive form** representing one symbol subtracted from the following symbol, for example, 4 is 1 (`I`) less than 5 (`V`): `IV` and 9 is 1 (`I`) less than 10 (`X`): `IX`. Only the following subtractive forms are used: 4 (`IV`), 9 (`IX`), 40 (`XL`), 90 (`XC`), 400 (`CD`) and 900 (`CM`).
- Only powers of 10 (`I`, `X`, `C`, `M`) can be appended consecutively at most 3 times to represent multiples of 10. You cannot append 5 (`V`), 50 (`L`), or 500 (`D`) multiple times. If you need to append a symbol 4 times use the **subtractive form**.

Given an integer, convert it to a Roman numeral.

### Examples

```
Input: num = 3749
Output: "MMMDCCXLIX"
Explanation:
3000 = MMM as 1000 (M) + 1000 (M) + 1000 (M)
 700 = DCC as 500 (D) + 100 (C) + 100 (C)
  40 = XL as 10 (X) less of 50 (L)
   9 = IX as 1 (I) less of 10 (X)
Note: 49 is not 1 (I) less of 50 (L) because the conversion is based on decimal places
```

```
Input: num = 58
Output: "LVIII"
Explanation:
50 = L
 8 = VIII
```

```
Input: num = 1994
Output: "MCMXCIV"
Explanation:
1000 = M
 900 = CM
  90 = XC
   4 = IV
```

### Constraints

- `1 <= num <= 3999`

---

## Approach: Greedy With a Value and Symbol Table

The idea is to always take the **largest Roman value that fits** into the remaining number, write its symbol, subtract the value, and repeat.

To handle the subtractive forms (`IV`, `IX`, `XL`, `XC`, `CD`, `CM`) without extra `if` conditions, they are **added to the table as their own entries**. The table is kept in **descending order** using two parallel arrays:

```
values  = 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
symbols =  M,   CM,  D,   CD,   C,  XC,  L,  XL,  X, IX, V, IV, I
```

1. Create a `StringBuilder` called `result`.
2. Go through the table from the largest value to the smallest.
3. For each entry, **while `num >= values[i]`**: append `symbols[i]` to `result` and subtract `values[i]` from `num`.
4. When all entries are done, `num` is `0` and `result` holds the Roman numeral.

Because the table already contains the subtractive pairs, a number like `4` is picked as `IV` directly, and never as `IIII`.

### Dry Run

`num = 1994`

| Table entry | Condition | Action | num after | result |
|-------------|-----------|--------|-----------|--------|
| 1000 (`M`) | `1994 >= 1000` | append `M` | 994 | `M` |
| 1000 (`M`) | `994 < 1000` | move on | 994 | `M` |
| 900 (`CM`) | `994 >= 900` | append `CM` | 94 | `MCM` |
| 500, 400, 100 | `94` is smaller | skip | 94 | `MCM` |
| 90 (`XC`) | `94 >= 90` | append `XC` | 4 | `MCMXC` |
| 50, 40, 10, 9, 5 | `4` is smaller | skip | 4 | `MCMXC` |
| 4 (`IV`) | `4 >= 4` | append `IV` | 0 | `MCMXCIV` |

Output: `"MCMXCIV"`

For `num = 58`: `50` gives `L` (remaining `8`), `5` gives `V` (remaining `3`), and then `I` is appended three times, so the output is `"LVIII"`.

---

## Solution (Java)

```java
class Solution {
    public String intToRoman(int num) {

        // values in descending order, including subtractive forms
        int[] values = {
            1000, 900, 500, 400,
            100, 90, 50, 40,
            10, 9, 5, 4, 1
        };

        // symbol for each value (same order as above)
        String[] symbols = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV", "I"
        };

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {

            // use the current value as many times as it fits
            while (num >= values[i]) {

                result.append(symbols[i]);

                num -= values[i];
            }
        }

        return result.toString();
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(1)** | The table always has 13 entries and `num` is at most `3999`, so the work is bounded by a constant (the result has at most 15 characters) |
| Space | **O(1)** | The two tables have a fixed size and the output length is bounded |

---

## Key Takeaways

- A **greedy** approach works because the table is sorted in descending order and always uses the largest value that fits.
- Adding the six subtractive forms (`CM`, `CD`, `XC`, `XL`, `IX`, `IV`) to the table removes the need for special-case logic.
- Two **parallel arrays** (`values` and `symbols`) keep each value and its symbol at the same index.
- `StringBuilder` is used to append efficiently, instead of creating a new `String` on every step.
- This is the reverse of the Roman to Integer problem.
