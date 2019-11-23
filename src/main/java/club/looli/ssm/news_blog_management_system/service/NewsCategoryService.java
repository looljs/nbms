package club.looli.ssm.news_blog_management_system.service;


import club.looli.ssm.news_blog_management_system.entity.NewsCategory;

import java.util.List;
import java.util.Map;

/**
 * 新闻分类业务接口
 */
public interface NewsCategoryService {
    /**
     * 查询新闻分类
     * @param map
     * @return
     */
    List<NewsCategory> findAllBySearch(Map<String,Object> map);

    /**
     * 查询新闻分类总数
     * @param name
     * @return
     */
    int findCount(String name);

    /**
     * 添加一条新闻分类
     * @param newsCategory
     * @return
     */
    int add(NewsCategory newsCategory);

    /**
     * 根据分类名查询分类
     * @param name
     * @return
     */
    NewsCategory findByName(String name);

    /**
     * 修改新闻分类信息
     * @param newsCategory
     * @return
     */
    int edit(NewsCategory newsCategory);

    /**
     * 批量删除新闻分类
     * @param id
     * @return
     */
    int delete(Integer id);
}
