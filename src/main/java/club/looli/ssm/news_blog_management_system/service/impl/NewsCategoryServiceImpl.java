package club.looli.ssm.news_blog_management_system.service.impl;

import club.looli.ssm.news_blog_management_system.dao.NewsCategoryDAO;
import club.looli.ssm.news_blog_management_system.entity.NewsCategory;
import club.looli.ssm.news_blog_management_system.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 新闻分类业务实现
 */

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryDAO categoryDAO;

    @Override
    public List<NewsCategory> findAllBySearch(Map<String, Object> map) {
        return categoryDAO.findAllBySearch(map);
    }

    @Override
    public int findCount(String name) {
        return categoryDAO.findCount(name);
    }

    @Override
    public int add(NewsCategory newsCategory) {
        return categoryDAO.add(newsCategory);
    }

    @Override
    public NewsCategory findByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public int edit(NewsCategory newsCategory) {
        return categoryDAO.edit(newsCategory);
    }

    @Override
    public int delete(Integer id) {
        return categoryDAO.delete(id);
    }

    @Override
    public List<NewsCategory> findList() {
        return categoryDAO.findList();
    }
}
