package club.looli.ssm.news_blog_management_system.admin.service;

import club.looli.ssm.news_blog_management_system.admin.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色业务接口
 */
public interface RoleService {

 /**
  * 获取当前页面角色信息
  * @param search
  * @return
  */
 List<Role> findList(Map<String, Object> search);

 /**
  * 获取总数
  * @param name
  * @return
  */
 int findCount(String name);

 /**
  * 根据角色名获取角色
  * @param name
  * @return
  */
 Role findRoleByRoleName(String name);

 /**
  * 添加角色
  * @param role
  * @return
  */
 int add(Role role);

 /**
  * 修改角色信息
  * @param role
  * @return
  */
 int edit(Role role);

 /**
  * 删除角色
  * @param id
  * @return
  */
 int delete(Integer id);

 /**
  * 查看是否有权限
  * @param id
  * @return
  */
 int hasPermission(Integer id);

 /**
  * 获取所有角色
  * @return
  */
 List<Role> findAll();

}
