# [20. Valid Parentheses](https://leetcode.com/problems/valid-parentheses/)

## Problem Statement

Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

### Examples

```
Input: s = "()"
Output: true
```

```
Input: s = "()[]{}"
Output: true
```

```
Input: s = "(]"
Output: false
```

```
Input: s = "([])"
Output: true
```

```
Input: s = "([)]"
Output: false
```

### Constraints

- `1 <= s.length <= 10^4`
- `s` consists of parentheses only: `'()[]{}'`.

---

## Approach: Stack (Custom Array Implementation)

Brackets must close in the **reverse order** they were opened (last opened, first closed). This is exactly how a **stack (LIFO)** works, so a stack is used here. Instead of Java's built-in `Stack`, this solution uses a **custom stack built on a `char` array** (`StackImplementation`) with `push`, `pop`, `peek` and `is_Empty` methods.

1. Create a stack with size equal to the string length.
2. Go through each character `ch` of the string:
   - If it is an **opening bracket** (`(`, `[`, `{`), **push** it onto the stack.
   - If it is a **closing bracket** (`)`, `]`, `}`):
     - If the stack is **empty**, there is nothing to match with, so return `false`.
     - If the top of the stack is the **matching opening bracket**, **pop** it.
     - Otherwise the type is wrong, so return `false`.
3. After the loop, the string is valid only if the stack is **empty** (every opening bracket was closed).

### Dry Run

`s = "([])"`

| Character | Action | Stack (bottom to top) |
|-----------|--------|-----------------------|
| `(` | opening, push | `(` |
| `[` | opening, push | `( [` |
| `]` | top is `[`, matches, pop | `(` |
| `)` | top is `(`, matches, pop | empty |

The stack is empty at the end, so the output is `true`.

`s = "([)]"`

| Character | Action | Stack (bottom to top) |
|-----------|--------|-----------------------|
| `(` | opening, push | `(` |
| `[` | opening, push | `( [` |
| `)` | top is `[`, does not match `(` | return `false` |

Output: `false`

---

## Solution (Java)

```java
import java.util.*;

// Custom stack implemented using a char array
class StackImplementation {
    char[] stack;
    int top;

    StackImplementation(int size) {
        stack = new char[size];
        top = -1;
    }

    void push(char charecter) {
        if (top == stack.length - 1) {
            System.out.println("Stack Overflow");
        } else {
            top++;
            stack[top] = charecter;
        }
    }

    void pop() {
        if (top == -1) {
            System.out.println("Stack UnderFlow");
        } else {
            top--;
        }
    }

    void display() {
        if (top == -1) {
            System.out.println("Stack Underflow");
        } else {
            for (int i = top; i >= 0; i--) {
                System.out.println(stack[i]);
            }
        }
    }

    public char peek() {
        char charvalue = 0;
        if (top == -1) {
            System.out.println("Stack UnderfLow");
        } else {
            charvalue = stack[top];
        }
        return charvalue;
    }

    public boolean is_Empty() {
        if (top == -1) {
            return true;
        }
        return false;
    }
}

class Solution {

    public boolean isValid(String s) {

        StackImplementation sta = new StackImplementation(s.length());

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            // Opening bracket
            if (ch == '(' || ch == '[' || ch == '{') {
                sta.push(ch);
            }

            // Closing bracket
            else if (ch == ')' || ch == ']' || ch == '}') {

                if (sta.is_Empty()) {
                    return false;
                }

                if (ch == ')' && sta.peek() == '(') {
                    sta.pop();
                }
                else if (ch == ']' && sta.peek() == '[') {
                    sta.pop();
                }
                else if (ch == '}' && sta.peek() == '{') {
                    sta.pop();
                }
                else {
                    return false;
                }
            }
        }

        // valid only if no unmatched opening brackets remain
        return sta.is_Empty();
    }
}
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(n)** | Each character is processed once, and every stack operation takes O(1) |
| Space | **O(n)** | The stack array can hold up to n characters (when all are opening brackets) |

---

## Key Takeaways

- Matching brackets in the correct order is a classic **stack (LIFO)** problem.
- Check for an **empty stack** before looking at the top, so a string like `")"` is rejected safely.
- The final `is_Empty()` check catches leftover opening brackets, such as in `"(("`.
- Building the stack manually shows how `push`, `pop` and `peek` work internally.
