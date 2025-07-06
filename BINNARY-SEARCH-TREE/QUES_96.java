class Solution{
    prvate int[]memo;
    public int numTress(int n){
        memo=new int[n+1];
        return dfs(n);
    }
    private int dfs(int n){
        if(n<=1){
            return 1;
        }
        
    }
}