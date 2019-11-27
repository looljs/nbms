package club.looli.ssm.news_blog_management_system.admin.service.impl;

import club.looli.ssm.news_blog_management_system.admin.dao.AuthorityDAO;
import club.looli.ssm.news_blog_management_system.admin.entity.Authority;
import club.looli.ssm.news_blog_management_system.admin.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限业务接口实现
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDAO authorityDAO;

    @Override
    public int addAuthority(List<Authority> authority) {
        return authorityDAO.add(authority);
    }

    @Override
    public int deleteAuthorityByRoleId(Integer roleId) {
        return authorityDAO.delete(roleId);
    }

    @Override
    public List<Authority> findAuthorityByRoleId(Integer roleId) {
        return authorityDAO.selectByRoleId(roleId);
    }
}
