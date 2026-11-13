# [2. Add Two Numbers](https://leetcode.com/problems/add-two-numbers/)

## Problem Statement

You are given two **non-empty** linked lists representing two non-negative integers. The digits are stored in **reverse order**, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

### Examples

```
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
```

```
Input: l1 = [0], l2 = [0]
Output: [0]
```

```
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]
```

### Constraints

- The number of nodes in each linked list is in the range `[1, 100]`.
- `0 <= Node.val <= 9`
- It is guaranteed that the list represents a number that does not have leading zeros.

---

## Approach: Add Digit by Digit With a Carry

The digits are stored in **reverse order**, so the **first node is the ones place**. This is perfect for addition, because we can start adding from the head of both lists, just like adding on paper from right to left.

1. Create a **dummy node** and a pointer `current` that starts at the dummy. The dummy node makes it easy to build the new list without special handling for the head.
2. Set `carry = 0`.
3. Repeat while `l1` has nodes, or `l2` has nodes, or a `carry` is left (`l1 != null || l2 != null || carry != 0`):
   - Take `x` from `l1` and `y` from `l2`. If a list has already ended, use `0`.
   - Calculate `sum = x + y + carry`.
   - The new `carry` is `sum / 10` and the digit to store is `sum % 10`.
   - Create a **new node** with that digit, attach it to `current.next` and move `current` forward.
   - Move `l1` and `l2` forward if they are not `null`.
4. Return `dummy.next`, the head of the result list.

The `carry != 0` part of the condition adds an extra final node when the answer has one more digit than both inputs (like `99 + 1 = 100`).

### Dry Run

`l1 = [2, 4, 3]`, `l2 = [5, 6, 4]` (this means 342 + 465)

| Step | x | y | carry in | sum | digit (`sum % 10`) | carry out (`sum / 10`) | Result list |
|------|---|---|----------|-----|--------------------|------------------------|-------------|
| 1 | 2 | 5 | 0 | 7 | 7 | 0 | 7 |
| 2 | 4 | 6 | 0 | 10 | 0 | 1 | 7 -> 0 |
| 3 | 3 | 4 | 1 | 8 | 8 | 0 | 7 -> 0 -> 8 |

Both lists are finished and the carry is `0`, so the loop stops.

Output: `[7, 0, 8]`

For `l1 = [9,9,9,9,9,9,9]` and `l2 = [9,9,9,9]`, the shorter list ends after 4 steps, so `0` is used for its missing digits. After the 7th step a carry of `1` is left, and the loop runs one more time to add the final `1`. The output is `[8,9,9,9,0,0,0,1]`.

---

## Solution (Java)

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // dummy node to build the result list easily
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        int carry = 0;

        // continue while there are digits or a carry left
        while (l1 != null || l2 != null || carry != 0) {

            // use 0 if a list has already ended
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;

            carry = sum / 10;
            int digit = sum % 10;

            // add the new digit to the result list
            current.next = new ListNode(digit);
            current = current.next;

            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }
        }

        return dummy.next;
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(max(m, n))** | The loop runs once for each digit of the longer list (m and n are the lengths of the lists), plus at most one extra step for the carry |
| Space | **O(max(m, n))** | A new list is created for the result; no other extra memory is used |

---

## Key Takeaways

- Because digits are stored in **reverse order**, the lists can be added from the head, with no reversing needed.
- A **dummy node** removes the need for special handling of the first node of the result.
- Using `0` for a finished list lets lists of different lengths be handled in the same loop.
- The `carry != 0` condition handles a leftover carry, like `[5] + [5] = [0, 1]`.
- Working node by node avoids converting the lists into numbers, which would overflow for lists with up to 100 digits.
