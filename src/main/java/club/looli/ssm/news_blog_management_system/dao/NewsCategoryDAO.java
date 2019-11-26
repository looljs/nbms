package club.looli.ssm.news_blog_management_system.dao;

import club.looli.ssm.news_blog_management_system.entity.NewsCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 新闻分类持久化接口
 */

@Mapper
@Repository
public interface NewsCategoryDAO {

    /**
     * 查询新闻分类
     * @param map （分页信息，分类名）
     * @return
     */
    @Select("select id,name,sort from news_category where name like #{name} order by sort asc limit #{start},#{size} ")
    List<NewsCategory> findAllBySearch(Map<String,Object> map);

    /**
     * 根据条件查询新闻分类总数
     * @param name
     * @return
     */
    @Select("select count(id) from news_category where name like #{name}")
    int findCount(String name);

    /**
     * 添加一条新闻分类
     * @param newsCategory
     * @return
     */
    @Insert("insert into news_category (name,sort) values (#{name},#{sort})")
    int add(NewsCategory newsCategory);

    /**
     * 根据分类名查询分类信息
     * @param name
     * @return
     */
    @Select("select id,name,sort from news_category where name = #{name}")
    NewsCategory findByName(String name);

    /**
     * 修改新闻分类信息
     * @param newsCategory
     * @return
     */
    @Update("update news_category set name=#{name},sort=#{sort} where id = #{id}")
    int edit(NewsCategory newsCategory);

    /**
     * 批量删除新闻分类
     * @param id
     * @return
     */
    @Delete("delete from news_category where id = #{id}")
    int delete(Integer id);

    @Select("select id,name,sort from news_category order by sort asc ")
    List<NewsCategory> findList();

}
