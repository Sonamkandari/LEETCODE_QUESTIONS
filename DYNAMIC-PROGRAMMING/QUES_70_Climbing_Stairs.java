import java.util.Scanner;

public class QUES_70_Climbing_Stairs {
    public static int climbStairs(int n) {
        //base case
        if(n==1){
            return 1;
        }
       int dp[]=new int[n+1];
       //
       dp[1]=1;
       dp[2]=2;
       for(int i=3;i<=n;i++){
        dp[i]=dp[i-1]+dp[i-2];
       }

       return dp[n];
      
       
        
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the value of  of n:");
        int n=sc.nextInt();
        int result= climbStairs(n);
        System.out.println("Number of ways is:"+result);
        
    }
    
}
