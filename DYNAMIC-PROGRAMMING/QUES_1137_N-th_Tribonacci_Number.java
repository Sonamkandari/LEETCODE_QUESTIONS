//Memoization Approach
class Solution {
    public int tribonacci(int n) {

        Integer[]cache=new Integer[n+1];
        return helper(n,cache);
    }
    // creating a hepler function
    public int helper(int n,Integer cache[]){
        if(n==0){
            return 0;
        }
        if(n==1||n==2){
            return 1;
        }
        if(cache[n]!=null){
            return cache[n];
        }

        return cache[n]=helper(n-1,cache)+helper(n-2,cache)+helper(n-3,cache);

    }
}



//using tabulation Method
class Solution {
    public int tribonacci(int n) {
        if(n==0){
            return 0;
        }
        if(n==1||n==2){
            return 1;
        }

       // created a dp array
        int dp[]=new int[n+1];
        dp[0]=0;
        dp[1]=1;
        dp[2]=1;
        for(int i=3;i<=n;i++){
            dp[i]=dp[i-1]+dp[i-2]+dp[i-3];
        }
        return dp[n];
    }
}
