# [58. Length of Last Word](https://leetcode.com/problems/length-of-last-word/)

## Problem Statement

Given a string `s` consisting of words and spaces, return the length of the **last** word in the string.

A **word** is a maximal substring consisting of non-space characters only.

### Examples

```
Input: s = "Hello World"
Output: 5
Explanation: The last word is "World" with length 5.
```

```
Input: s = "   fly me   to   the moon  "
Output: 4
Explanation: The last word is "moon" with length 4.
```

```
Input: s = "luffy is still joyboy"
Output: 6
Explanation: The last word is "joyboy" with length 6.
```

### Constraints

- `1 <= s.length <= 10^4`
- `s` consists of only English letters and spaces `' '`.
- There will be at least one word in `s`.

---

## Approach: Scan From the End

The last word is at the end of the string, so instead of splitting the whole string, we start from the **right side** and work backwards. There are two steps:

1. **Skip the trailing spaces.** Start from the last index (`length = s.length() - 1`). While the current character is a space, move left (`length--`). After this loop, `length` points to the **last character of the last word**.
2. **Count the word's characters.** From that position, move left and increase `count` for every non-space character. Stop when a **space** is found (or the start of the string is reached).

Return `count`, which is the length of the last word.

### Dry Run

`s = "   fly me   to   the moon  "`

**Step 1: skip trailing spaces**

The string ends with two spaces. Both are skipped, and `length` stops at the letter `n` of `"moon"`.

**Step 2: count the last word**

| Character checked | Space? | Action | count |
|-------------------|--------|--------|-------|
| `n` | no | count++ | 1 |
| `o` | no | count++ | 2 |
| `o` | no | count++ | 3 |
| `m` | no | count++ | 4 |
| (space before `moon`) | yes | break | 4 |

Output: `4`

For `s = "Hello World"`, there are no trailing spaces, so Step 1 does nothing. Step 2 counts `d, l, r, o, W` and stops when it sees the space, giving `5`.

---

## Solution (Java)

```java
class Solution {
    public int lengthOfLastWord(String s) {

        int count = 0;
        int length = s.length() - 1;

        // Step 1: skip the trailing spaces
        for (int i = length; i >= 0; i--) {
            if (s.charAt(length) == ' ') {
                length--;
            } else {
                break;
            }
        }

        // Step 2: count the characters of the last word
        for (int i = length; i >= 0; i--) {

            if (s.charAt(i) == ' ') {
                break;
            } else {
                count++;
            }
        }

        return count;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | In the worst case, the string is scanned once from the end |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- Scanning from the **end** avoids processing the whole string, because only the last word matters.
- Trailing spaces must be skipped first, otherwise the count would stop immediately at a space.
- Methods like `split(" ")` or `trim()` are not needed, so no extra strings or arrays are created.
- The guarantee that the string has at least one word means `length` never goes below `0` in Step 1.
