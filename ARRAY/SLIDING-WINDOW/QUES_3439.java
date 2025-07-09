class Solution {
    public int maxFreeTime(int eventTime, int k, int[] startTime, int[] endTime) {
        List<Integer>freeArray=new ArrayList<>();//store durations of free gaps
        //ith event
        //ith start-i-1th ka end=free gap duration
     freeArray.add(startTime[0]);

        for(int i=1;i<startTime.length;i++){
            freeArray.add(startTime[i]-endTime[i-1]);
        }
        freeArray.add(eventTime-endTime[endTime.length-1]);

        // sliding window
        int i=0;
        int j=0;
        int maxSum=0;
        int currentSum=0;
        int n=freeArray.size();
         for (j = 0; j < freeArray.size(); j++) {
            currentSum += freeArray.get(j);

            if (j - i + 1 > k + 1) {
                currentSum -= freeArray.get(i);
                i++;
            }

            if (j - i + 1 == k + 1) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;

    }
}