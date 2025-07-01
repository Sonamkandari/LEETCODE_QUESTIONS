public class QUES_2810 {
     public String finalString(String s) {
        StringBuilder result = new StringBuilder();
      for(int i=0;i<s.length();i++){
        char ch=s.charAt(i);
        if(ch=='i'){
            result.reverse();
        }else{
            result.append(ch);
        }
      }
      return result.toString();
    }
}
