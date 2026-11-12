# [11. Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

## Problem Statement

You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `i`th line are `(i, 0)` and `(i, height[i])`.

Find two lines that, together with the x-axis, form a container, such that the container contains the most water.

Return the **maximum amount of water** a container can store.

**Notice** that you may not slant the container.

### Examples

```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The vertical lines are represented by the array [1,8,6,2,5,4,8,3,7].
The max area of water the container can contain is 49 (between index 1 and index 8).
```

```
Input: height = [1,1]
Output: 1
```

### Constraints

- `n == height.length`
- `2 <= n <= 10^5`
- `0 <= height[i] <= 10^4`

---

## Approach: Two Pointers (Shrink from Both Ends)

The water held between two lines depends on two things:

- **Width** = distance between the lines = `right - left`
- **Height** = the **shorter** of the two lines = `min(height[left], height[right])`

So `area = (right - left) * min(height[left], height[right])`.

We start with the **widest** container (`left = 0`, `right = n - 1`) and move inward. Moving inward always makes the width smaller, so the only way to get a bigger area is to find a taller line. That tells us which pointer to move:

1. Set `left = 0`, `right = n - 1`, `max = 0`.
2. While `left < right`:
   - Calculate the current area and update `max`.
   - Move the pointer at the **shorter** line inward:
     - If `height[left] < height[right]`, do `left++`.
     - Otherwise, do `right--`.
3. Return `max`.

### Why move the shorter line?

The shorter line limits the height of the container. If we moved the taller line instead, the width would shrink and the height could never be more than the shorter line, so the area could only get smaller. Moving the shorter line is the only move that gives a chance of a bigger area, so we never miss the best answer.

### Dry Run

`height = [1, 8, 6, 2, 5, 4, 8, 3, 7]`, start with `left = 0`, `right = 8`, `max = 0`

| left | right | height[left] | height[right] | Width | Area | max | Move |
|------|-------|--------------|---------------|-------|------|-----|------|
| 0 | 8 | 1 | 7 | 8 | 8 | 8 | `left++` (1 < 7) |
| 1 | 8 | 8 | 7 | 7 | 49 | 49 | `right--` |
| 1 | 7 | 8 | 3 | 6 | 18 | 49 | `right--` |
| 1 | 6 | 8 | 8 | 5 | 40 | 49 | `right--` |
| 1 | 5 | 8 | 4 | 4 | 16 | 49 | `right--` |
| 1 | 4 | 8 | 5 | 3 | 15 | 49 | `right--` |
| 1 | 3 | 8 | 2 | 2 | 4 | 49 | `right--` |
| 1 | 2 | 8 | 6 | 1 | 6 | 49 | `right--` |

Now `left == right`, so the loop ends. Return `max = 49`.

---

## Solution (Java)

```java
class Solution {
    public int maxArea(int[] height) {

        int max = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            // area = width * shorter line
            int area = (right - left) * Math.min(height[left], height[right]);
            max = Math.max(max, area);

            // move the pointer at the shorter line
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Each pointer moves inward at most `n` times in total, so one pass |
| Space | **O(1)** | Only a few variables (`left`, `right`, `max`, `area`) |

---

## Key Takeaways

- Area = **width × shorter height**, so the shorter line is the bottleneck.
- Start with the maximum width, then shrink it only when there is a chance of a taller line.
- Always move the pointer at the **shorter** line, because moving the taller one can never improve the area.
- This beats the brute force O(n²) approach of checking every pair of lines.
- It uses the same two-pointer pattern as problems like Two Sum II and 3Sum.
