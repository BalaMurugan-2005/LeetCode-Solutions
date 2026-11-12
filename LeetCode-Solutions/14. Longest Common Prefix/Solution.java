
import java.util.Scanner;

class Solution {

    public String longestCommonPrefix(String[] arr) {


        String prefix = arr[0];

        for (int i = 1; i < arr.length; i++) {

            while (!arr[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }

        return prefix;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        String[] arr = new String[n];

        System.out.println("Enter the strings:");

        for (int i = 0; i < n; i++) {
            arr[i] = sc.next();
        }

        Solution obj = new Solution();

        String result = obj.longestCommonPrefix(arr);

        System.out.println("Longest Common Prefix: " + result);

        sc.close();
    }
}
