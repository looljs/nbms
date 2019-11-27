package club.looli.ssm.news_blog_management_system.admin.service;

import club.looli.ssm.news_blog_management_system.admin.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 评论管理业务接口
 */
public interface CommentService {

    /**
     * 根据条件查询评论
     * @param map （分页信息，查询条件）
     * @return
     */
    List<Comment> findAllBySearch(Map<String, Object> map);

    /**
     * 根据条件查询评论总数
     * @param map （查询条件）
     * @return
     */
    int findCount(Map<String, Object> map);

    /**
     * 添加一条评论
     * @param comment
     * @return
     */
    int add(Comment comment);

    /**
     * 修改评论信息
     * @param comment
     * @return
     */
    int edit(Comment comment);

    /**
     * 删除评论信息
     * @param ids
     * @return
     */
    int delete(Integer[] ids);

    /**
     * 根据id查询评论信息
     * @param id
     * @return
     */
    Comment findById(Integer id);
}