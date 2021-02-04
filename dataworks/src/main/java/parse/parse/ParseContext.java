package parse.parse;

import java.io.FileInputStream;

public class ParseContext {
    ParseState parseState;
    private final ParseStrategy psg;
    public ParseContext(ParseStrategy parseStrategy){
        this.psg=parseStrategy;
    }
    public void doAction(FileInputStream fi){
        try {
            parseState=ParseState.VALIDATE;
            cheakFile(fi);
            parseState=ParseState.PARSING;
            this.psg.Parse(fi);
            parseState=ParseState.COMPLETED;
        }catch (Exception e){
            parseState=ParseState.ERROR;
            e.printStackTrace();
        }
    }
    static void cheakFile(FileInputStream fi){
        System.out.println("检查文件是否合法");
    }
}
