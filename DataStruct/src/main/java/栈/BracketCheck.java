package 栈;

public class BracketCheck {
    SqStack s=new SqStack<>(50);
    public boolean bracketCheck(String str){
        for (char i :str.toCharArray()){
           if (i=='('||i=='{'||i=='['){
               s.push(i);
           }else {
               if (s.stackEmpty()){//扫描到右括号，且当前栈空
                   return false;
               }

               char c = (char) s.pop();
               if (i=='}'&&c!='{'){
                   return false;
               }
               if (i==']'&&c!='['){
                   return false;
               }
               if (i==')'&&c!='('){
                   return false;
               }
           }
        }
        return s.stackEmpty();
    }

    public static void main(String[] args) {
        BracketCheck bracketCheck = new BracketCheck();
        System.out.println(bracketCheck.bracketCheck(
                "(){}[()]"
        ));
        System.out.println(bracketCheck.bracketCheck("()]]"));
    }
}
