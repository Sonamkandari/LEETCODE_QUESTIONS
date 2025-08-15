import java.util.Scanner;

public class QUES {
    public static int productOfSubarray(int []nums,int k){
        if(k<=1){
            return 0;
        }
        int n=nums.length;
        int multi=1;
       int start=0;
       int maxProduct=-1;
      for(int end=0;end<n;end++){
          multi*=nums[end];
          while(multi>=k && start<=end){
            multi/=nums[start];
            start++;
          }
          //update max product if valid 
          if(multi<k){
            maxProduct=(int)Math.max(maxProduct,multi);
          }

       }
        return maxProduct;

    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the value of k");
        int k=sc.nextInt();
        System.out.println("Enter the value of n");
        int n=sc.nextInt();
        System.out.println("enter array elements");
        int []nums=new int[n];
        for(int i=0;i<n;i++){
            nums[i]=sc.nextInt();
        }
        int result =productOfSubarray(nums,k);
        System.out.println(result);

        
    }
}
