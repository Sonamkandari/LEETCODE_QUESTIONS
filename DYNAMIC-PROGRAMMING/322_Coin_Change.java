//Brute force Approch
class Solution {
    public int coinChange(int[] coins, int amount) {
        if(amount==0) return 0;
        if(amount<0) return -1;
        int minCoins=Integer.MAX_VALUE;
        //try every coin
        //extract each coin
        for(int coin:coins){
            int result=coinChange(coins,amount-coin);
            if(result>=0){
                minCoins=Math.min(minCoins,result+1);
            }
        }
        //return -1 if no solution was found
        return (minCoins==Integer.MAX_VALUE)? -1:minCoins; 
    }
}

//optimize Approach
//the  Time complexity for this solution O(m*n)   and the space complexity is O(n)  
//where m is the totall number of different coins
//and n is the total amount that you have to make

class Solution {
    public int coinChange(int[] coins, int amount) {
        // check the eadge case 
        if (amount < 1)
            return 0;
        //creating a dp array this will stores our all memoizations results
        int[] minCoinsDP = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            minCoinsDP[i] = Integer.MAX_VALUE;
            //Try Each coin -- using enhance for loop for extracting each coin from the array = picking each  of the coin and then try  to determine what are the minimum amount of coin is required to populate each of the value
            for (int coin : coins) {
                if (coin <= i && minCoinsDP[i - coin] != Integer.MAX_VALUE)
                    minCoinsDP[i] = Math.min(minCoinsDP[i], 1 + minCoinsDP[i - coin]);

            }
           
        }

         if (minCoinsDP[amount] == Integer.MAX_VALUE) 
                return -1;
                return minCoinsDP[amount];
            
    }
}
