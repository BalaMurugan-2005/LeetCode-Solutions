# [27. Remove Element](https://leetcode.com/problems/remove-element/)

## Problem Statement

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` in `nums` **in-place**. The order of the elements may be changed. Then return the number of elements in `nums` which are not equal to `val`.

Consider the number of elements in `nums` which are not equal to `val` to be `k`. To get accepted, you need to do the following:

- Change the array `nums` such that the first `k` elements of `nums` contain the elements which are not equal to `val`. The remaining elements of `nums` are not important, and neither is the size of `nums`.
- Return `k`.

### Custom Judge

The judge will test your solution with the following code:

```
int[] nums = [...]; // Input array
int val = ...; // Value to remove
int[] expectedNums = [...]; // The expected answer with correct length.
                            // It is sorted with no values equaling val.

int k = removeElement(nums, val); // Calls your implementation

assert k == expectedNums.length;
sort(nums, 0, k); // Sort the first k elements of nums
for (int i = 0; i < actualLength; i++) {
    assert nums[i] == expectedNums[i];
}
```

If all assertions pass, then your solution will be accepted.

### Examples

```
Input: nums = [3,2,2,3], val = 3
Output: 2, nums = [2,2,_,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 2.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

```
Input: nums = [0,1,2,2,3,0,4,2], val = 2
Output: 5, nums = [0,1,4,0,3,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums containing 0, 0, 1, 3, and 4.
Note that the five elements can be returned in any order.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

### Constraints

- `0 <= nums.length <= 100`
- `0 <= nums[i] <= 50`
- `0 <= val <= 100`

---

## Approach: Two Pointers (Reader and Writer)

We need all elements that are **not equal to `val`** to be at the front of the array. So we scan the array once and copy every "good" element to the next free position at the front.

1. Keep a variable `count` that works as the **write position** and also counts the elements that are not equal to `val`. Start with `count = 0`.
2. Loop `i` through the whole array (this is the **reader**):
   - If `nums[i] != val`, copy it to `nums[count]` and increase `count`.
   - If `nums[i] == val`, skip it.
3. Return `count`. The first `count` elements of `nums` now contain all the values that are not `val`.

The elements after index `count - 1` do not matter, so there is no need to clear them.

### Dry Run

`nums = [3, 2, 2, 3]`, `val = 3`, start with `count = 0`

| i | nums[i] | Equal to val? | Action | nums | count |
|---|---------|---------------|--------|------|-------|
| 0 | 3 | yes | skip | `[3, 2, 2, 3]` | 0 |
| 1 | 2 | no | `nums[0] = 2`, `count++` | `[2, 2, 2, 3]` | 1 |
| 2 | 2 | no | `nums[1] = 2`, `count++` | `[2, 2, 2, 3]` | 2 |
| 3 | 3 | yes | skip | `[2, 2, 2, 3]` | 2 |

Return `count = 2`. The first two elements are `[2, 2]`, and the rest is ignored.

For `nums = [0,1,2,2,3,0,4,2]` and `val = 2`, the elements `0, 1, 3, 0, 4` are copied to the front in order, so `count = 5`.

---

## Solution (Java)

```java
class Solution {
    public int removeElement(int[] nums, int val) {

        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            // keep the element only if it is not the value to remove
            if (nums[i] != val) {
                nums[count++] = nums[i];
            }
        }

        // number of elements that are not equal to val
        return count;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Single pass through the array |
| Space | **O(1)** | Done in-place with only one extra variable `count` |

---

## Key Takeaways

- `count` plays two roles: the **number of kept elements** and the **next write position**.
- Only the first `k` elements matter, so unwanted values are simply overwritten or left behind.
- The same reader/writer two-pointer pattern is used in Move Zeroes and Remove Duplicates from Sorted Array.
- Empty arrays are handled automatically, because the loop does not run and `0` is returned.
