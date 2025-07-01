public class QUES_3330{
    public int possibleStringCount(String word) {
        // StringBuilder hi=new stringBuilder();
        if(word.length()==1){
            return 1;
        }
              int count=0;
              int result=0;
           for(int i=0;i<word.length()-1;i++){
            if(word.charAt(i)!=word.charAt(i+1)){
               count++;
            }
            
            result=word.length()-count;
        }
        return result;
        
    }
}