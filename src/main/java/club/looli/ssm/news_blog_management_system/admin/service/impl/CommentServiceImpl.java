package club.looli.ssm.news_blog_management_system.admin.service.impl;

import club.looli.ssm.news_blog_management_system.admin.dao.CommentDAO;
import club.looli.ssm.news_blog_management_system.admin.entity.Comment;
import club.looli.ssm.news_blog_management_system.admin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评论管理业务实现
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public List<Comment> findAllBySearch(Map<String, Object> map) {
        return commentDAO.findAllBySearch(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return commentDAO.findCount(map);
    }

    @Override
    public int add(Comment comment) {
        return commentDAO.add(comment);
    }

    @Override
    public int edit(Comment comment) {
        return commentDAO.edit(comment);
    }

    @Override
    public int delete(Integer[] ids) {
        return commentDAO.delete(ids);
    }

    @Override
    public Comment findById(Integer id) {
        return commentDAO.findById(id);
    }
}
