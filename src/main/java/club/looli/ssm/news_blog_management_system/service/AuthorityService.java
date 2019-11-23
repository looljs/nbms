package club.looli.ssm.news_blog_management_system.service;

import club.looli.ssm.news_blog_management_system.entity.Authority;

import java.util.List;

/**
 * 权限业务接口
 */
public interface AuthorityService {

    /**
     * 添加权限
     * @param authority
     * @return
     */
    int addAuthority(List<Authority> authority);

    /**
     * 删除权限根据角色id
     * @param roleId
     * @return
     */
    int deleteAuthorityByRoleId(Integer roleId);

    /**
     * 获取角色拥有的权限
     * @param roleId
     * @return
     */
    List<Authority> findAuthorityByRoleId(Integer roleId);
}
