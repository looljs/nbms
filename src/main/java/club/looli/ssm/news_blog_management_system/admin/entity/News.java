package club.looli.ssm.news_blog_management_system.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * 新闻实体
 */
@Data
public class News {
    private Integer id;
    private String title;//标题
    private Integer categoryId;//分类id
    private String tags;//标签
    private String summary;//摘要
    private String photo;//新闻封面图片
    private String author;//作者
    private String content;//新闻内容
    private Integer pageViews = 0;//浏览量
    private Integer commentVolume = 0;//评论量
    private Date createTime;//发布时间
    private NewsCategory newsCategory;//分类实体
}
