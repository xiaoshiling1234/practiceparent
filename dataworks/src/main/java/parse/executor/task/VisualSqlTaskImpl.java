package parse.executor.task;

import parse.executor.JoinRunDag;
import parse.executor.SqlTask;

@JoinRunDag(enable = true)
public class VisualSqlTaskImpl extends SqlTask {
    public VisualSqlTaskImpl() {
        sql.add("select \n" +
                "    b.name 学校, \n" +
                "    a.study_section_code ,\n" +
                "    if(a.study_section_code='GZ','高中',if(a.study_section_code='CZ','初中',if(a.study_section_code='XX','小学','幼儿园'))) 学段 ,\n" +
                "    d.code_type,\n" +
                "    d.date_code,\n" +
                "    count(DISTINCT if(a.resource_category=1,a.resource_id,null)) 应下载课堂实录数,\n" +
                "    count(DISTINCT if(a.resource_category=1,c.resource_id,null)) 实际下载课堂实录数,\n" +
                "    count(DISTINCT if(a.resource_category=2,a.resource_id,null)) 应下载课件数,\n" +
                "    count(DISTINCT if(a.resource_category=2,c.resource_id,null)) 实际下载课件数,\n" +
                "    GETDATE() update_time\n" +
                "from \n" +
                "dwd_os_user_sync_lesson_resource_detail a\n" +
                "join \n" +
                "ods_os_openschool_organization b\n" +
                "on a.organization_id =b.org_id\n" +
                "JOIN dim_pub_date_col_to_row d \n" +
                "ON d.day_id =CAST(to_char(a.first_release_time, 'yyyymmdd') AS INT)\n" +
                "left join\n" +
                "dwd_os_user_down_lesson_resource c \n" +
                "on a.user_id=c.user_id and a.resource_id = c.resource_id\n" +
                "and c.download_time<=DATEADD(a.first_release_time,14-WEEKDAY(a.first_release_time),'dd')\n" +
                "where a.resource_category in(1,2) \n" +
                "and a.column_id IN (365492434807562240,365495787511881728)\n" +
                "and  d.code_type='WEEK' and d.date_code > 202001\n" +
                "group by \tb.name, \n" +
                "            a.study_section_code ,\n" +
                "            d.date_code,\n" +
                "            d.code_type;");
    }
}
