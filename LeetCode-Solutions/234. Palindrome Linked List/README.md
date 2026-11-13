# [234. Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/)

## Problem Statement

Given the `head` of a singly linked list, return `true` if it is a palindrome or `false` otherwise.

### Examples

```
Input: head = [1,2,2,1]
Output: true
```

```
Input: head = [1,2]
Output: false
```

### Constraints

- The number of nodes in the list is in the range `[1, 10^5]`.
- `0 <= Node.val <= 9`

**Follow up:** Could you do it in `O(n)` time and `O(1)` space?

---

## Approach: Find Middle + Reverse Second Half + Compare

In a singly linked list we can only move forward, so we cannot compare from both ends directly. The trick is to **reverse the second half** of the list and then compare it with the first half. This needs no extra list or array, so it meets the follow-up (`O(n)` time, `O(1)` space).

1. **Find the middle** using the slow and fast pointer technique. `slow` moves 1 step and `fast` moves 2 steps. When `fast` reaches the end, `slow` is at the middle.
2. **Skip the middle node if the length is odd.** If `fast != null` after the loop, the list has an odd number of nodes, so move `slow` one step ahead. The middle node can be ignored because it is compared with itself.
3. **Reverse the second half** starting from `slow`, using the standard `prev`, `current`, `next` reversal. At the end, `prev` is the head of the reversed second half.
4. **Compare both halves:** walk `first` from `head` and `second` from the reversed half. If any values differ, return `false`. If `second` becomes `null` with no mismatch, return `true`.

### Dry Run

`head = [1, 2, 2, 1]`

**Step 1: find the middle**

| Iteration | slow | fast |
|-----------|------|------|
| Start | 1st node (`1`) | 1st node (`1`) |
| 1 | 2nd node (`2`) | 3rd node (`2`) |
| 2 | 3rd node (`2`) | `null` |

`fast` is `null`, so the length is even and no node is skipped. The second half starts at the 3rd node: `2 -> 1`.

**Step 3: reverse the second half**

`2 -> 1` becomes `1 -> 2`, so `second` starts at `1`.

**Step 4: compare**

| first | second | Equal? |
|-------|--------|--------|
| 1 | 1 | yes |
| 2 | 2 | yes |
| (second is `null`, stop) | | |

Output: `true`

For `head = [1, 2]`, the second half is `2` and the first node is `1`. They are not equal, so the output is `false`.

For an odd length like `[1, 2, 3, 2, 1]`, `fast` ends on the last node (not `null`), so `slow` moves one step past the middle `3`. The second half `2 -> 1` is reversed to `1 -> 2` and compared with `1 -> 2`, giving `true`.

---

## Solution (Java)

```java
class Solution {
    public boolean isPalindrome(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        // 1. Find the middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Skip the middle node if length is odd
        if (fast != null) {
            slow = slow.next;
        }

        // 3. Reverse the second half
        ListNode prev = null;
        ListNode current = slow;

        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        // 4. Compare both halves
        ListNode first = head;
        ListNode second = prev;

        while (second != null) {
            if (first.val != second.val) {
                return false;
            }

            first = first.next;
            second = second.next;
        }

        return true;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Finding the middle, reversing half and comparing each take one pass |
| Space | **O(1)** | Only a few pointers are used; the list is reversed in-place |

---

## Key Takeaways

- **Slow and fast pointers** find the middle of a linked list in a single pass.
- Reversing the second half lets us compare the list from both ends without extra memory.
- For an **odd** length, `fast` is not `null` at the end, so the middle node is skipped.
- Comparing only until `second` becomes `null` is enough, since the second half is never longer than the first.
- This solution **modifies the list** (the second half stays reversed). If the original list is needed afterwards, the second half should be reversed back.
