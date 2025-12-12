class Solution {
    public int mostFrequentEven(int[] nums) {
        int n = nums.length;
        // initializinging maxFreq as 0
        int maxFreq = 0;
        // initially considering result as 0
        int result = -1;
        //outer loop
        for (int i = 0; i < n; i++) {
            // checking if element at I is an even number or not
            if (nums[i] % 2 == 0) {
                //initLL COUNT ==0
                int count = 0;
                //inner loop
                for (int j = 0; j < n; j++) {
                    // if we finf the more numbers which is equal to i 
                    if (nums[i] == nums[j]) {
                        // increaing the count of i
                        count++;
                    }
                }
                //update the result if
                //1.found a higher frequncy
                //2.same freuency but smaller number

                if(count>maxFreq||(count==maxFreq && nums[i]<result)){
                    maxFreq=count;
                    result=nums[i];
                }

            }

        }

        return result;

    }
}
