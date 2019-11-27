package club.looli.ssm.news_blog_management_system.admin.controller;


import club.looli.ssm.news_blog_management_system.admin.entity.Menu;
import club.looli.ssm.news_blog_management_system.admin.entity.NewsCategory;
import club.looli.ssm.news_blog_management_system.admin.page.Page;
import club.looli.ssm.news_blog_management_system.admin.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻分类控制器
 */
@Controller
@RequestMapping("/news_category")
public class NewsCategoryController {



    @Autowired
    private NewsCategoryService categoryService;


    /**
     * 跳转分类管理
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/categoryList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("news_category");
        modelAndView.addObject("news_category",menuList);
        modelAndView.setViewName("news/category_list");
        return modelAndView;
    }

    /**
     * 获取新闻分类列表
     * @param page
     * @param name
     * @param request
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> menuList(
            Page page,
            @RequestParam(name = "name",defaultValue = "",required = false) String name,
            HttpServletRequest request
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> search = new HashMap<>();

        search.put("start",page.getStart());
        search.put("size",page.getRows());
        search.put("name","%"+name.trim()+"%");
        List<NewsCategory> data = categoryService.findAllBySearch(search);
        int count = categoryService.findCount((String) search.get("name"));

        map.put("type","success");
        map.put("rows",data);
        map.put("total",count);
        return map;
    }

    /**
     * 添加新闻分类
     * @param newsCategory 新闻分类信息
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(NewsCategory newsCategory){
        Map<String,Object> map = new HashMap<>();
        if (newsCategory == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //判断分类是否存在
        NewsCategory newsCategory2 = categoryService.findByName(newsCategory.getName());
        if (newsCategory2 != null){
            map.put("type","error");
            map.put("msg","分类信息已存在！");
            return map;
        }
        //添加分类
        categoryService.add(newsCategory);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 修改新闻分类
     * @param newsCategory 新闻分类信息
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(NewsCategory newsCategory){
        Map<String,Object> map = new HashMap<>();
        if (newsCategory == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //判断分类是否存在
        NewsCategory newsCategory2 = categoryService.findByName(newsCategory.getName());
        if (newsCategory2 !=null && !newsCategory2.getId().equals(newsCategory.getId())){
            map.put("type","error");
            map.put("msg","分类信息已存在！");
            return map;
        }
        //修改分类
        categoryService.edit(newsCategory);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 删除新闻分类
     * @param id 要删除的新闻分类的id集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> delete(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (id == null){
            map.put("type","error");
            map.put("msg","请选择删除的对象！");
            return map;
        }
        categoryService.delete(id);
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }

    /**
     * 获取新闻分类列表
     * @return
     */
    @RequestMapping(value = "categorys" ,method = RequestMethod.POST)
    @ResponseBody
    public List<NewsCategory> categorys(){
        return categoryService.findList();
    }

    @RequestMapping(value = "/list2",method = RequestMethod.POST)
    @ResponseBody
    public List<NewsCategory> list2(){
        List<NewsCategory> all = categoryService.findList();
        all.add(0,new NewsCategory(-1,"全部"));
        return all;
    }

}
