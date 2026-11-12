
import java.util.*;

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

            if (ch == '(' || ch == '[' || ch == '{') {
                sta.push(ch);
            }

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

        return sta.is_Empty();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter brackets: ");
        String s = sc.nextLine();

        Solution obj = new Solution();

        boolean result = obj.isValid(s);

        System.out.println(result);

        sc.close();
    }
}
