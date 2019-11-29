package club.looli.ssm.news_blog_management_system.home.controller;

import club.looli.ssm.news_blog_management_system.admin.entity.Comment;
import club.looli.ssm.news_blog_management_system.admin.entity.News;
import club.looli.ssm.news_blog_management_system.admin.entity.NewsCategory;
import club.looli.ssm.news_blog_management_system.admin.page.Page;
import club.looli.ssm.news_blog_management_system.admin.service.CommentService;
import club.looli.ssm.news_blog_management_system.admin.service.NewsCategoryService;
import club.looli.ssm.news_blog_management_system.admin.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
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
    @Autowired
    private CommentService commentService;

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
        map.put("size",3);
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
        mv.addObject("newsCategory",newsCategoryList.get(cid.toString()));
        mv.addObject("newsCategoryId",cid);
        mv.setViewName("home/news/category_list");
        return mv;
    }

    /**
     * 分页获取某个分类下的文章
     * @param page
     * @param cid
     * @return
     */
    @RequestMapping(value="/get_category_list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCategoryList(Page page,
                                               @RequestParam(name="cid",required=true) Integer cid
    ){
        List<NewsCategory> list = newsCategoryService.findList();
        Map<String,String> newsCategoryList = new HashMap<>();
        for (NewsCategory category : list){
            newsCategoryList.put(category.getId().toString(),category.getName());
        }

        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        queryMap.put("categoryId", cid);
        ret.put("type", "success");
        ret.put("newsList", newsService.findAllBySearch(queryMap));
        ret.put("newsCategoryMap",newsCategoryList);
        return ret;
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
        ret.put("type", "success");
        ret.put("newsList", newsService.findLastCommentNewsList(10));
        return ret;
    }

    /**
     * 根据关键字搜索新闻列表
     * @param mv
     * @param keyword
     * @return
     */
    @RequestMapping(value = "search_list",method = RequestMethod.GET)
    public ModelAndView searchList(ModelAndView mv,
                                     @RequestParam(name = "keyword") String keyword){
        Map<String,Object> map = new HashMap<>();
        map.put("start",0);
        map.put("size",3);
        map.put("title","%"+keyword+"%");
        //获取所有符合条件的新闻
        List<News> allBySearch = newsService.findAllBySearch(map);
        List<NewsCategory> list = newsCategoryService.findList();
        Map<String,String> newsCategoryList = new HashMap<>();
        for (NewsCategory category : list){
            newsCategoryList.put(category.getId().toString(),category.getName());
        }
        mv.addObject("newsList",allBySearch);
        mv.addObject("newsCategoryMap",newsCategoryList);
        mv.addObject("newsCategoryList",list);
        mv.addObject("keyword",keyword);
        mv.setViewName("home/news/search_list");
        return mv;
    }

    /**
     * 加载更多分页信息
     * @param page
     * @param keyword
     * @return
     */
    @RequestMapping(value="/get_search_list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSearchList(Page page,
                                               @RequestParam(name = "keyword") String keyword
    ){
        List<NewsCategory> list = newsCategoryService.findList();
        Map<String,String> newsCategoryList = new HashMap<>();
        for (NewsCategory category : list){
            newsCategoryList.put(category.getId().toString(),category.getName());
        }

        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        queryMap.put("title","%"+keyword+"%");
//        queryMap.put("categoryId", cid);
        ret.put("keyword",keyword);
        ret.put("type", "success");
        ret.put("newsList", newsService.findAllBySearch(queryMap));
        ret.put("newsCategoryMap",newsCategoryList);
        return ret;
    }

    /**
     * 分页获取某一文章的评论
     * @param page
     * @return
     */
    @RequestMapping(value="/get_comment_list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCommentList(Page page,Long newsId){
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("start", page.getStart());
        queryMap.put("size", page.getRows());
        queryMap.put("newsId", newsId);
        ret.put("type", "success");
        ret.put("commentList", commentService.findAllBySearch(queryMap));
        return ret;
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @RequestMapping(value="/comment_news",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commentNews(Comment comment){
        Map<String, Object> ret = new HashMap<String, Object>();
        if(comment == null){
            ret.put("type", "error");
            ret.put("msg", "请填写完整的评论信息！");
            return ret;
        }
        if(comment.getNewsId() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择一个文章进行评论！");
            return ret;
        }
        if(StringUtils.isEmpty(comment.getNickname())){
            ret.put("type", "error");
            ret.put("msg", "请填写昵称！");
            return ret;
        }
        if(StringUtils.isEmpty(comment.getContent())){
            ret.put("type", "error");
            ret.put("msg", "请填写评论内容！");
            return ret;
        }
        comment.setCreateTime(new Date());
        if(commentService.add(comment) <= 0){
            ret.put("type", "error");
            ret.put("msg", "评论失败，请联系管理员！");
            return ret;
        }
        //文章评论数加1
        newsService.updateCommentVolume(comment.getNewsId());
        ret.put("type", "success");
        ret.put("createTime", comment.getCreateTime());
        return ret;
    }
}
