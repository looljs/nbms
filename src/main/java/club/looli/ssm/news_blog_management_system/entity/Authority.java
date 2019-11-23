package club.looli.ssm.news_blog_management_system.entity;

import lombok.Data;

/**
 * 权限实体
 */
@Data
public class Authority {
    private Integer id;//id
    private Integer roleId; //角色id
    private Integer menuId; //菜单id
}
