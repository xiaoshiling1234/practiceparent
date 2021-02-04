package parse.executor.task;

import parse.executor.JoinRunDag;
import parse.executor.SqlTask;

@JoinRunDag(enable = true)
public class AvgScoreSqlTaskImpl extends SqlTask {
    public AvgScoreSqlTaskImpl() {
        parents.add(new VisualSqlTaskImpl());
        sql.add("select urd. *\n" +
                "  from dwd_os_user_use_resource_detail urd\n" +
                "    INNER JOIN dim_os_lesson l ON l.lesson_id = urd.lesson_id\n" +
                "    INNER JOIN dim_os_course c ON c.course_id = l.course_id and c.course_id = urd.course_id\n" +
                "  WHERE c.is_delete = '0' AND c.release_state = 1 and l.is_delete = '0' AND l.release_state = 1;");
    }
}
