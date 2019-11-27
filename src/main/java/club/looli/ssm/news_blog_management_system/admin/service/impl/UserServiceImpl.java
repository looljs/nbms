package club.looli.ssm.news_blog_management_system.admin.service.impl;

import club.looli.ssm.news_blog_management_system.admin.dao.UserDAO;
import club.looli.ssm.news_blog_management_system.admin.entity.User;
import club.looli.ssm.news_blog_management_system.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户业务接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public List<User> findList(Map<String, Object> search) {
        return userDAO.findList(search);
    }

    @Override
    public int findCount(Map<String, Object> search) {
        return userDAO.findCount(search);
    }

    @Override
    public int add(User user) {
        return userDAO.add(user);
    }

    @Override
    public int edit(User user) {
        return userDAO.edit(user);
    }

    @Override
    public int delete(Integer[] userIds) {
        return userDAO.delete(userIds);
    }

    @Override
    public int editPassword(User user) {
        return userDAO.editPassword(user);
    }


}
