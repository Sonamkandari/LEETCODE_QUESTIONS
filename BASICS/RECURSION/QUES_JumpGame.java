
public class QUES_JumpGame {

    public boolean canJump(int[] nums) {
        //calli g the funtion
       return  CanReach(nums,0);
           
        
    }
    //creating a helper function
    boolean CanReach(int[]nums,int index){
        //creating a base case 
        if(index>=nums.length-1){
            return true;
        }
         int max=nums[index];

        for(int step=1;step<=max;step++){
            if( CanReach(nums,index+step)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
    QUES_JumpGame solver = new QUES_JumpGame();

    int[] test1 = {2, 3, 1, 1, 4}; // Expected: true
       

    System.out.println(solver.canJump(test1));
   
}
}
