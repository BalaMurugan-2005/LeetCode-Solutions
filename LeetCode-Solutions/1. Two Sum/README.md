# [1. Two Sum](https://leetcode.com/problems/two-sum/)

## Problem Statement

You are given an array of integers `nums` and an integer `target`. Return the **indices** of the two numbers such that they add up to `target`.

You may assume that each input would have **exactly one solution**, and you may not use the same element twice.

You can return the answer in any order.

### Examples

```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: nums[0] + nums[1] = 2 + 7 = 9, so the indices [0, 1] are returned.
```

```
Input: nums = [3,2,4], target = 6
Output: [1,2]
Explanation: nums[1] + nums[2] = 2 + 4 = 6, so the indices [1, 2] are returned.
```

```
Input: nums = [3,3], target = 6
Output: [0,1]
Explanation: nums[0] + nums[1] = 3 + 3 = 6, so the indices [0, 1] are returned.
```

### Constraints

- `2 <= nums.length <= 10^4`
- `-10^9 <= nums[i] <= 10^9`
- `-10^9 <= target <= 10^9`
- Only one valid answer exists.

---

## Approach: Sort with Index Tracking + Two Pointers

The two-pointer technique needs a **sorted** array, but sorting moves the numbers, so their **original indices** would be lost. To fix this, the solution keeps a second array `index[]` that moves together with `nums[]`.

1. **Create an index array:** `index[i] = i` stores the original position of every number.
2. **Sort `nums` using Bubble Sort.** Whenever two numbers are swapped in `nums`, the same two positions are swapped in `index` too. This way `index[k]` always tells the original position of `nums[k]`.
3. **Use two pointers** on the sorted array: `left = 0` and `right = n - 1`.
   - If `nums[left] + nums[right] == target`, return `index[left]` and `index[right]` (the original indices).
   - If the sum is **less** than the target, move `left` forward to get a bigger sum.
   - If the sum is **greater** than the target, move `right` backward to get a smaller sum.
4. If no pair is found, return `{-1, -1}` (this never happens, because a solution is guaranteed).

### Dry Run

`nums = [3, 2, 4]`, `target = 6`

**Step 1 and 2: sort while tracking indices**

| Stage | nums | index |
|-------|------|-------|
| Start | `[3, 2, 4]` | `[0, 1, 2]` |
| Swap `3` and `2` | `[2, 3, 4]` | `[1, 0, 2]` |
| `3 < 4`, no swap | `[2, 3, 4]` | `[1, 0, 2]` |

**Step 3: two pointers**

| left | right | nums[left] + nums[right] | Action |
|------|-------|--------------------------|--------|
| 0 | 2 | `2 + 4 = 6` | equals target, return `index[0]` and `index[2]` |

`index[0] = 1` and `index[2] = 2`, so the output is `[1, 2]`.

For `nums = [2, 7, 11, 15]` and `target = 9`, the array is already sorted. The pointers check `2 + 15 = 17` (too big, `right--`), then `2 + 11 = 13` (too big, `right--`), then `2 + 7 = 9` and return `[0, 1]`.

---

## Solution (Java)

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {

        int n = nums.length;

        // stores the original index of every number
        int[] index = new int[n];

        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        // Bubble sort: swap numbers and their indices together
        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - 1 - i; j++) {

                if (nums[j] > nums[j + 1]) {

                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;

                    temp = index[j];
                    index[j] = index[j + 1];
                    index[j + 1] = temp;
                }
            }
        }

        // Two pointers on the sorted array
        int left = 0;
        int right = n - 1;

        while (left < right) {

            int sum = nums[left] + nums[right];

            if (sum == target) {
                // return the ORIGINAL indices
                return new int[] {
                    index[left],
                    index[right]
                };
            }

            if (sum < target) {
                left++;
            }
            else {
                right--;
            }
        }

        return new int[] {-1, -1};
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n^2)** | Bubble sort takes O(n^2); the two-pointer search after it is only O(n) |
| Space | **O(n)** | The extra `index` array stores one value per element |

---

## Key Takeaways

- Two pointers work on a **sorted** array: a small sum moves `left` up, a large sum moves `right` down.
- Sorting loses the original positions, so the solution carries an **index array** and swaps it together with `nums`.
- Always return `index[left]` and `index[right]`, not `left` and `right`, because the question asks for the original indices.
- Note that this solution **modifies the input array** `nums` by sorting it in place.
