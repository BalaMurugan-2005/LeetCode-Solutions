# [219. Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)

## Problem Statement

Given an integer array `nums` and an integer `k`, return `true` if there are two **distinct indices** `i` and `j` in the array such that `nums[i] == nums[j]` and `abs(i - j) <= k`.

### Examples

```
Input: nums = [1,2,3,1], k = 3
Output: true
```

```
Input: nums = [1,0,1,1], k = 1
Output: true
```

```
Input: nums = [1,2,3,1,2,3], k = 2
Output: false
```

### Constraints

- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `0 <= k <= 10^5`

---

## Approach: Sliding Window + HashSet

We only care about duplicates that are at most `k` positions apart. So when we look at `nums[i]`, we only need to remember the **last `k` elements** before it. A `HashSet` stores exactly these elements and can check if a value exists in O(1) time.

1. Create an empty `HashSet` that acts as a **window** of the most recent elements.
2. Go through the array with index `i`:
   - If `nums[i]` is **already in the set**, the same value appeared within the last `k` positions, so return `true`.
   - Otherwise, **add** `nums[i]` to the set.
   - If the set now has **more than `k` elements**, the window is too big. Remove the oldest element, `nums[i - k]`, so the window slides forward.
3. If the loop ends without finding a match, return `false`.

After each step, the set holds the last `k` elements (`nums[i-k+1]` to `nums[i]`), so the next element is compared only with values that are within distance `k`.

### Dry Run

`nums = [1, 2, 3, 1, 2, 3]`, `k = 2`

| i | nums[i] | In set? | Set after adding | Size > 2? | Remove | Set after step |
|---|---------|---------|------------------|-----------|--------|----------------|
| 0 | 1 | no | {1} | no | - | {1} |
| 1 | 2 | no | {1, 2} | no | - | {1, 2} |
| 2 | 3 | no | {1, 2, 3} | yes | `nums[0] = 1` | {2, 3} |
| 3 | 1 | no (`1` was already removed) | {2, 3, 1} | yes | `nums[1] = 2` | {3, 1} |
| 4 | 2 | no | {3, 1, 2} | yes | `nums[2] = 3` | {1, 2} |
| 5 | 3 | no | {1, 2, 3} | yes | `nums[3] = 1` | {2, 3} |

No match was found, so the output is `false`.

For `nums = [1, 2, 3, 1]` and `k = 3`, nothing is removed before `i = 3`, so `1` is still in the set and the output is `true`.

---

## Solution (Java)

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {

            // same value already seen within the last k positions
            if (set.contains(nums[i])) {
                return true;
            }

            set.add(nums[i]);

            // keep the window size at most k: remove the oldest element
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }

        return false;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | One pass through the array; `contains`, `add` and `remove` on a `HashSet` take O(1) on average |
| Space | **O(min(n, k))** | The set never holds more than `k` elements |

---

## Key Takeaways

- A **sliding window** of size `k` is enough, because only duplicates within distance `k` matter.
- A `HashSet` gives fast O(1) lookups, so there is no need to compare with every earlier element (which would be O(n * k)).
- Removing `nums[i - k]` keeps the window moving forward as `i` increases.
- When `k = 0`, each element is removed right after it is added, so the function returns `false`, which is correct because the two indices must be different.
