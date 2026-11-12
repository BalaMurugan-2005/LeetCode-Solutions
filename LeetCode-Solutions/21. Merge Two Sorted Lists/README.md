# [21. Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)

## Problem Statement

You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists into one **sorted** list. The list should be made by **splicing together the nodes** of the first two lists.

Return the head of the merged linked list.

### Examples

```
Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]
```

```
Input: list1 = [], list2 = []
Output: []
```

```
Input: list1 = [], list2 = [0]
Output: [0]
```

### Constraints

- The number of nodes in both lists is in the range `[0, 50]`.
- `-100 <= Node.val <= 100`
- Both `list1` and `list2` are sorted in non-decreasing order.

---

## Approach: Dummy Node + Two Pointers

Since both lists are already sorted, we can build the merged list by always picking the **smaller front node** of the two lists, just like the merge step in merge sort.

1. Create a **dummy node** and a pointer `temp` that starts at the dummy. The dummy node avoids special handling for the head of the new list.
2. While both lists have nodes:
   - Compare `list1.val` and `list2.val`.
   - Attach the **smaller** node to `temp.next` and move that list's pointer forward.
   - Move `temp` forward to the node just attached.
3. When one list becomes empty, **attach the remaining nodes** of the other list directly (they are already sorted).
4. Return `dummy.next`, which is the head of the merged list.

No new nodes are created for the result (except the dummy). The existing nodes are re-linked, so this matches the "splicing" requirement.

### Dry Run

`list1 = [1, 2, 4]`, `list2 = [1, 3, 4]`

| list1 | list2 | Compare | Attached | Merged so far |
|-------|-------|---------|----------|---------------|
| 1 | 1 | `1 <= 1` | from list1 | 1 |
| 2 | 1 | `2 > 1` | from list2 | 1 -> 1 |
| 2 | 3 | `2 <= 3` | from list1 | 1 -> 1 -> 2 |
| 4 | 3 | `4 > 3` | from list2 | 1 -> 1 -> 2 -> 3 |
| 4 | 4 | `4 <= 4` | from list1 | 1 -> 1 -> 2 -> 3 -> 4 |
| null | 4 | list1 is empty | attach rest of list2 | 1 -> 1 -> 2 -> 3 -> 4 -> 4 |

Output: `[1, 1, 2, 3, 4, 4]`

If both lists are empty, the loop never runs and `dummy.next` is `null`, so the output is `[]`. If only one list has nodes, that list is attached directly.

---

## Solution (Java)

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        // dummy node to build the new list easily
        ListNode dummy = new ListNode(0);
        ListNode temp = dummy;

        // pick the smaller node each time
        while (list1 != null && list2 != null) {

            if (list1.val <= list2.val) {
                temp.next = list1;
                list1 = list1.next;
            }
            else {
                temp.next = list2;
                list2 = list2.next;
            }

            temp = temp.next;
        }

        // attach the remaining nodes (only one of these will run)
        if (list1 != null) {
            temp.next = list1;
        }

        if (list2 != null) {
            temp.next = list2;
        }

        // dummy.next is the head of the merged list
        return dummy.next;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n + m)** | Each node of both lists is visited once (n and m are the lengths of the lists) |
| Space | **O(1)** | Existing nodes are re-linked; only a dummy node and a few pointers are used |

---

## Key Takeaways

- A **dummy node** removes the need for special checks when setting the head of the new list.
- Because the lists are already sorted, comparing only the front nodes is enough.
- When one list ends, the rest of the other list can be attached in one step.
- Using `<=` keeps the merge **stable** (equal values from `list1` come first).
