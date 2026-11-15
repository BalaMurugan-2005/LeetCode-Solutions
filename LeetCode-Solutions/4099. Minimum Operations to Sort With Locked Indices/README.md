# [4099. Minimum Operations to Sort With Locked Indices](https://leetcode.com/problems/minimum-operations-to-sort-with-locked-indices/)

## Problem Statement

You are given an integer array `nums` of length `n`, where `nums` is a permutation of the integers in the range `[1, n]`.

You are also given a binary array `locked` of length `n` and an integer `k`.

- `locked[i] = 1` means index `i` is locked.
- `locked[i] = 0` means index `i` is unlocked.

You may perform the following operations in any order:

- **Swap:** Choose an index `i` such that both indices `i` and `i + 1` are currently unlocked, and swap `nums[i]` and `nums[i + 1]`.
- **Unlock:** Choose an index `i` such that `locked[i] = 1` and permanently unlock it by setting `locked[i] = 0`.

You may perform any number of swap operations but **at most `k`** unlock operations.

Return the **minimum total number of operations** required to sort `nums` in strictly increasing order. If it is impossible, return `-1`.

### Examples

```
Input: nums = [2,1,3,4], locked = [0,1,0,1], k = 1
Output: 2
Explanation:
- Unlock index 1. The array locked becomes [0,0,0,1].
- Swap nums[0] and nums[1]. The array nums becomes [1,2,3,4].
The total number of operations is 1 + 1 = 2.
```

```
Input: nums = [3,1,2], locked = [1,0,1], k = 2
Output: 4
Explanation:
- Unlock indices 0 and 2. All indices are now unlocked.
- Swap indices 0 and 1. The array nums becomes [1,3,2].
- Swap indices 1 and 2. The array nums becomes [1,2,3].
The total number of operations is 2 + 2 = 4.
```

```
Input: nums = [2,3,1], locked = [1,0,0], k = 0
Output: -1
Explanation:
Index 0 is locked, so its value cannot be moved. Since k = 0, no indices can be unlocked.
Therefore, sorting the array is impossible.
```

### Constraints

- `1 <= n == nums.length == locked.length <= 2000`
- `1 <= nums[i] <= n`
- `0 <= locked[i] <= 1`
- `nums` is a permutation of the integers in the range `[1, n]`.
- `0 <= k <= n`

---

## Approach: Count Needed Unlocks + Count Inversions (Fenwick Tree)

The answer is made of two separate parts: **unlock operations** and **swap operations**.

**1. Which locked indices must be unlocked?**

An index is **involved** if it is part of an inversion, which means an earlier element is larger than it, or a later element is smaller than it. To fix an inversion, elements must move across each other, so every involved index has to be unlocked.

- `prefixMax[i]` stores the largest value in `nums[0..i]`.
- `suffixMin[i]` stores the smallest value in `nums[i..n-1]`.
- Index `i` is involved if `prefixMax[i - 1] > nums[i]` (a larger element before it) or `suffixMin[i + 1] < nums[i]` (a smaller element after it).

An index that is **not involved** already holds its final value (everything before it is smaller and everything after it is larger), and nothing needs to cross it. So it can stay locked.

Count the involved indices that are locked as `unlockCount`. If `unlockCount > k`, sorting is impossible, so return `-1`.

**2. How many swaps are needed?**

Once all involved indices are unlocked, every pair that needs swapping has both indices unlocked. The minimum number of adjacent swaps needed to sort an array equals its **number of inversions** (pairs `i < j` with `nums[i] > nums[j]`).

The inversions are counted with a **Binary Indexed Tree (Fenwick tree)**. Go from right to left. For each `x = nums[i]`:

- Query how many already-seen values (the elements to the right of `i`) are **smaller than `x`**. These are the inversions that `x` forms. Add them to `swaps`.
- Add `x` to the tree.

**3. Final answer:** `swaps + unlockCount`.

### Dry Run

