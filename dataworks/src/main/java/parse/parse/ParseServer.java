package parse.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParseServer {
    public void main(String[] args) throws FileNotFoundException {
//        SevenDayModelParseStrategy sevenDayModelParseStrategy = new SevenDayModelParseStrategy();
//        ParseContext parseContext = new ParseContext(sevenDayModelParseStrategy);
//        parseContext.doAction(null);
//        GoodScoreModelParseStrategy goodScoreModelParseStrategy = new GoodScoreModelParseStrategy();
//        ParseContext goodScoreContext = new ParseContext(goodScoreModelParseStrategy);
//        goodScoreContext.doAction(null);
        FileInputStream fileInputStream = readExcel("七天网络模板-华蓥中学.xlsx");



    }

    public FileInputStream readExcel(String fileName) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(
                String.valueOf(this.getClass().getResourceAsStream(fileName)));
        return fileInputStream;
    }
}
