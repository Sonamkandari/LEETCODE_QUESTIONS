import java.util.Scanner;

public class CRUD {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter value of n:");
        int n=sc.nextInt();
        int[]arr=new int[n];
        System.out.println("Enter the value of N");

        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }

        for(int i=0;i<n;i++){
            System.out.println(arr[i]);
        }

        // replacing a particular value at a particular index
        // Or we can say updating and modifying a particular index
        arr[0]=9;

        //printing the updated array
       
          for(int i=0;i<n;i++){
            System.out.print(arr[i]);
        }

       //Remove array element
       // arr.remove(arr[4]) not works in case of array

      /*  Why is deletion in arrays expensive?
        Because shifting may happen.
        Worst case:
        delete first element.
        Need to shift: n-1 elements.
        Complexity: O(n)

     */
        
    }
}
