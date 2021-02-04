package parse.executor.task;

import parse.executor.JoinRunDag;
import parse.executor.SqlTask;

@JoinRunDag(enable = true)
public class TotalScoreSqlTaskImpl extends SqlTask {
    public TotalScoreSqlTaskImpl() {
        parents.add(new VisualSqlTaskImpl());
        parents.add(new AvgScoreSqlTaskImpl());
        sql.add("select distinct tcs.user_id AS teacher_user_id, tcs.class_id, tcs.subject_code, csb.user_id AS student_user_id\n" +
                "  FROM dim_uc_teacher_class_subject tcs\n" +
                "    INNER JOIN dim_uc_class_student_bridge csb ON csb.class_id = tcs.class_id\n" +
                "  where csb.available;");
    }
}
