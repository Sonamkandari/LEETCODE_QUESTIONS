// brute force Approach for this question
// writting a brute force approch currently

//time complexity - O(n^2)
//space complexity -O(n)
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

// Time Complexity: O(n)
// Space Complexity: O(n)

//better approach for this question
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        //creating a list 
        List<Integer> list = new ArrayList<>();
        //creating a HashMap
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int minimum = n / 3+1;// floor value
        for (int numbers : nums) {
            //counting the frequency or count of each element
            map.put(numbers, map.getOrDefault(numbers, 0) + 1);
            //checking  the number count is equal to minimum or no
            if (map.get(numbers) == minimum) {
                // if the number count is equal to minimum add it to the list
                list.add(numbers);
            }
            // check the size of the list if it is equal to the limit breakthe loop
            if (list.size() == 2) {
                break;
            }

        }

        Collections.sort(list);
        return list;

    }
}
