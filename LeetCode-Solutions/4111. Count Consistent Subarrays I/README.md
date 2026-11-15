# [4111. Count Consistent Subarrays I](https://leetcode.com/problems/count-consistent-subarrays-i/)

## Problem Statement

You are given an integer array `nums`.

A subarray is called **consistent** if the bitwise AND of all its elements is equal to the **minimum** value of the subarray.

Return the number of consistent subarrays in `nums`.

A **subarray** is a contiguous, non-empty sequence of elements within an array.

### Examples

```
Input: nums = [5,3,1,2]
Output: 6
Explanation: The following subarrays are consistent:
```

| Subarray | Values | Bitwise AND | Minimum Value |
|----------|--------|-------------|---------------|
| nums[0..0] | [5] | 5 | 5 |
| nums[1..1] | [3] | 3 | 3 |
| nums[2..2] | [1] | 1 | 1 |
| nums[3..3] | [2] | 2 | 2 |
| nums[1..2] | [3, 1] | 3 AND 1 = 1 | 1 |
| nums[0..2] | [5, 3, 1] | 5 AND 3 AND 1 = 1 | 1 |

Therefore, the number of consistent subarrays is 6.

```
Input: nums = [1,1,1]
Output: 6
Explanation: For every subarray, both the bitwise AND and the minimum value are equal to 1.
Therefore, the answer is 6.
```

```
Input: nums = [0]
Output: 1
Explanation: The only subarray is [0], whose bitwise AND and minimum value are both 0.
Therefore, the answer is 1.
```

### Constraints

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 100`

---

## Approach: Check Every Subarray With a Running AND and a Running Minimum

The array has at most 100 elements, so every subarray can be checked directly. For each starting index `i`, we extend the subarray one element at a time to the right, and keep two running values:

- `and_val`: the bitwise AND of the subarray so far.
- `min_val`: the minimum of the subarray so far.

1. Start with `count = 0`.
2. For each start index `i`, set `and_val = nums[i]` and `min_val = nums[i]`.
3. For each end index `j` from `i` to the end of the array:
   - Update `and_val = and_val & nums[j]`.
   - Update `min_val = Math.min(min_val, nums[j])`.
   - If `and_val == min_val`, the subarray `nums[i..j]` is consistent, so increase `count`.
4. Return `count`.

Because the values are updated one element at a time, there is no need to recalculate the AND and the minimum from scratch for each subarray.

Every single-element subarray is always consistent, because its AND and its minimum are both the element itself.

### Dry Run

`nums = [5, 3, 1, 2]`

| i | j | Subarray | and_val | min_val | Consistent? | count |
|---|---|----------|---------|---------|-------------|-------|
| 0 | 0 | [5] | 5 | 5 | yes | 1 |
| 0 | 1 | [5, 3] | 1 | 3 | no | 1 |
| 0 | 2 | [5, 3, 1] | 1 | 1 | yes | 2 |
| 0 | 3 | [5, 3, 1, 2] | 0 | 1 | no | 2 |
| 1 | 1 | [3] | 3 | 3 | yes | 3 |
| 1 | 2 | [3, 1] | 1 | 1 | yes | 4 |
| 1 | 3 | [3, 1, 2] | 0 | 1 | no | 4 |
| 2 | 2 | [1] | 1 | 1 | yes | 5 |
| 2 | 3 | [1, 2] | 0 | 1 | no | 5 |
| 3 | 3 | [2] | 2 | 2 | yes | 6 |

Output: `6`

---

## Solution (Java)

```java
class Solution {
    public int countConsistent(int[] nums) {
        int count = 0;

        // i = start of the subarray
        for (int i = 0; i < nums.length; i++) {
            int and_val = nums[i];
            int min_val = nums[i];

            // j = end of the subarray; extend it one element at a time
            for (int j = i; j < nums.length; j++) {
                and_val = and_val & nums[j];
                min_val = Math.min(min_val, nums[j]);

                // consistent subarray found
                if (and_val == min_val) {
                    count++;
                }
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
| Time | **O(n^2)** | Two nested loops go through every start and end position, and each update takes O(1) |
| Space | **O(1)** | Only a few integer variables are used |

---

## Key Takeaways

- Keeping a **running AND** and a **running minimum** avoids recalculating them for every subarray.
- The AND of a group of numbers can never be larger than their minimum, so a subarray is consistent exactly when the AND does not fall below the minimum.
- Every single-element subarray is consistent, so the answer is at least `n`.
- With `n <= 100`, checking all subarrays is fast enough.
