# [83. Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)

## Problem Statement

Given the `head` of a **sorted** linked list, delete all duplicates such that each element appears only once. Return the linked list **sorted** as well.

### Examples

```
Input: head = [1,1,2]
Output: [1,2]
```

```
Input: head = [1,1,2,3,3]
Output: [1,2,3]
```

### Constraints

- The number of nodes in the list is in the range `[0, 300]`.
- `-100 <= Node.val <= 100`
- The list is guaranteed to be **sorted** in ascending order.

---

## Approach: Single Pointer, Skip the Duplicate Nodes

Because the list is **sorted**, all duplicate values are next to each other. So we only need to compare each node with the node right after it.

1. If the list is empty (`head == null`), return it as it is.
2. Use a pointer `node` that starts at `head`.
3. While `node.next != null`, compare `node.val` with `node.next.val`:
   - If they are **equal**, the next node is a duplicate. Remove it by linking `node.next` to `node.next.next`. Do **not** move `node` yet, because the new next node might also be a duplicate (like `1 -> 1 -> 1`).
   - If they are **different**, move forward: `node = node.next`.
4. Return `head`. The head never changes, because the first node of a sorted list is never a duplicate of an earlier node.

### Dry Run

`head = [1, 1, 2, 3, 3]`

| Current node | Next node | Equal? | Action | List after step |
|--------------|-----------|--------|--------|-----------------|
| 1 (first) | 1 (second) | yes | skip the second `1` | 1 -> 2 -> 3 -> 3 |
| 1 (first) | 2 | no | move to `2` | 1 -> 2 -> 3 -> 3 |
| 2 | 3 (first) | no | move to the first `3` | 1 -> 2 -> 3 -> 3 |
| 3 (first) | 3 (second) | yes | skip the second `3` | 1 -> 2 -> 3 |
| 3 | `null` | loop ends | | 1 -> 2 -> 3 |

Output: `[1, 2, 3]`

---

## Solution (Java)

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode node = head;

        // empty list, nothing to remove
        if (node == null) {
            return node;
        }

        while (node.next != null) {
            if (node.val == node.next.val) {
                // duplicate found: skip the next node
                node.next = node.next.next;
            } else {
                // different value: move forward
                node = node.next;
            }
        }
        return head;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Each node is visited at most once |
| Space | **O(1)** | Nodes are unlinked in-place; only one pointer is used |

---

## Key Takeaways

- In a **sorted** list, duplicates are always neighbours, so comparing a node with the next one is enough.
- After removing a duplicate, **do not move forward**. The new next node could be a duplicate too, so it must be checked again.
- The `head` never changes, so no dummy node is needed (unlike problems where the head itself can be removed).
- The `null` check at the start prevents a `NullPointerException` for an empty list.
