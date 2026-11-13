# [4. Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/)

## Problem Statement

Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return **the median** of the two sorted arrays.

The overall run time complexity should be `O(log (m+n))`.

### Examples

```
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
```

```
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
```

### Constraints

- `nums1.length == m`
- `nums2.length == n`
- `0 <= m <= 1000`
- `0 <= n <= 1000`
- `1 <= m + n <= 2000`
- `-10^6 <= nums1[i], nums2[i] <= 10^6`

---

## Approach: Combine Both Arrays + Merge Sort + Pick the Middle

The median is the middle value of the sorted combined data. This solution builds that combined sorted array directly and then reads the median from it.

1. **Combine:** create a new array `arr` of size `nums1.length + nums2.length` and copy all elements of `nums1` and then `nums2` into it.
2. **Sort:** sort `arr` using **merge sort**, which is written by hand with two helper methods:
   - `mergeSort(arr, left, right)` splits the array into two halves recursively until each part has one element.
   - `merge(arr, left, mid, right)` merges two sorted halves into one sorted part, using a temporary array.
3. **Find the median** using the length `n` of the combined array:
   - If `n` is **odd**, the median is the middle element `arr[n / 2]`.
   - If `n` is **even**, the median is the average of the two middle elements, `(arr[n / 2 - 1] + arr[n / 2]) / 2.0`. The `2.0` makes sure the division gives a decimal result.

### Dry Run

**Example 1:** `nums1 = [1, 3]`, `nums2 = [2]`

| Step | Result |
|------|--------|
| Combine | `arr = [1, 3, 2]` |
| Merge sort | `[1, 3]` is already sorted, `[2]` has one element, and merging them gives `[1, 2, 3]` |
| Length | `n = 3` (odd) |
| Median | `arr[3 / 2] = arr[1] = 2` |

Output: `2.0`

**Example 2:** `nums1 = [1, 2]`, `nums2 = [3, 4]`

| Step | Result |
|------|--------|
| Combine | `arr = [1, 2, 3, 4]` |
| Merge sort | `[1, 2, 3, 4]` |
| Length | `n = 4` (even) |
| Median | `(arr[1] + arr[2]) / 2.0 = (2 + 3) / 2.0 = 2.5` |

Output: `2.5`

---

## Solution (Java)

```java
class Solution {

    // splits the array into halves and sorts them recursively
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    // merges two sorted halves: [left..mid] and [mid+1..right]
    static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // combine both arrays into one
        int[] arr = new int[nums1.length + nums2.length];

        for (int i = 0; i < nums1.length; i++) {
            arr[i] = nums1[i];
        }
        for (int i = 0; i < nums2.length; i++) {
            arr[nums1.length + i] = nums2[i];
        }

        // sort the combined array
        mergeSort(arr, 0, arr.length - 1);

        // only for local testing: print the sorted array
        for (int num : arr) {
            System.out.print(num + " ");
        }

        // pick the median
        int n = arr.length;
        double median;

        if (n % 2 != 0) {
            median = arr[n / 2];
        } else {
            median = (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
        }

        return median;
    }
}
```

> **Note:** The printing loop is only for local testing and can be removed when submitting.

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O((m + n) log(m + n))** | Merge sort on the combined array of `m + n` elements |
| Space | **O(m + n)** | The combined array plus temporary arrays used while merging |

> The problem asks for `O(log (m + n))`, which needs a binary search approach. This solution is correct and fits the given limits (`m + n <= 2000`), but it is slower than the required complexity.

---

## Key Takeaways

- The median is the middle element of an odd-length sorted array, or the average of the two middle elements of an even-length one.
- Dividing by `2.0` (not `2`) keeps the decimal part of the average.
- Merge sort is written here with two parts: a recursive split (`mergeSort`) and a merge step (`merge`).
- Writing `mid = left + (right - left) / 2` avoids integer overflow compared to `(left + right) / 2`.
