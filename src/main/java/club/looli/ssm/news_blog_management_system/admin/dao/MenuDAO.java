package club.looli.ssm.news_blog_management_system.admin.dao;

import club.looli.ssm.news_blog_management_system.admin.entity.Authority;
import club.looli.ssm.news_blog_management_system.admin.entity.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MenuDAO {

    /**
     * 保存菜单信息
     * @param menu
     * @return 返回影响的行数
     */
    @Insert("insert into menu (id,parentId,name,url,icon) values (#{id},#{parentId},#{name},#{url},#{icon})")
    int add(Menu menu);

    /**
     * 获取菜单
     * @param map
     * @return 返回符合条件的菜单信息
     * 去除分页
     */
//    @Select("select id,parentId,name,url,icon from menu where id != 0 and name like #{name} limit #{start} , #{size}")
    @Select("select id,parentId,name,url,icon from menu where id != 0 and name like #{name} ")
    List<Menu> findList(Map<String, Object> map);

    /**
     * 获取要显示的菜单总数
     * @param menuName
     * @return 返回符合条件的菜单的行数
     */
    @Select("select count(id) from menu where id != 0 and name like #{menuName}")
    int findCount(String menuName);

    /**
     * 获取顶级菜单列表
     * @return 返回顶级菜单列表
     */
    @Select("select id,parentId,name,url,icon from menu where parentId = 0")
    List<Menu> findTopMenuList();

    /**
     * 根据菜单名获取菜单信息
     * @param menuName
     * @return 返回菜单信息
     */
    @Select("select id,parentId,name,url,icon from menu where id != 0 and name = #{menuName}")
    Menu findMenuByMenuName(String menuName);

    /**
     * 获取表中最大的id
     * @return 表中最大的id
     */
    @Select("select max(id) from menu")
    int getId();

    /**
     * 修改菜单
     * @param menu
     * @return 返回影响的行数
     */
    @Update("update menu set parentId=#{parentId},name=#{name},url=#{url},icon=#{icon} where id=#{id}")
    int update(Menu menu);

    /**
     * 删除菜单
     * @param id
     * @return 返回影响的行数
     */
    @Delete("delete from menu where id = #{id}")
    int delete(Integer id);

    /**
     * 查询子菜单数量
     * @param id
     * @return
     */
    @Select("select count(id) from menu where parentId = #{id}")
    int selectSubmenu(Integer id);

    /**
     * 查询所有菜单
     * @return
     */
    @Select("select id,parentId,name,url,icon from menu where id != 0")
    List<Menu> getAll();

    /**
     * 查询角色拥有的菜单
     * @param authorityList
     * @return
     */
    @Select({
            "<script>"
            ,"select id,parentId,name,url,icon from menu where id != 0 and parentId =0 and id in (",
            "<foreach item='id' collection='ids' separator=','>",
                    "#{id.menuId}",
            "</foreach>",
            ")",
            "</script>"
    })
    List<Menu> findAllById(@Param("ids") List<Authority> authorityList);

    /**
     * 查询所有子菜单
     * @param authorityList
     * @return
     */
    @Select({
            "<script>"
            ,"select id,parentId,name,url,icon from menu where id != 0 and parentId !=0 and id in (",
            "<foreach item='id' collection='ids' separator=','>",
            "#{id.menuId}",
            "</foreach>",
            ")",
            "</script>"
    })
    List<Menu> findchildListById(@Param("ids") List<Authority> authorityList);
}
