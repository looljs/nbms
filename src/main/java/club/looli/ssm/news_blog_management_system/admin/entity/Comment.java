package club.looli.ssm.news_blog_management_system.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 新闻评论实体
 */
@Data
public class Comment {
    private Integer id; //id
    private String nickname; //昵称
    private String content; //内容
    private Date createTime; //评论时间
    private Integer newsId;//评论的新闻的id

    private News news;//评论的新闻
}
