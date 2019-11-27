package club.looli.ssm.news_blog_management_system.admin.dao;

import club.looli.ssm.news_blog_management_system.admin.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 评论管理持久化接口
 */
@Mapper
@Repository
public interface CommentDAO {

    /**
     * 根据条件查询评论
     * @param map （分页信息，查询条件）
     * @return
     */
    @Select({
            "<script>",
            "select id,nickname,content,createTime,newsId" +
                    " from comment " +
                    "<where>",
            "<if test='nickname != null'>",
            " nickname like #{nickname} ",
            "</if>",
            "<if test='content != null'>",
            " and content like #{content} ",
            "</if>",
//            "<if test='author != null'>",
//            " and author like #{author} ",
//            "</if>",
//            "<if test='categoryId != null'>",
//            " and categoryId = #{categoryId} ",
//            "</if>",
            "</where>",
            " order by createTime desc limit #{start},#{size} ",
            "</script>"
    })
    @Results(id="findAllBySearch",value = {
            @Result(id = true , column = "id" ,property = "id"),
            @Result(column = "nickname" ,property = "nickname"),
            @Result(column = "content" ,property = "content"),
            @Result(column = "createTime" ,property = "createTime"),
            @Result(column = "newsId" ,property = "newsId"),
            @Result(property = "news" , column = "newsId" ,one = @One(select = "club.looli.ssm.news_blog_management_system.admin.dao.NewsDAO.findById", fetchType = FetchType.EAGER)),
    })
    List<Comment> findAllBySearch(Map<String, Object> map);

    /**
     * 根据条件查询评论总数
     * @param map （查询条件）
     * @return
     */
    @Select({
            "<script>",
            "select count(id)" +
                    " from comment " +
                    "<where>",
            "<if test='nickname != null'>",
            "nickname like #{nickname} ",
            "</if>",
            "<if test='content != null'>",
            " and content like #{content} ",
            "</if>",
//            "<if test='author != null'>",
//            "and author like #{author} ",
//            "</if>",
//            "<if test='categoryId != null'>",
//            " and categoryId = #{categoryId} ",
//            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String, Object> map);

    /**
     * 添加一条评论
     * @param comment
     * @return
     */
//    @Insert("insert into news (title,categoryId,tags,summary,photo,author,content,pageViews,commentVolume,createTime) values (#{title},#{categoryId},#{tags},#{summary},#{photo},#{author},#{content},#{pageViews},#{commentVolume},#{createTime})")
    @Insert("insert into comment (nickname,content,createTime,newsId) values (#{nickname},#{content},#{createTime},#{newsId})")
    int add(Comment comment);

    /**
     * 修改评论信息
     * @param comment
     * @return
     */
    @Update("update comment set nickname=#{nickname},content=#{content},newsId=#{newsId} where id = #{id}")
    int edit(Comment comment);

    /**
     * 删除评论信息
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
            "delete from comment where id in ( ",
            "<foreach item='id' collection='ids' separator=','>",
            "#{id}",
            "</foreach>",
            ")",
            "</script>"
    })
    int delete(@Param("ids") Integer[] ids);

    /**
     * 根据id查询评论信息
     * @param id
     * @return
     */
    @Select("select id,nickname,content,createTime,newsId from comment where id = #{id}")
    Comment findById(Integer id);
}