**Example 1:** `nums = [2, 1, 3, 4]`, `locked = [0, 1, 0, 1]`, `k = 1`

`prefixMax = [2, 2, 3, 4]`, `suffixMin = [1, 1, 3, 4]`

| i | nums[i] | Larger before? | Smaller after? | Involved? | locked[i] | unlockCount |
|---|---------|----------------|----------------|-----------|-----------|-------------|
| 0 | 2 | no (no element before) | yes (`suffixMin[1] = 1 < 2`) | yes | 0 | 0 |
| 1 | 1 | yes (`prefixMax[0] = 2 > 1`) | no | yes | 1 | 1 |
| 2 | 3 | no (`2 < 3`) | no (`4 > 3`) | no | 0 | 1 |
| 3 | 4 | no (`3 < 4`) | no (no element after) | no | 1 | 1 |

`unlockCount = 1`, which is not more than `k = 1`, so it is allowed. The only inversion is `(2, 1)`, so `swaps = 1`.

Answer: `1 + 1 = 2`

**Example 2:** `nums = [3, 1, 2]`, `locked = [1, 0, 1]`, `k = 2`

Index `0` (value `3`) and index `2` (value `2`) are both involved and locked, so `unlockCount = 2`, which fits `k = 2`.

Counting inversions from right to left:

| i | x | Seen values before this step | Smaller than x | swaps |
|---|---|------------------------------|----------------|-------|
| 2 | 2 | none | 0 | 0 |
| 1 | 1 | {2} | 0 | 0 |
| 0 | 3 | {2, 1} | 2 | 2 |

Answer: `2 + 2 = 4`

**Example 3:** `nums = [2, 3, 1]`, `locked = [1, 0, 0]`, `k = 0`

Index `0` (value `2`) has a smaller element after it (`1`), so it is involved and locked. `unlockCount = 1 > k = 0`, so the answer is `-1`.

---

## Solution (Java)

```java
class Solution {
    public int minOperations(int[] nums, int[] locked, int k) {

        int n = nums.length;

        int[] prefixMax = new int[n];
        int[] suffixMin = new int[n];

        // largest value from the start up to each index
        prefixMax[0] = nums[0];

        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }

        // smallest value from each index to the end
        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }

        // count locked indices that take part in an inversion
        int unlockCount = 0;

        for (int i = 0; i < n; i++) {

            boolean involved = false;

            if (i > 0 && prefixMax[i - 1] > nums[i]) {
                involved = true;
            }

            if (i < n - 1 && suffixMin[i + 1] < nums[i]) {
                involved = true;
            }

            if (involved && locked[i] == 1) {
                unlockCount++;
            }
        }

        // not enough unlocks available
        if (unlockCount > k) {
            return -1;
        }

        // count inversions (= minimum adjacent swaps) with a Fenwick tree
        int[] bit = new int[n + 1];
        int swaps = 0;

        for (int i = n - 1; i >= 0; i--) {

            int x = nums[i];

            // how many values to the right are smaller than x
            for (int j = x - 1; j > 0; j -= j & -j) {
                swaps += bit[j];
            }

            // add x to the tree
            for (int j = x; j <= n; j += j & -j) {
                bit[j]++;
            }
        }

        return swaps + unlockCount;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n log n)** | Prefix and suffix arrays take O(n), and each of the `n` Fenwick tree queries and updates takes O(log n) |
| Space | **O(n)** | The `prefixMax`, `suffixMin` and `bit` arrays each have about `n` elements |

---

## Key Takeaways

- The answer splits into two independent parts: the **unlocks** that are needed, and the **swaps** that are needed.
- An index must be unlocked only if it is part of an **inversion**, which `prefixMax` and `suffixMin` detect in O(n).
- The minimum number of adjacent swaps needed to sort an array is its **inversion count**.
- A **Fenwick tree (BIT)** counts inversions in O(n log n), which is fast enough for `n = 2000` and much better than checking every pair.
- Locked indices that already hold their final value never need to be unlocked.
