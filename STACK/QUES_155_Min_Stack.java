//Brute force Approach
//Time Complexity for all the operation is O(1)
//The space cpmlexity is O(n)

class MinStack {
     //creating a box 
    private Stack<Integer>st; 
    //creating Constructer
    public MinStack() {
         st=new Stack<>();

        
    }
    //creating a function which is used to add a value in to the stack
    public void push(int val) {
        st.push(val);  
    }
    // creating a function which is used to pop a value from the stack
    public void pop() {
        st.pop();  
    }
    
    //creating a function to check or seek the top most element of the stack
    public int top() {
      return  st.peek();
        
    }
    
    //this function is heping us to get the minimum element from the stack
    public int getMin() {
        int minimumEle=Integer.MAX_VALUE;
        //itrate the complete stack
        for(int num:st){
            minimumEle=Math.min(minimumEle,num);


        }
        return minimumEle;
 
    }
    public static void main(String[] args) {
        MinStackOptimized ms = new MinStackOptimized();
        ms.push(-2);
        ms.push(0);
        ms.push(-3);
        System.out.println(ms.getMin()); // -3
        ms.pop();
        System.out.println(ms.top());    // 0
        System.out.println(ms.getMin()); // -2
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
