class Solution {
    public int findCircleNum(int[][] isConnected) {
        // isConnected[i][j]=0;//no connection
        int numOfCities=isConnected.length;
        boolean []visited=new boolean[numOfCities]; //all fill with false
        int count=0; //number of provinces
        for(int i=0;i<numOfCities;i++){
            if(!visited[i]){//if city not visited
                dfs(i,isConnected,visited);
                // BAck track
                count++;
            }
        }
        return count;
        
    }
    void dfs(int currentCity,int[][]isConnected,boolean visited[]){
        visited[currentCity]=true;//this city is marked visited
        //current city neighbour city
        for(int neighbour=0;neighbour<isConnected.length;neighbour++){
            if(isConnected[currentCity][neighbour]==1&&!visited[neighbour]){
                dfs(neighbour,isConnected,visited);
            }
        }
    }
}