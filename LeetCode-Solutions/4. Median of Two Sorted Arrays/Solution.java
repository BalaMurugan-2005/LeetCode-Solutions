
import java.util.Scanner;

class Solution {

    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            arr[left + x] = temp[x];
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int[] arr = new int[nums1.length + nums2.length];

        for (int i = 0; i < nums1.length; i++) {
            arr[i] = nums1[i];
        }

        for (int i = 0; i < nums2.length; i++) {
            arr[nums1.length + i] = nums2[i];
        }

        mergeSort(arr, 0, arr.length - 1);

        for (int num : arr) {
            System.out.print(num + " ");
        }

        int n = arr.length;
        double median;

        if (n % 2 != 0) {
            median = arr[n / 2];
        } else {
            median = (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
        }

        return median;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter nums1 size: ");
        int n1 = sc.nextInt();

        int[] nums1 = new int[n1];

        System.out.println("Enter nums1 elements:");
        for (int i = 0; i < n1; i++) {
            nums1[i] = sc.nextInt();
        }

        System.out.print("Enter nums2 size: ");
        int n2 = sc.nextInt();

        int[] nums2 = new int[n2];

        System.out.println("Enter nums2 elements:");
        for (int i = 0; i < n2; i++) {
            nums2[i] = sc.nextInt();
        }

        Solution obj = new Solution();

        double result = obj.findMedianSortedArrays(nums1, nums2);

        System.out.println("\nMedian: " + result);

        sc.close();
    }
}
