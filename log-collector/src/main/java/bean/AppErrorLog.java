package bean;

import lombok.Data;

/**
 * 错误日志
 */
@Data
public class AppErrorLog extends AppBase {
    private String errorBrief;    //错误摘要
    private String errorDetail;   //错误详情
}
