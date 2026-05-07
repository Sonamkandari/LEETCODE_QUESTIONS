import java.util.Scanner;

public class Zig_zag_feshion {
    public static void zig_zag(int[]arr){
        int n=arr.length-1;
        int left=0;
        int right=left+1;
        while(left<n){
            if(left%2==0){
                if(arr[left]>arr[right]){
                    int temp=arr[left];
                    arr[left]=arr[right];
                    arr[right]=temp;
                    left++;
                    right++;
                }else{
                    left++;
                    right++;
                }
            }else{
                if(arr[left]<arr[right]){
                    int temp=arr[left];
                    arr[left]=arr[right];
                    arr[right] = temp;
                    left ++;
                    right ++;
                }else{
                    left++;
                    right++;
                }
            }
        }


    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the Value of n:");
        int n=sc.nextInt();
        System.out.println("Enter the array Elements:");
        int[]arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }

        zig_zag(arr);

        System.out.println("Print zig_zig Array");
        for(int i=0;i<n;i++){
            System.out.println(arr[i]);

        }

    }
    
}
