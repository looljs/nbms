package club.looli.ssm.news_blog_management_system.admin.service;

import club.looli.ssm.news_blog_management_system.admin.entity.News;

import java.util.List;
import java.util.Map;

/**
 * 新闻管理业务接口
 */
public interface NewsService {
    /**
     * 根据条件查询新闻
     * @param map （分页信息，查询条件）
     * @return
     */
    List<News> findAllBySearch(Map<String, Object> map);

    /**
     * 根据条件查询新闻总数
     * @param map （查询条件）
     * @return
     */
    int findCount(Map<String, Object> map);

    /**
     * 添加一条新闻
     * @param news
     * @return
     */
    int add(News news);

    /**
     * 修改新闻信息
     * @param news
     * @return
     */
    int edit(News news);

    /**
     * 删除新闻
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 根据id查询新闻信息
     * @param id
     * @return
     */
    News findById(Integer id);

    /**
     * 更新新闻浏览量
     * @param id
     */
    void updatePageViews(Integer id);
}
