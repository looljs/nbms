package club.looli.ssm.news_blog_management_system.dao;

import club.looli.ssm.news_blog_management_system.entity.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 新闻管理持久化接口
 */
@Mapper
@Repository
public interface NewsDAO {

//    public News find(Long id);
//    public int updateCommentNumber(Long id);
//    public int updateViewNumber(Long id);
//    public List<News> findLastCommentList(int pageSize);

    /**
     * 根据条件查询新闻
     * @param map （分页信息，查询条件）
     * @return
     */
    @Select({
            "<script>",
            "select id,title,summary,photo,author,categoryId,tags,content,pageViews,commentVolume,createTime" +
                    " from news " +
                    "<where>",
            "<if test='title != null'>",
            "title like #{title} ",
            "</if>",
            "<if test='author != null'>",
            " and author like #{author} ",
            "</if>",
            "</where>",
            " order by createTime desc limit #{start},#{size} ",
            "</script>"
    })
    List<News> findAllBySearch(Map<String, Object> map);

    /**
     * 根据条件查询新闻总数
     * @param map （查询条件）
     * @return
     */
    @Select({
            "<script>",
            "select count(id)" +
                    " from news " +
                    "<where>",
            "<if test='title != null'>",
            "title like #{title} ",
            "</if>",
            "<if test='author != null'>",
            "and author like #{author} ",
            "</if>",
            "</where>",
            "</script>"
    })
    int findCount(Map<String, Object> map);

    /**
     * 添加一条新闻
     * @param news
     * @return
     */
    @Insert("insert into news (title,categoryId,tags,summary,photo,author,content,pageViews,commentVolume,createTime) values (#{title},#{categoryId},#{tags},#{summary},#{photo},#{author},#{content},#{pageViews},#{commentVolume},#{createTime})")
    int add(News news);

    /**
     * 修改新闻信息
     * @param news
     * @return
     */
    @Update("update news set title=#{title},categoryId=#{categoryId},tags=#{tags},summary=#{summary},photo=#{photo},author=#{author},content=#{content},pageViews=#{pageViews},commentVolume=#{commentVolume} where id = #{id}")
    int edit(News news);

    /**
     * 删除新闻
     * @param id
     * @return
     */
    @Delete("delete from news where id = #{id}")
    int delete(Integer id);
}