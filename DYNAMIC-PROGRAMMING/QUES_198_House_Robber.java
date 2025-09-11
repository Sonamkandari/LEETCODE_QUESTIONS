//Brute force Approach is recursive Approach
class Solution {
    public int rob(int[] nums) {
      return robHouseNo(nums,0);
    }
    //creating a helper function
    public int robHouseNo(int[]nums,int houseNo){
        if(houseNo>=nums.length){
            return 0; 
        }

        //choice 1- select the first house and leave adjacent house due to security constraint
        int choice1=nums[houseNo]+robHouseNo(nums,houseNo+2);
        //choice 2- select the second house and leave first house
        int choice2=robHouseNo(nums,houseNo+1);

        int result=Math.max(choice1,choice2);

        return result;
    }
}
