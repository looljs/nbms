package club.looli.ssm.news_blog_management_system.controller;


import club.looli.ssm.news_blog_management_system.entity.Menu;
import club.looli.ssm.news_blog_management_system.entity.News;
import club.looli.ssm.news_blog_management_system.page.Page;
import club.looli.ssm.news_blog_management_system.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 新闻管理控制器
 */
@Controller
@RequestMapping("/news")
public class NewsController {

    //当前操作系统分隔符
    private String sepa = File.separator;

    @Autowired
    private NewsService newsService;

    /**
     * 跳转到添加页面
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView newsAdd(ModelAndView modelAndView){
        modelAndView.setViewName("news/news_add");
        return modelAndView;
    }

    /**
     * 跳转新闻界面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/newsList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("news");
        modelAndView.addObject("news",menuList);
        modelAndView.setViewName("news/news_list");
        return modelAndView;
    }

    /**
     * 获取新闻列表
     * @param page
     * @param author
     * @param title
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> menuList(
            Page page,
            @RequestParam(name = "author",defaultValue = "",required = false) String author,
            @RequestParam(name = "title",defaultValue = "",required = false) String title
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> search = new HashMap<>();

        search.put("start",page.getStart());
        search.put("size",page.getRows());
        search.put("author","%"+author.trim()+"%");
        search.put("title","%"+title.trim()+"%");
        int count = newsService.findCount(search);
        List<News> data = newsService.findAllBySearch(search);

        map.put("type","success");
        map.put("rows",data);
        map.put("total",count);
        return map;
    }

    /**
     * 添加新闻
     * @param news 新闻信息
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(News news){
        Map<String,Object> map = new HashMap<>();
        if (news == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //添加
        newsService.add(news);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 修改新闻
     * @param news 新闻信息
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(News news){
        Map<String,Object> map = new HashMap<>();
        if (news == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //修改
        newsService.edit(news);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 删除新闻
     * @param id 要删除的新闻的id
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
        newsService.delete(id);
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }

    /**
     * 封面上传
     * @param multipartFile
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
    public Map<String,Object> uploadPhoto(@RequestParam("photo") MultipartFile multipartFile,
                                          HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //images
        String realPath = request.getSession().getServletContext().getRealPath("/")+"images"+sepa;
        File folder = new File(realPath);
        //不是目录,创建成目录
        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        //返回客户端文件系统中的原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()+
                originalFilename.substring(
                        originalFilename.lastIndexOf('.'),
                        originalFilename.length());
        try {
            multipartFile.transferTo(new File(folder,newName));
            map.put("type","success");
            map.put("filepath",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/images"+"/"+newName);
            map.put("msg","上传成功");
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("type","error");
        map.put("msg","上传失败");
        return map;
    }
}