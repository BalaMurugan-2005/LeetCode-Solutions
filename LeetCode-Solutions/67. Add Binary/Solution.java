
import java.util.Scanner;

class Solution {

    public String addBinary(String a, String b) {

        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        String result = "";

        while (i >= 0 || j >= 0 || carry > 0) {

            int bit1 = 0;
            int bit2 = 0;

            if (i >= 0) {
                bit1 = a.charAt(i) - '0';
                i--;
            }

            if (j >= 0) {
                bit2 = b.charAt(j) - '0';
                j--;
            }

            int sum = bit1 + bit2 + carry;

            result = (sum % 2) + result;
            carry = sum / 2;
        }

        return result;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first binary number: ");
        String a = sc.nextLine();

        System.out.print("Enter second binary number: ");
        String b = sc.nextLine();

        Solution obj = new Solution();

        String result = obj.addBinary(a, b);

        System.out.println("Binary Sum: " + result);

        sc.close();
    }
}
