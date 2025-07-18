//Approach no 1 recursion
class Solution {
    public int fib(int n) {
        if(n<=1){
            return n;
        }

        int fibonachi=fib(n-2)+fib(n-1);
        return fibonachi;
        
    }
}

//Approch 2 Memoization
class Solution {
    // creating a map
    // cache starts with two values but can grow as needed.
    HashMap<Integer, Integer> cache = new HashMap<>(Map.of(0, 0, 1, 1));

    public int fib(int n) {
        // if already computed then return it 
      if(cache.containsKey(n)){
        return cache.get(n);
      }
      //if Not in cache so compute it first and return in cache
       cache.put(n,fib(n-1)+fib(n-2));
       return cache.get(n);
    }
}

//Approach 3 Tabulation method
class Solution {
    public int fib(int n) {
        //create an cache array
        int []cache=new int [n+1];
        
        cache[0]=0;
        if(n>0) cache[1]=1; 
        for(int i=2;i<=n;i++){
            // fib(n-1)+fib(n-2)
            cache[i]=cache[i-1]+cache[i-2];
        }
        return cache[n];

    }
}   