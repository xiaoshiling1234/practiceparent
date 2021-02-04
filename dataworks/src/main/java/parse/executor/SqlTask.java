package parse.executor;

import parse.utils.OdpsConnect;

import java.util.ArrayList;
import java.util.List;

public abstract class SqlTask implements Task {
    public List<Task> parents = new ArrayList<>();
    public List<String> sql = new ArrayList<>();

    public List<Task> getParents() {
        return parents;
    }

    public List<String> getSql() {
        return sql;
    }

    @Override
    public boolean run() {
        OdpsConnect odpsConnect = OdpsConnect.getInstance();
        System.out.println("执行sql!!!");
        for (String s : sql) {
            odpsConnect.runSql(s);
        }
        return true;
    }
}
