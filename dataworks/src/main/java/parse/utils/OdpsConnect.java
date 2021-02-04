package parse.utils;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;

import java.util.List;

public class OdpsConnect {
    private static final String accessId = "";
    private static final String accessKey = "";
    private static final String endPoint = "";
    private static final String project = "";
    private Odps odps;
    private static final OdpsConnect instance = new OdpsConnect();

    private void OdpsConnect() {
        Account account = new AliyunAccount(accessId, accessKey);
        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        odps.setDefaultProject(project);
        this.odps = odps;
    }

    @SuppressWarnings("deprecation")
    public List<Record> runSql(String sql) {
        Instance i;
        try {
            i = SQLTask.run(odps, sql);
            i.waitForSuccess();
            return SQLTask.getResult(i);
        } catch (OdpsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OdpsConnect getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        OdpsConnect instance = OdpsConnect.getInstance();
        instance.runSql("select 1");
    }
}
