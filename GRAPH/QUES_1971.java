class Solution {
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        // step-1 we build the adjacent list
        List<List<Integer>>graph=new ArrayList<>();
        for(int i=0;i<n;i++){//for filling the list
            graph.add(new ArrayList<>()); //vertex is formed

        }
        //get the edges //or filling the  neighbours
        for(int[]edge:edges){
            int u=edge[0];//start point
            int v=edge[1];//end point
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        boolean visited[]=new boolean[n];
        return dfs(source,destination,graph,visited);
    }
    boolean dfs(int CurrentNode,int dest,List<List<Integer>>graph,boolean visited[]){
        if(CurrentNode==dest){
            return true;
        }
        visited[CurrentNode]=true;//mark this node as visited
        for(int neighbour:graph.get(CurrentNode)){
            if(!visited[neighbour]){
               if(dfs(neighbour,dest,graph,visited)){
                return true;
            }
        }
     } 
        return false;
    }

}