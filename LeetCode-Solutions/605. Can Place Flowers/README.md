# [605. Can Place Flowers](https://leetcode.com/problems/can-place-flowers/)

## Problem Statement

You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in **adjacent** plots.

Given an integer array `flowerbed` containing `0`'s and `1`'s, where `0` means empty and `1` means not empty, and an integer `n`, return `true` if `n` new flowers can be planted in the `flowerbed` without violating the no-adjacent-flowers rule and `false` otherwise.

### Examples

```
Input: flowerbed = [1,0,0,0,1], n = 1
Output: true
```

```
Input: flowerbed = [1,0,0,0,1], n = 2
Output: false
```

### Constraints

- `1 <= flowerbed.length <= 2 * 10^4`
- `flowerbed[i]` is `0` or `1`.
- There are no two adjacent flowers in `flowerbed`.
- `0 <= n <= flowerbed.length`

---

## Approach: Greedy, Plant Whenever Possible

A flower can be planted at position `i` only if the plot is empty and **both neighbours are empty** (or do not exist, at the edges of the flowerbed). Planting as early as possible never makes the answer worse, so we go from left to right and plant a flower at every valid spot.

1. For each index `i`, check the neighbours:
   - `leftempty` is `true` if `i` is the first plot (`i == 0`) or the plot to its left is `0`.
   - `rightemprt` is `true` if `i` is the last plot or the plot to its right is `0`.
2. If `flowerbed[i] == 0` and both neighbours are empty:
   - Plant a flower: `flowerbed[i] = 1`. This also blocks the next plot from being used, because it now has a flower as its left neighbour.
   - Decrease `n` by `1`.
   - If `n == 0`, all the required flowers are planted, so stop early with `break`.
3. At the end, return `true` if `n <= 0`, which means all the flowers were planted. Otherwise return `false`.

Using `n <= 0` (not just `n == 0`) also handles the case `n = 0`, where no flowers are needed and the answer is `true`.

### Dry Run

`flowerbed = [1, 0, 0, 0, 1]`, `n = 1`

| i | flowerbed[i] | Left empty? | Right empty? | Action | n |
|---|--------------|-------------|--------------|--------|---|
| 0 | 1 | - | - | not empty, skip | 1 |
| 1 | 0 | no (`flowerbed[0] = 1`) | yes | cannot plant | 1 |
| 2 | 0 | yes | yes | plant, `flowerbed = [1,0,1,0,1]` | 0 |

`n == 0`, so the loop stops and the output is `true`.

For `n = 2`, the same steps plant at index `2` and leave `n = 1`. Then index `3` has a flower on its left, and index `4` is already planted, so no more flowers fit. At the end `n = 1`, so the output is `false`.

---

## Solution (Java)

```java
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        boolean flag = false;

        for (int i = 0; i < flowerbed.length; i++) {
            boolean leftempty = false;
            boolean rightemprt = false;

            // left plot is empty, or there is no left plot
            if (i == 0 || flowerbed[i - 1] == 0) {
                leftempty = true;
            }

            // right plot is empty, or there is no right plot
            if (i == flowerbed.length - 1 || flowerbed[i + 1] == 0) {
                rightemprt = true;
            }

            // plant a flower here if the plot and both neighbours are empty
            if (flowerbed[i] == 0 && leftempty && rightemprt) {
                flowerbed[i] = 1;
                n--;
                if (n == 0) break;
            }
        }

        // all required flowers were planted
        if (n <= 0) {
            flag = true;
        }

        return flag;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Single pass through the flowerbed (n is its length) |
| Space | **O(1)** | Only a few variables are used; the flowerbed is updated in-place |

---

## Key Takeaways

- Planting a flower at the **first possible spot** is always safe, because it leaves the most room for the plots after it.
- The edges are handled by treating a missing neighbour as empty (`i == 0` and `i == length - 1`).
- Writing `1` into the plot after planting stops the next plot from also being used.
- Stopping early with `break` when `n` reaches `0` saves unnecessary work.
- This solution **modifies the input array** `flowerbed`.
