package club.looli.ssm.news_blog_management_system.home.controller;

import club.looli.ssm.news_blog_management_system.admin.entity.News;
import club.looli.ssm.news_blog_management_system.admin.entity.NewsCategory;
import club.looli.ssm.news_blog_management_system.admin.service.NewsCategoryService;
import club.looli.ssm.news_blog_management_system.admin.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台首页控制器
 */
@RestController
@RequestMapping("/home/index")
public class IndexController {

    @Autowired
    private NewsCategoryService newsCategoryService;
    @Autowired
    private NewsService newsService;

    /**
     * 跳转到前台首页
     * @param mv
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mv){
        Map<String,Object> map = new HashMap<>();
        map.put("start",0);
        map.put("size",10);
        List<News> allBySearch = newsService.findAllBySearch(map);
        List<NewsCategory> list = newsCategoryService.findList();
        Map<String,String> newsCategoryList = new HashMap<>();
        for (NewsCategory category : list){
            newsCategoryList.put(category.getId().toString(),category.getName());
        }
        mv.addObject("newsList",allBySearch);
        mv.addObject("newsCategoryMap",newsCategoryList);
        mv.addObject("newsCategoryList",list);
        mv.setViewName("home/index/index");
        return mv;
    }

    


}
