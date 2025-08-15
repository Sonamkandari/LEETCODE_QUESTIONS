import java.util.Scanner;

public class Ques {
     public static boolean isPowerOfFour(int n) {
        if(n<=0){
            return false;
        }
        while(n%4==0){
            n/=4;
        }
        return n==1;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the  value of n");
        int n=sc.nextInt();
        boolean result=isPowerOfFour(n);
        System.out.println(result);
    }
}
