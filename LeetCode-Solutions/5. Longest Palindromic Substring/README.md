# [5. Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)

## Problem Statement

Given a string `s`, return the **longest palindromic substring** in `s`.

### Examples

```
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.
```

```
Input: s = "cbbd"
Output: "bb"
```

### Constraints

- `1 <= s.length <= 1000`
- `s` consists of only digits and English letters.

---

## Approach: Expand Around the Center

Every palindrome has a **center** and is mirrored on both sides of it. Instead of checking every substring, we pick each possible center and **expand outwards** while the characters on both sides match.

There are two kinds of centers:

- **Odd length** palindromes have a single character as the center (like `"aba"`), so `left = i` and `right = i`.
- **Even length** palindromes have a center between two characters (like `"abba"`), so `left = i` and `right = i + 1`.

The steps:

1. Keep two variables: `start` (the start index of the best palindrome so far) and `maxLength` (its length). Begin with `start = 0` and `maxLength = 1`, because a single character is always a palindrome.
2. For every index `i`:
   - **Odd case:** set `left = i`, `right = i`. While `left >= 0`, `right < s.length()` and `s.charAt(left) == s.charAt(right)`, check the current length `right - left + 1`. If it is larger than `maxLength`, update `start` and `maxLength`. Then move `left--` and `right++`.
   - **Even case:** do the same with `left = i`, `right = i + 1`.
3. Return `s.substring(start, start + maxLength)`.

The `>` comparison keeps the **first** palindrome found when two have the same length.

### Dry Run

`s = "babad"`

| Center i | Case | Expansion | Palindrome found | maxLength | start |
|----------|------|-----------|------------------|-----------|-------|
| 0 (`b`) | odd | `b`, then `left` goes out of range | `b` (length 1, not larger) | 1 | 0 |
| 0 | even | `b` vs `a`, not equal | none | 1 | 0 |
| 1 (`a`) | odd | `a`, then `b == b` | `bab` (length 3) | 3 | 0 |
| 1 | even | `a` vs `b`, not equal | none | 3 | 0 |
| 2 (`b`) | odd | `b`, then `a == a` gives `aba` (length 3, not larger), then `b` vs `d` stops | none better | 3 | 0 |
| 2 | even | `b` vs `a`, not equal | none | 3 | 0 |
| 3 (`a`) | odd / even | `a`, then `a` vs `d` and `d` vs ... stop | none better | 3 | 0 |
| 4 (`d`) | odd / even | no larger palindrome | none better | 3 | 0 |

Output: `s.substring(0, 3) = "bab"`

For `s = "cbbd"`, the even case at `i = 1` compares `b == b`, which gives `"bb"` (length 2), and then `c` vs `d` stops. The output is `"bb"`.

---

## Solution (Java)

```java
class Solution {

    public String longestPalindrome(String s) {

        int start = 0;
        int maxLength = 1;

        for (int i = 0; i < s.length(); i++) {

            // odd length palindromes: center is one character
            int left = i;
            int right = i;

            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {

                if (right - left + 1 > maxLength) {
                    start = left;
                    maxLength = right - left + 1;
                }

                left--;
                right++;
            }

            // even length palindromes: center is between two characters
            left = i;
            right = i + 1;

            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {

                if (right - left + 1 > maxLength) {
                    start = left;
                    maxLength = right - left + 1;
                }

                left--;
                right++;
            }
        }

        return s.substring(start, start + maxLength);
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n^2)** | There are about `2n` centers, and each expansion can take up to O(n) steps |
| Space | **O(1)** | Only a few integer variables are used; no extra table is created |

---

## Key Takeaways

- A palindrome is symmetric around its center, so expanding from each center finds all palindromes.
- Both **odd** (single-character center) and **even** (between two characters) centers must be checked, otherwise cases like `"bb"` are missed.
- Only `start` and `maxLength` are stored, and the substring is created once at the end.
- Compared to checking every substring (O(n^3)), this approach is much faster and needs no extra memory.
