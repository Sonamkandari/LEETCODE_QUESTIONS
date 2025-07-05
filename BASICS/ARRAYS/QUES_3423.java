class Solution {
    public int maxAdjacentDistance(int[] nums) {
        int result = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // Get the next index in a circular manner
            int nextIndex = (i + 1) % n;
            // Calculate absolute difference between adjacent elements
            int diff = Math.abs(nums[i] - nums[nextIndex]);
            // Update result if this diff is greater than current result
            result = Math.max(result, diff);
        }

        return result;
    }
}
