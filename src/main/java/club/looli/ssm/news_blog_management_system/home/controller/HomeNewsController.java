package club.looli.ssm.news_blog_management_system.home.controller;

import club.looli.ssm.news_blog_management_system.admin.entity.News;
import club.looli.ssm.news_blog_management_system.admin.entity.NewsCategory;
import club.looli.ssm.news_blog_management_system.admin.page.Page;
import club.looli.ssm.news_blog_management_system.admin.service.NewsCategoryService;
import club.looli.ssm.news_blog_management_system.admin.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home/news")
public class HomeNewsController {

    @Autowired
    private NewsCategoryService newsCategoryService;
    @Autowired
    private NewsService newsService;

    /**
     * 根据分类显示新闻列表
     * @param mv
     * @param page
     * @param cid
     */
    @RequestMapping(value = "category_list",method = RequestMethod.GET)
    public ModelAndView categoryList(ModelAndView mv,
                             Page page,
                             @RequestParam(name = "cid") Integer cid){
        Map<String,Object> map = new HashMap<>();
        map.put("start",0);
        map.put("size",10);
        if (cid != -1){
            map.put("categoryId",cid);
        }
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
     * 查看新闻详情
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="/detail",method=RequestMethod.GET)
    public ModelAndView detail(ModelAndView model,Integer id){
        List<NewsCategory> list = newsCategoryService.findList();
        Map<String,String> newsCategoryList = new HashMap<>();
        for (NewsCategory category : list){
            newsCategoryList.put(category.getId().toString(),category.getName());
        }
//        model.addObject("newsCategoryList", newsCategoryService.findList());
        model.addObject("newsCategoryList",list);
        News news = newsService.findById(id);
        model.addObject("news", news);
        model.addObject("title", news.getTitle());
        model.addObject("tags", news.getTags().split(","));
        model.setViewName("home/news/detail");
        model.addObject("newsCategoryMap",newsCategoryList);
        //查看数加1
        newsService.updatePageViews(id);
        return model;
    }

    /**
     * 获取按评论数排序的最新n条信息
     * @return
     */
    @RequestMapping(value="/last_comment_list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> lastCommentList(){
        Map<String, Object> ret = new HashMap<String, Object>();
//        ret.put("type", "success");
//        ret.put("newsList", newsService.findLastCommentList(10));
        return ret;
    }


}
