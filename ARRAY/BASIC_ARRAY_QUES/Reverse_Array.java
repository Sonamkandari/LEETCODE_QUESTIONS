import java.util.Scanner;

public class Reverse_Array {
    public static void reverseArray(int arr[]) {
        // code here
        int n=arr.length;
        int left = 0;
        int right = n-1;
        while(left<right){
        int temp=arr[left];
        arr[left]=arr[right];
        arr[right]=temp;
        left++;
        right--;
        }
       
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the value of N");
        int n=sc.nextInt();
        System.out.println("Enter the Array Elements");
        int arr[]=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }

        System.out.println("Print the original array");

        for(int i=0;i<n;i++){
            System.out.println(arr[i]);
        }
        System.out.println("Print the reversed array");
        reverseArray(arr);
        
         System.out.println("Print the reversed array:");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
