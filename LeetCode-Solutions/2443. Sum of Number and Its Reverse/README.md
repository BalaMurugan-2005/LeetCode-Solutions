# [2443. Sum of Number and Its Reverse](https://leetcode.com/problems/sum-of-number-and-its-reverse/)

## Problem Statement

Given a **non-negative** integer `num`, return `true` if `num` can be expressed as the sum of any non-negative integer and its reverse, or `false` otherwise.

### Examples

```
Input: num = 443
Output: true
Explanation: 172 + 271 = 443, where 271 is the reverse of 172. Therefore, we return true.
```

```
Input: num = 63
Output: false
Explanation: 63 cannot be expressed as the sum of a non-negative integer and its reverse. Therefore, we return false.
```

```
Input: num = 181
Output: true
Explanation: 140 + 041 = 181, where 041 represents 41, the reverse of 140.
Reversed numbers may contain leading zeros. Therefore, we return true.
```

### Constraints

- `0 <= num <= 10^5`

---

## Approach: Try Every Candidate Number (Brute Force)

If `num = i + reverse(i)`, then `i` cannot be larger than `num` (since the reverse is never negative). With `num` at most `10^5`, we can simply try every possible `i` and check it.

1. If `num == 0`, the answer is `true`, because `0 + 0 = 0`.
2. Loop `i` from `num` down to `1`. For each `i`:
   - Build its reverse with digit arithmetic: take the last digit with `temp % 10`, add it to `rev` with `rev = rev * 10 + digit`, and remove it with `temp /= 10`.
   - If `i + rev == num`, set `flag = true`.
3. Return `flag`.

The reverse is built from digits, so leading zeros disappear automatically. For example, the reverse of `140` is built as `0`, `4`, `41`, which gives `41`, matching the problem rule that `041` means `41`.

### Dry Run

`num = 181`

The loop tries `i = 181, 180, 179, ...` and checks `i + reverse(i)`:

| i | reverse(i) | i + reverse(i) | Equals 181? |
|---|------------|----------------|-------------|
| 181 | 181 | 362 | no |
| 180 | 81 | 261 | no |
| ... | ... | ... | no |
| 140 | 41 | 181 | **yes** |

`i = 140` works, so `flag` becomes `true` and the output is `true`.

For `num = 63`, none of `i = 63, 62, ..., 1` gives `i + reverse(i) = 63`, so `flag` stays `false`.

---

## Solution (Java)

```java
class Solution {
    public boolean sumOfNumberAndReverse(int num) {
        boolean flag = false;

        // 0 = 0 + 0
        if (num == 0) {
            flag = true;
        }

        // try every number i from num down to 1
        for (int i = num; i >= 1; i--) {
            int rev = 0;
            int temp = i;

            // reverse the digits of i
            while (temp != 0) {
                int digit_num = temp % 10;
                rev = (rev * 10) + digit_num;
                temp /= 10;
            }

            // check if i + reverse(i) gives num
            int result = i + rev;
            if (result == num) {
                flag = true;
            }
        }

        return flag;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n log n)** | Up to `n` candidates are tried (n is `num`), and reversing each one takes about log10(n) steps |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- The number `i` can never be bigger than `num`, so the search range is limited to `1..num`.
- Reversing with `% 10` and `/ 10` handles leading zeros in the reverse automatically (`140` reverses to `41`).
- `num = 0` is a special case that the loop (which starts at `1`) does not cover, so it is handled separately.
- With `num <= 10^5`, checking every candidate is fast enough, so no special math is needed.
