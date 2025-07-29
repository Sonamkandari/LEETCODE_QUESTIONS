class Solution {
    public int[] twoSum(int[] nums, int target) {
        //we created a HashMap for storing the indices and numbers
        HashMap<Integer,Integer>map=new HashMap<>();
        //traverse through the array
        for(int i=0;i<nums.length;i++){
          //find the compliment
            int complement=target-nums[i];
              //check is the compliment of that number exists in the map or not
            if(map.containsKey(complement)){
                // if compliment found ,return the indices of the compliment and current nummber
                return new int[] {map.get(complement),i };
            }
            //store the current number and its index in the map
            map.put(nums[i],i);
        }

        //or return empty array if no idexes is found
        return new int[]{};
    }
}
