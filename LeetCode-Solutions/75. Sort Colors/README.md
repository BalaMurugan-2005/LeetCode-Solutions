# [75. Sort Colors](https://leetcode.com/problems/sort-colors/)

## Problem Statement

You are given an array `nums` with `n` objects colored red, white, or blue, sort them **in-place** so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers `0`, `1`, and `2` to represent the color red, white, and blue, respectively.

You must solve this problem without using the library's sort function.

### Examples

```
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
Explanation:
The array has two 0s, two 1s, and two 2s. Sorting them in-place places all 0s first, then all 1s, then all 2s.
```

```
Input: nums = [2,0,1]
Output: [0,1,2]
Explanation:
The array has one each of 0, 1, and 2, arranged in-place in the order 0, 1, 2.
```

### Constraints

- `n == nums.length`
- `1 <= n <= 300`
- `nums[i]` is either `0`, `1`, or `2`.

**Follow up:** Could you come up with a one-pass algorithm using only constant extra space?

---

## Approach: Dutch National Flag (Three Pointers)

Since there are only three values, the array can be sorted in a **single pass** by splitting it into four regions using three pointers: `low`, `mid` and `high`.

| Region | Contains |
|--------|----------|
| `[0 .. low - 1]` | all `0`s (sorted) |
| `[low .. mid - 1]` | all `1`s (sorted) |
| `[mid .. high]` | not checked yet |
| `[high + 1 .. n - 1]` | all `2`s (sorted) |

Start with `low = 0`, `mid = 0` and `high = n - 1`, and look at `nums[mid]` while `mid <= high`:

1. **`nums[mid] == 0`:** swap it with `nums[low]` and move both `low` and `mid` forward. The value that comes back from `low` is always `1` (or the same element), so it is safe to move `mid`.
2. **`nums[mid] == 1`:** it is already in the correct middle region, so just move `mid` forward.
3. **`nums[mid] == 2`:** swap it with `nums[high]` and move `high` backward. `mid` stays in place, because the value that was swapped in from `high` has not been checked yet.

When `mid` passes `high`, every element has been placed and the array is sorted.

### Dry Run

`nums = [2, 0, 2, 1, 1, 0]`, start with `low = 0`, `mid = 0`, `high = 5`

| Step | nums[mid] | Action | nums | low | mid | high |
|------|-----------|--------|------|-----|-----|------|
| 1 | 2 | swap with `nums[high]`, `high--` | `[0, 0, 2, 1, 1, 2]` | 0 | 0 | 4 |
| 2 | 0 | swap with `nums[low]`, `low++`, `mid++` | `[0, 0, 2, 1, 1, 2]` | 1 | 1 | 4 |
| 3 | 0 | swap with `nums[low]`, `low++`, `mid++` | `[0, 0, 2, 1, 1, 2]` | 2 | 2 | 4 |
| 4 | 2 | swap with `nums[high]`, `high--` | `[0, 0, 1, 1, 2, 2]` | 2 | 2 | 3 |
| 5 | 1 | `mid++` | `[0, 0, 1, 1, 2, 2]` | 2 | 3 | 3 |
| 6 | 1 | `mid++` | `[0, 0, 1, 1, 2, 2]` | 2 | 4 | 3 |

Now `mid (4) > high (3)`, so the loop stops.

Output: `[0, 0, 1, 1, 2, 2]`

---

## Solution (Java)

```java
class Solution {
    public void sortColors(int[] nums) {

        int low = 0;                  // next position for a 0
        int mid = 0;                  // current element being checked
        int high = nums.length - 1;   // next position for a 2 (from the right)

        while (mid <= high) {

            if (nums[mid] == 0) {
                // move 0 to the front region
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;

                low++;
                mid++;
            }

            else if (nums[mid] == 1) {
                // 1 is already in the right region
                mid++;
            }

            else {
                // move 2 to the back region (do not move mid yet)
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;

                high--;
            }
        }
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Single pass; every step moves either `mid` forward or `high` backward |
| Space | **O(1)** | In-place with only three pointers |

---

## Key Takeaways

- This is the **Dutch National Flag** algorithm. It sorts three distinct values in one pass with constant extra space, so it answers the follow-up.
- After swapping with `high`, `mid` must **not** move, because the element that came from the right has not been checked yet.
- After swapping with `low`, `mid` can move, because what came back from `low` is already known to be a `1` (or the same element).
- The loop condition `mid <= high` makes sure the unchecked region is fully processed.
