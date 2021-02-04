package parse.parse.impl;

import parse.parse.ParseStrategy;

import java.io.FileInputStream;

public class GoodScoreModelParseStrategy implements ParseStrategy {
    @Override
    public String getName() {
        return "好分数";
    }

    @Override
    public void Parse(FileInputStream fi) {
        System.out.println("好分数解析模板");
    }
}
