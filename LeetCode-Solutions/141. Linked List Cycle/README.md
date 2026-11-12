# [141. Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)

## Problem Statement

Given `head`, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that the tail's `next` pointer is connected to. Note that `pos` is **not passed as a parameter**.

Return `true` if there is a cycle in the linked list. Otherwise, return `false`.

### Examples

```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
```

```
Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
```

```
Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
```

### Constraints

- The number of nodes in the list is in the range `[0, 10^4]`.
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a **valid index** in the linked list.

**Follow up:** Can you solve it using `O(1)` (i.e. constant) memory?

---

## Approach: Two Pointers (Slow and Fast, Floyd's Cycle Detection)

Use two pointers that move through the list at different speeds:

- `slow` moves **1 step** at a time.
- `fast` moves **2 steps** at a time.

If there is **no cycle**, `fast` reaches the end (`null`) and we stop.
If there **is a cycle**, both pointers end up going around the loop. Since `fast` gains one step on `slow` every round, it will eventually catch up and they will point to the **same node**.

1. Set `slow = head` and `fast = head`.
2. While `fast != null` and `fast.next != null`:
   - Move `slow` one step: `slow = slow.next`.
   - Move `fast` two steps: `fast = fast.next.next`.
   - If `slow == fast`, a cycle exists, so return `true`.
3. If the loop ends, `fast` reached the end of the list, so return `false`.

### Why does it work?

Think of two runners on a circular track. The faster runner gains one step on the slower one every round, so the gap between them keeps shrinking by 1 until it becomes 0. At that point they meet. On a straight path (no cycle) the faster runner just reaches the end and they never meet.

### Why check `fast != null && fast.next != null`?

`fast` jumps two nodes at a time. We need `fast.next` to be non-null before doing `fast.next.next`, otherwise we get a `NullPointerException`. This check also handles an empty list (`head == null`).

### Dry Run

`head = [3, 2, 0, -4]`, the tail (`-4`) connects back to index `1` (value `2`).

Start with `slow = 3`, `fast = 3`

| Step | slow | fast | slow == fast? |
|------|------|------|---------------|
| 1 | 2 | 0 | no |
| 2 | 0 | 2 | no |
| 3 | -4 | -4 | **yes** |

`slow` and `fast` meet at node `-4`, so return `true`.

For `head = [1]`, `pos = -1`: `fast.next` is `null`, so the loop never runs and we return `false`.

---

## Solution (Java)

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;        // 1 step
            fast = fast.next.next;   // 2 steps

            // both pointers meet, so there is a cycle
            if (slow == fast) {
                return true;
            }
        }

        // fast reached the end, so there is no cycle
        return false;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Without a cycle `fast` reaches the end in about n/2 steps. With a cycle, the pointers meet within a few rounds of the loop |
| Space | **O(1)** | Only two pointers (`slow`, `fast`), no extra data structure |

---

## Key Takeaways

- Floyd's slow and fast pointer technique detects a cycle without extra memory.
- A HashSet of visited nodes also works, but it uses `O(n)` space. Two pointers gives the `O(1)` follow-up answer.
- Always check `fast != null && fast.next != null` to avoid a `NullPointerException`.
- The empty list case (`head == null`) is already handled by the loop condition.
- The same pattern is used in problems like Linked List Cycle II and Middle of the Linked List.
