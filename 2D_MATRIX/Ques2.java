class Solution {
    
    public int countSquares(int[][] matrix) {
          int n=matrix.length;
          int m=matrix[0].length;
       
        int count=0;
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrix[i][j]==1){
                    count++;
                }
            }
        }

        int  totalCount= count+ twoMatrix(matrix,n,m) + ThreeSquare(matrix,n,m);  
        return totalCount;
    }

     //create a helper function for counting trhres's square
        public int ThreeSquare(int [][]matrix,int n,int m){
          
           if (matrix == null || matrix.length < 3 || matrix[0].length < 3) {
            return 0; // Not enough rows or columns to form a 3x3 submatrix
        }

        int count3mat = 0;

        // Iterate through possible top-left corners of a 3x3 submatrix
        for (int i = 0; i <= n -3; i++) {
            for (int j = 0; j <= j -3; j++) {
                // A 3x3 submatrix exists with (i, j) as its top-left corner
               count3mat++;
            }
        }
        return count3mat;
     }
        //create a helper function for counting threes'square
        public int twoMatrix(int[][]matrix,int n,int m){
              if (matrix == null || matrix.length < 3 || matrix[0].length < 2) {
            return 0; // Not enough rows or columns to form a 3x3 submatrix
         }
            int matrix2Count=0;
            for(int i=0;i<n-2;i++){
                for(int k=0;k<m-2;k++){
                    matrix2Count++;
             }
        }

           return  matrix2Count;

    }
        
}