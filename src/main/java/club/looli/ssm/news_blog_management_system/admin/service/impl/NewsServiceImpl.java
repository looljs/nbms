package club.looli.ssm.news_blog_management_system.admin.service.impl;

import club.looli.ssm.news_blog_management_system.admin.dao.NewsDAO;
import club.looli.ssm.news_blog_management_system.admin.entity.News;
import club.looli.ssm.news_blog_management_system.admin.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 新闻管理业务接口实现
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDAO newsDAO;

    @Override
    public List<News> findAllBySearch(Map<String, Object> map) {
        return newsDAO.findAllBySearch(map);
    }

    @Override
    public int findCount(Map<String, Object> map) {
        return newsDAO.findCount(map);
    }

    @Override
    public int add(News news) {
        return newsDAO.add(news);
    }

    @Override
    public int edit(News news) {
        return newsDAO.edit(news);
    }

    @Override
    public int delete(Integer id) {
        return newsDAO.delete(id);
    }

    @Override
    public News findById(Integer id) {
        return newsDAO.findById(id);
    }

    @Override
    public void updatePageViews(Integer id) {
        newsDAO.updatePageViews(id);
    }
}
