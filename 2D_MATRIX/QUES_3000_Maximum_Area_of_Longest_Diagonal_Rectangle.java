class Solution {
    public int areaOfMaxDiagonal(int[][] dimensions) {
        double maxDiagonal=0;
        int maxArea=0;
        // int maxArea=0;
       //extracting  the 1 dimensional array first
       for(int rect[]:dimensions){
        //at the zero th index we have length of the diagonal
        int length=rect[0];
        //at the one  th index we have width of the diagonal
        int width=rect[1];
        //finding the size of the diiadonal
        double diagonal=Math.sqrt(length*length+width*width);
        //calculating the area of the diagonal
         int area=length*width;
         //if current diagonal is greater than the maximum diagonal
        if(diagonal>maxDiagonal){
            //updating the maxDiagonal
            maxDiagonal=diagonal;
            maxArea=area;
            
        }

        else if(diagonal==maxDiagonal){
            maxArea=Math.max(maxArea,area);

        } 
       }
       return maxArea;   
    }
}
