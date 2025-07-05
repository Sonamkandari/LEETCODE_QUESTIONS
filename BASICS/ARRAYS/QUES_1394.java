class Solution {
    public int findLucky(int[] arr) {
        int[]freq=new int[501];//since arr[i]<=500
        for(int number:arr){
            freq[number]=freq[number]+1;
        }
        for(int i=500;i>=1;i--){
            if(freq[i]==i){
                return i;
            }
        }
        return -1;
        
    }
}