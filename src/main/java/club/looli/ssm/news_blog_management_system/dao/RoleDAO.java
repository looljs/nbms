package club.looli.ssm.news_blog_management_system.dao;

import club.looli.ssm.news_blog_management_system.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色DAO接口
 */
@Mapper
@Repository
public interface RoleDAO {

    /**
     * 查询符合条件的信息
     * @param search
     * @return
     */
    @Select("select id,name,remark from role where name like #{name} limit #{start} , #{size}")
    List<Role> findList(Map<String, Object> search);

    /**
     * 查询符合条件的总数
     * @param name
     * @return
     */
    @Select("select count(id) from role where name like #{name}")
    int findCount(String name);


    /**
     * 根据角色名获取角色
     * @param name
     * @return
     */
    @Select("select id,name,remark from role where name = #{name}")
    Role findRoleByRoleName(String name);

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Insert("insert into role" +
            "(name,remark) values (#{name},#{remark}) ")
    int add(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Update("update role set name=#{name},remark=#{remark} where id =#{id}")
    int edit(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Delete("delete from role where  id = #{id}")
    int delete(Integer id);

    /**
     * 获取角色的权限总数
     * @param id
     * @return
     */
    @Select("select count(id) from authority where roleId = #{id}")
    int hasPermission(Integer id);

    /**
     * 查询所有角色
     * @return
     */
    @Select("select id,name from role")
    List<Role> findAll();

}
