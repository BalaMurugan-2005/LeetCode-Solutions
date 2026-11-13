# [15. 3Sum](https://leetcode.com/problems/3sum/)

## Problem Statement

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must **not contain duplicate triplets**.

### Examples

```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation:
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
Notice that the order of the output and the order of the triplets does not matter.
```

```
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum up to 0.
```

```
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums up to 0.
```

### Constraints

- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

---

## Approach: Sort + Fix One Number + Two Pointers

Checking every triplet would take O(n^3). Sorting the array first lets us reduce the problem to a **two-pointer search** for each fixed number.

1. **Sort** the array. Sorting makes duplicates sit together and lets the two pointers move in a meaningful direction.
2. **Fix the first number** `nums[i]` and search for two more numbers on its right whose sum is `-nums[i]`:
   - `left = i + 1` and `right = n - 1`.
   - Calculate `sum = nums[i] + nums[left] + nums[right]`.
   - If `sum == 0`, save the triplet and move both pointers inward.
   - If `sum < 0`, the sum is too small, so move `left` forward to get a bigger number.
   - If `sum > 0`, the sum is too big, so move `right` backward to get a smaller number.
3. **Avoid duplicate triplets:**
   - Skip `nums[i]` if it equals the previous number (`i > 0 && nums[i] == nums[i - 1]`).
   - After finding a triplet, skip repeated values on the `left` and `right` sides.
4. **Early stop:** if `nums[i] > 0`, all the remaining numbers are positive too (the array is sorted), so no sum can reach `0`. The loop can break.

### Dry Run

`nums = [-1, 0, 1, 2, -1, -4]`

After sorting: `[-4, -1, -1, 0, 1, 2]`

| i | nums[i] | Action | Triplets found |
|---|---------|--------|----------------|
| 0 | -4 | `left` moves right because every sum (`-3`, `-3`, `-2`, `-1`) is below `0`, until the pointers meet | none |
| 1 | -1 | `-1 + -1 + 2 = 0`, save the triplet, move both pointers. Then `-1 + 0 + 1 = 0`, save the triplet | `[-1,-1,2]`, `[-1,0,1]` |
| 2 | -1 | same as the previous number, skip | none |
| 3 | 0 | `0 + 1 + 2 = 3`, too big, so `right` moves left until the pointers meet | none |

Output: `[[-1,-1,2],[-1,0,1]]`

---

## Solution (Java)

```java
import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {

            // Skip duplicate first elements
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Remaining numbers cannot make sum zero
            if (nums[i] > 0) {
                break;
            }

            int left = i + 1;
            int right = n - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {

                    result.add(Arrays.asList(
                        nums[i], nums[left], nums[right]
                    ));

                    left++;
                    right--;

                    // Skip duplicate left values
                    while (left < right &&
                           nums[left] == nums[left - 1]) {
                        left++;
                    }

                    // Skip duplicate right values
                    while (left < right &&
                           nums[right] == nums[right + 1]) {
                        right--;
                    }

                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n^2)** | Sorting takes O(n log n), and for each of the `n` fixed numbers the two pointers scan the rest of the array once, which is O(n) |
| Space | **O(1)** | No extra data structures besides the output list (sorting space is ignored) |

---

## Key Takeaways

- **Sort first, then use two pointers.** This turns a 3-nested-loop O(n^3) problem into O(n^2).
- Duplicates are handled by skipping repeated values for the first number and, after a match, for both pointers. No `Set` is needed.
- Because the array is sorted, a first number greater than `0` can never lead to a zero sum, so the loop can stop early.
- The same two-pointer idea is used in the Two Sum solution on a sorted array.
