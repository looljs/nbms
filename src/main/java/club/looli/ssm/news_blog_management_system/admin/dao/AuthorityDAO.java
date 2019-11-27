package club.looli.ssm.news_blog_management_system.admin.dao;

import club.looli.ssm.news_blog_management_system.admin.entity.Authority;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限持久化接口
 */
@Mapper
@Repository
public interface AuthorityDAO {

    /**
     * 添加权限
     * @param authority
     * @return 返回影响的行数
     */
    @Insert({
            "<script>" +
                "insert into authority (roleId,menuId) values",
                "<foreach item=\"item\" collection=\"authority\"\n" +
                        "     separator=\",\">\n" +
                        "       ( #{item.roleId},#{item.menuId})\n" +
                        "  </foreach>",
            "</script>"
    })
    int add(@Param("authority") List<Authority> authority);

    /**
     * 根据角色id删除权限
     * @param roleId
     * @return 影响的行数
     */
    @Delete("delete from authority where roleId = #{roleId}")
    int delete(Integer roleId);

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return 返回该角色的所有权限
     */
    @Select("select id,roleId,menuId from authority where roleId = #{roleId}")
    List<Authority> selectByRoleId(Integer roleId);
}
