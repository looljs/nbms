package club.looli.ssm.news_blog_management_system.service.impl;

import club.looli.ssm.news_blog_management_system.dao.RoleDAO;
import club.looli.ssm.news_blog_management_system.entity.Role;
import club.looli.ssm.news_blog_management_system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色业务实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<Role> findList(Map<String, Object> search) {
        return roleDAO.findList(search);
    }

    @Override
    public int findCount(String name) {
        return roleDAO.findCount(name);
    }

    @Override
    public Role findRoleByRoleName(String name) {
        return roleDAO.findRoleByRoleName(name);
    }

    @Override
    public int add(Role role) {
        return roleDAO.add(role);
    }

    @Override
    public int edit(Role role) {
        return roleDAO.edit(role);
    }

    @Override
    public int delete(Integer id) {
        return roleDAO.delete(id);
    }

    @Override
    public int hasPermission(Integer id) {
        return roleDAO.hasPermission(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDAO.findAll();
    }

}
