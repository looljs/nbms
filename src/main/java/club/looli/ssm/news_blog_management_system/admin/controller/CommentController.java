package club.looli.ssm.news_blog_management_system.admin.controller;

import club.looli.ssm.news_blog_management_system.admin.entity.Comment;
import club.looli.ssm.news_blog_management_system.admin.entity.Menu;
import club.looli.ssm.news_blog_management_system.admin.page.Page;
import club.looli.ssm.news_blog_management_system.admin.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论管理控制器
 */

@RequestMapping("/comment")
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 跳转评论管理界面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/commentList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        Map<String, List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("comment");
        modelAndView.addObject("commentList",menuList);
        modelAndView.setViewName("comment/comment_list");
        return modelAndView;
    }

    /**
     * 获取评论列表
     * @param page
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> menuList(
            Page page,
            @RequestParam(name = "nickname",defaultValue = "",required = false) String nickname,
            @RequestParam(name = "content",defaultValue = "",required = false) String content
    ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> search = new HashMap<>();
        search.put("start",page.getStart());
        search.put("size",page.getRows());
        search.put("nickname","%"+nickname.trim()+"%");
        search.put("content","%"+content.trim()+"%");
        int count = commentService.findCount(search);
        List<Comment> data = commentService.findAllBySearch(search);
        map.put("type","success");
        map.put("rows",data);
        map.put("total",count);
        return map;
    }

    /**
     * 添加评论
     * @param comment 评论信息
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(Comment comment){
        Map<String,Object> map = new HashMap<>();
        if (comment == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        if(comment.getNewsId().equals("")){
            map.put("type","error");
            map.put("msg","评论的新闻不能为空");
            return map;
        }
        if(StringUtils.isEmpty(comment.getContent())){
            map.put("type","error");
            map.put("msg","内容不能为空");
            return map;
        }
        if(StringUtils.isEmpty(comment.getNickname())){
            map.put("type","error");
            map.put("msg","昵称不能为空");
            return map;
        }
        comment.setCreateTime(new Date());
        //添加
        int add = commentService.add(comment);
        if (add <= 0){
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 修改评论
     * @param comment 评论信息
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(Comment comment){
        Map<String,Object> map = new HashMap<>();
        if (comment == null){
            map.put("type","error");
            map.put("msg","数据绑定出错");
            return map;
        }
        //修改
        commentService.edit(comment);
        map.put("type","success");
        map.put("msg","修改成功");
        return map;
    }

    /**
     * 删除评论
     * @param ids 要删除的评论的ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> delete(@RequestParam("ids[]") Integer[] ids){
        Map<String,Object> map = new HashMap<>();
        if (ids == null || ids.length <= 0){
            map.put("type","error");
            map.put("msg","请选择删除的对象！");
            return map;
        }
        commentService.delete(ids);
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }

}
