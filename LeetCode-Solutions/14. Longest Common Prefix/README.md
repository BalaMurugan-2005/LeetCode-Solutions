# [14. Longest Common Prefix](https://leetcode.com/problems/longest-common-prefix/)

## Problem Statement

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

### Examples

```
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

### Constraints

- `1 <= strs.length <= 200`
- `0 <= strs[i].length <= 200`
- `strs[i]` consists of only lowercase English letters if it is non-empty.

---

## Approach: Horizontal Scanning (Shrink the Prefix)

The common prefix of all strings can never be longer than the first string, so we start with the first string as our guess and shrink it whenever another string does not match.

1. Set `prefix = arr[0]` (the first string).
2. For every next string `arr[i]`, check if it **starts with** `prefix`.
3. If it does not, **remove the last character** of `prefix` and check again. Repeat until `arr[i]` starts with `prefix`.
4. Move to the next string and repeat.
5. After all strings are checked, `prefix` is the longest common prefix.

If nothing matches, `prefix` keeps shrinking until it becomes `""`. Every string starts with an empty string, so the `while` loop always stops.

### Dry Run

`strs = ["flower", "flow", "flight"]`, start with `prefix = "flower"`

| Comparing with | Does it start with prefix? | Action | prefix |
|----------------|----------------------------|--------|--------|
| `"flow"` | `"flower"` no | remove last char | `"flowe"` |
| `"flow"` | `"flowe"` no | remove last char | `"flow"` |
| `"flow"` | `"flow"` yes | go to next string | `"flow"` |
| `"flight"` | `"flow"` no | remove last char | `"flo"` |
| `"flight"` | `"flo"` no | remove last char | `"fl"` |
| `"flight"` | `"fl"` yes | done | `"fl"` |

Output: `"fl"`

`strs = ["dog", "racecar", "car"]`: the prefix `"dog"` shrinks to `"do"`, then `"d"`, then `""` while checking `"racecar"`. An empty prefix matches everything, so the output is `""`.

---

## Solution (Java)

```java
class Solution {
    public String longestCommonPrefix(String[] arr) {
        // assume the first string is the common prefix
        String prefix = arr[0];

        for (int i = 1; i < arr.length; i++) {

            // shrink the prefix until the current string starts with it
            while (!arr[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }

        return prefix;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(S)** | S is the total number of characters in all strings; in the worst case every character is compared once |
| Space | **O(1)** | Only the `prefix` variable is used (no extra data structures) |

---

## Key Takeaways

- The answer can never be longer than the shortest string, so starting from the first string and shrinking is safe.
- `startsWith()` makes the prefix check simple and readable.
- An empty prefix `""` always matches, so the `while` loop can never run forever.
- The case where there is no common prefix is handled naturally without any extra check.
