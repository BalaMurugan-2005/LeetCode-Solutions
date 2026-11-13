
import java.util.Scanner;

class Solution {

    public String longestPalindrome(String s) {

        int start = 0;
        int maxLength = 1;

        for (int i = 0; i < s.length(); i++) {

            int left = i;
            int right = i;

            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {

                if (right - left + 1 > maxLength) {
                    start = left;
                    maxLength = right - left + 1;
                }

                left--;
                right++;
            }

            left = i;
            right = i + 1;

            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {

                if (right - left + 1 > maxLength) {
                    start = left;
                    maxLength = right - left + 1;
                }

                left--;
                right++;
            }
        }

        return s.substring(start, start + maxLength);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String s = sc.nextLine();

        Solution obj = new Solution();

        String result = obj.longestPalindrome(s);

        System.out.println("Longest Palindrome: " + result);

        sc.close();
    }
}
