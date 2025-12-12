class Solution {
    public int search(int[] nums, int target) {
        int n=nums.length;
       int low =0;
       int high=n-1;

       while(low<=high){
        //  we can use this formula also 
        //int mid = low + (high - low) / 2;
        // as it is a standard formula or it also avoids the condition of integer over flow 
        // this formula is used when we used a very large amount of numbers in the arays and the last number is a very large value
         
        int mid=(low+high)/2;
        if(target<nums[mid]){
            high=mid-1;
        }else if(target>nums[mid]){
            low=mid+1;
        }else{
            return mid;
        }
       }

       return -1;
    }
}
