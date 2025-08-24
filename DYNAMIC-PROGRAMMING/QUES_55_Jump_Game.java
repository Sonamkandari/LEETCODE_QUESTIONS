//for the optimization of this recursive appproach we will used memoization approach
class Solution {
    public boolean canJump(int[] nums) {
        // created a cache array which+ will remember the steps which already taken once
        int[]cache=new int[nums.length];
        return CanReach(nums,0,cache);

        
    }

    //creating a helper function
    private boolean CanReach(int[]nums,int index,int[]cache){
        //base case
        if(index>=nums.length-1){
            return true;
        }
        if(cache[index]!=0){
            return cache[index]==1;
        }
        int max=nums[index];
        for(int step=1;step<=max;step++){
            if(CanReach(nums,index+step,cache)){
                cache[index]=1;
                return true;
            }
        }

        cache [index]=-1;
        return false;
   

    }
}
