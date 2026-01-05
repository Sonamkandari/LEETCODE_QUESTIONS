public class linear_serch {

    static int searchTarget(int []arr,int target,int index){
        // base case // this shows target is not present in the array
        if(index==arr.length){
            // there fore returning 0
            return -1;
        }
        // sub problem
        if(arr[index]==target){
            return index;
        }else{
           return searchTarget(arr, target, index+1);
        }
    }   

    public static void main(String[] args) {
        int[]arr={12,34,54,23};
        int target=54;

        int result = searchTarget(arr, target, 0);
        System.out.println(result);
        
    }
    
}
