class Solution {
    public void rotate(int[][] matrix) {
        int n=matrix.length; // total no of row
        int m=matrix[0].length;// total no of column
        
       // step 1  transepose the given matrix first
       for(int i=0;i<n;i++){
        for(int j=0;j<=i;j++){
            //Swap rows of the matix to the column of the matrix
            int temp=matrix[i][j];
            matrix[i][j]=matrix[j][i];
            matrix[j][i]=temp;
            
        }
     }

     //After taking the transepose of the matrix  for the rotaion of the matrix
     //swap the last column of the matrix to the first column

     for(int i=0;i<n;i++){
        int j=0, k=n-1;
        while(j<k){
            int  temp=matrix[i][j];
            matrix[i][j]=matrix[i][k];
            matrix[i][k]=temp;
            j++;
            k--;
        }
     }

        
    }
}
