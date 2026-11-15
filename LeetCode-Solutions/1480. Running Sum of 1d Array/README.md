# [1480. Running Sum of 1d Array](https://leetcode.com/problems/running-sum-of-1d-array/)

## Problem Statement

Given an array `nums`. We define a running sum of an array as `runningSum[i] = sum(nums[0]…nums[i])`.

Return the running sum of `nums`.

### Examples

```
Input: nums = [1,2,3,4]
Output: [1,3,6,10]
Explanation: Running sum is obtained as follows: [1, 1+2, 1+2+3, 1+2+3+4].
```

```
Input: nums = [1,1,1,1,1]
Output: [1,2,3,4,5]
Explanation: Running sum is obtained as follows: [1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1].
```

```
Input: nums = [3,1,2,10,1]
Output: [3,4,6,16,17]
```

### Constraints

- `1 <= nums.length <= 1000`
- `-10^6 <= nums[i] <= 10^6`

---

## Approach: Keep a Running Total and Overwrite the Array

Each running sum value is just the **previous total plus the current number**. So we only need to remember one number, the total so far, and update the array as we go.

1. Start with `sum = 0`.
2. For each index `i` from left to right:
   - Add `nums[i]` to `sum`.
   - Store `sum` back into `nums[i]`.
3. Return `nums`. It now holds the running sums.

Because each position is read **before** it is overwritten, no information is lost, and no second array is needed.

### Dry Run

`nums = [1, 2, 3, 4]`, start with `sum = 0`

| i | nums[i] (before) | sum after adding | nums after step |
|---|------------------|------------------|-----------------|
| 0 | 1 | 1 | `[1, 2, 3, 4]` |
| 1 | 2 | 3 | `[1, 3, 3, 4]` |
| 2 | 3 | 6 | `[1, 3, 6, 4]` |
| 3 | 4 | 10 | `[1, 3, 6, 10]` |

Output: `[1, 3, 6, 10]`

---

## Solution (Java)

```java
class Solution {
    public int[] runningSum(int[] nums) {
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            // add the current number to the total so far
            sum += nums[i];

            // store the running sum in the same position
            nums[i] = sum;
        }

        return nums;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Single pass through the array |
| Space | **O(1)** | The input array is updated in-place; only one extra variable `sum` is used |

---

## Key Takeaways

- A running sum is the **previous total plus the current element**, so one variable is enough.
- Overwriting `nums[i]` after reading it is safe, so no extra array is needed.
- This solution **modifies the input array**. If the original values are still needed, a new array should be used.
- The largest possible sum is `1000 * 10^6 = 10^9`, which fits in an `int`.
