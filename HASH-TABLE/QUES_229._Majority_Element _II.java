// brute force Approach for this question
// writting a brute force approch currently
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        //here i created array list  so that i can store my majority elements in it
        List<Integer> list = new ArrayList<Integer>();
        int n = nums.length;
        // this my outer loop
        for (int i = 0; i < n; i++) {
            // here i am checking the size of list first and also checking that what is the first element of the list is equal to i or not
            if (list.size() == 0 || list.get(0) != nums[i]) {
                int count = 0;
                for (int j = 0; j < n; j++) {
                    if (nums[j] == nums[i]) {
                        count++;
                    }
                }
                //checking if the array elements count is greater than n/3
                if (count > n / 3) {
                    list.add(nums[i]);
                }
                //sice there can be at most two majority elements

                if (list.size() == 2) {
                    break;
                }
            }

        }
        return list;
    }
}
