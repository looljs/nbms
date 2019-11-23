package club.looli.ssm.news_blog_management_system.entity;

import lombok.Data;

import java.util.Date;

/**
 * 日志实体
 */
@Data
public class Log {
    private Integer id;
    private String content; //日志内容
    private Date createTime; //创建时间
}
