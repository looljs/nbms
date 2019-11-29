package club.looli.ssm.news_blog_management_system.home.controller;

import club.looli.ssm.news_blog_management_system.admin.entity.News;
import club.looli.ssm.news_blog_management_system.admin.entity.NewsCategory;
import club.looli.ssm.news_blog_management_system.admin.service.NewsCategoryService;
import club.looli.ssm.news_blog_management_system.admin.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 获取网站基本信息
     * @return
     */
    @RequestMapping(value="/get_site_info",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSiteInfo(){
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("type", "success");
        retMap.put("totalArticle", newsService.getTotal());
        retMap.put("siteDays", getDays("2019-11-29"));
        return retMap;
    }

    private Integer getDays(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = sdf.parse(data);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date endDate = new Date();
        Integer time = Math.toIntExact((endDate.getTime() - startDate.getTime()) / 1000 / 3600 / 24);
        return time;
    }
}
