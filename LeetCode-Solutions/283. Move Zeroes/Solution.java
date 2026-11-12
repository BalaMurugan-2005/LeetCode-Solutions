class Solution {
    public void moveZeroes(int[] arr) {
        int pos = 0; 
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
               
                int temp = arr[i];
                arr[i] = arr[pos];
                arr[pos] = temp;
                pos++;
            }
        }
        for (int data : arr) {
            System.out.println(data);
        }
    }
}