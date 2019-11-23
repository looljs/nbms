package club.looli.ssm.news_blog_management_system.entity;

import lombok.Data;

/**
 * 新闻分类实体
 */
@Data
public class NewsCategory {
    private Integer id;//id
    private String name; //分类名
    private Integer sort; // 排序
}
