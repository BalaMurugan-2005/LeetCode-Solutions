# [35. Search Insert Position](https://leetcode.com/problems/search-insert-position/)

## Problem Statement

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with `O(log n)` runtime complexity.

### Examples

```
Input: nums = [1,3,5,6], target = 5
Output: 2
```

```
Input: nums = [1,3,5,6], target = 2
Output: 1
```

```
Input: nums = [1,3,5,6], target = 7
Output: 4
```

### Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` contains **distinct** values sorted in **ascending** order.
- `-10^4 <= target <= 10^4`

---

## Approach: Count the Smaller Elements

The array is sorted and has no duplicates, so the correct position for `target` is simply the **number of elements that are smaller than `target`**.

- If `target` **is in the array**, every element before it is smaller, so the count equals its index.
- If `target` **is not in the array**, the count of smaller elements is exactly the index where it should be inserted to keep the order.

So the solution is:

1. Start with `count = 0`.
2. Go through the array. For every element `nums[i] < target`, increase `count`.
3. Return `count`.

### Dry Run

`nums = [1, 3, 5, 6]`

**target = 5**

| i | nums[i] | nums[i] < 5? | count |
|---|---------|--------------|-------|
| 0 | 1 | yes | 1 |
| 1 | 3 | yes | 2 |
| 2 | 5 | no | 2 |
| 3 | 6 | no | 2 |

Output: `2`

**target = 2**

| i | nums[i] | nums[i] < 2? | count |
|---|---------|--------------|-------|
| 0 | 1 | yes | 1 |
| 1 | 3 | no | 1 |
| 2 | 5 | no | 1 |
| 3 | 6 | no | 1 |

Output: `1`

**target = 7**: all four elements are smaller than `7`, so `count = 4` and the output is `4`.

---

## Solution (Java)

```java
class Solution {
    public int searchInsert(int[] nums, int target) {

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            // count how many elements are smaller than the target
            if (nums[i] < target) {
                count++;
            }
        }

        // this count is the index of the target (or its insert position)
        return count;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | The loop checks every element once |
| Space | **O(1)** | Only one extra variable `count` is used |

---

## Key Takeaways

- In a sorted array of distinct values, the insert position equals the **number of elements smaller than the target**.
- The same formula works whether the target is present or not, so no separate "found" case is needed.
- This solution is linear. The problem statement asks for `O(log n)`, which is what binary search gives.
