
import java.util.Scanner;

class Solution {

    public int lengthOfLastWord(String s) {

        int count = 0;
        int length = s.length() - 1;

        for (int i = length; i >= 0; i--) {

            if (s.charAt(length) == ' ') {
                length--;
            } else {
                break;
            }
        }

        for (int i = length; i >= 0; i--) {

            if (s.charAt(i) == ' ') {
                break;
            } else {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String s = sc.nextLine();

        Solution obj = new Solution();

        int result = obj.lengthOfLastWord(s);

        System.out.println("Length of Last Word: " + result);

        sc.close();
    }
}
