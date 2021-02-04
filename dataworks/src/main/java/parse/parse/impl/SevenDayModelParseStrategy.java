package parse.parse.impl;

import parse.parse.ParseStrategy;

import java.io.FileInputStream;

public class SevenDayModelParseStrategy implements ParseStrategy {
    @Override
    public String getName() {
        return "七天";
    }

    @Override
    public void Parse(FileInputStream fi) {
        System.out.println("解析中");
    }
}
