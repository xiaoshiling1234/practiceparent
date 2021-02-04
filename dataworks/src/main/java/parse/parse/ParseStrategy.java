package parse.parse;

import java.io.FileInputStream;

public interface ParseStrategy {
    String getName() ;
    void Parse(FileInputStream fi);
}
