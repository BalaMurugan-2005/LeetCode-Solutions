# [283. Move Zeroes](https://leetcode.com/problems/move-zeroes/)

## Problem Statement

Given an integer array `nums`, move all `0`'s to the end of it while maintaining the **relative order** of the non-zero elements.

> You must do this **in-place** without making a copy of the array.

### Examples

```
Input:  nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
```

```
Input:  nums = [0]
Output: [0]
```

---

## Approach: Two Pointers (Swap)

The idea is to keep a pointer `pos` that always marks the **position where the next non-zero element should go**. Everything before `pos` is already a cleaned-up, non-zero prefix.

1. Start with `pos = 0`.
2. Scan the array with `i` from left to right.
3. When `arr[i]` is **non-zero**, swap it with `arr[pos]`, then move `pos` forward by one.
4. When `arr[i]` is **zero**, do nothing and just move `i` ahead.

Because we only ever swap a non-zero value into the front part, the **order of non-zero elements never changes**, and zeros naturally get pushed to the back.

### Dry Run

`nums = [0, 1, 0, 3, 12]`

| i | arr[i] | Action | Array after step | pos |
|---|--------|--------|------------------|-----|
| 0 | 0 | Zero, skip | `[0, 1, 0, 3, 12]` | 0 |
| 1 | 1 | Swap with `arr[0]` | `[1, 0, 0, 3, 12]` | 1 |
| 2 | 0 | Zero, skip | `[1, 0, 0, 3, 12]` | 1 |
| 3 | 3 | Swap with `arr[1]` | `[1, 3, 0, 0, 12]` | 2 |
| 4 | 12 | Swap with `arr[2]` | `[1, 3, 12, 0, 0]` | 3 |

Final result: `[1, 3, 12, 0, 0]`

---

## Solution (Java)

```java
class Solution {
    public void moveZeroes(int[] arr) {
        int pos = 0; // next index where a non-zero element should be placed

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                // swap current non-zero element to the 'pos' index
                int temp = arr[i];
                arr[i] = arr[pos];
                arr[pos] = temp;
                pos++;
            }
        }

        // Only for local testing: print the final array
        for (int data : arr) {
            System.out.println(data);
        }
    }
}
```

> **Note:** The printing loop at the end is only for local testing. LeetCode checks the modified array directly, so you can remove it when submitting.

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Single pass through the array |
| Space | **O(1)** | In-place, only a few extra variables |

---

## Key Takeaways

- The **two-pointer** pattern (one reader `i`, one writer `pos`) is useful for many in-place array problems.
- Moving only non-zero elements in one direction keeps their **relative order stable**.
- Edge cases handled: a single element (`[0]`), all zeros, and no zeros at all.
