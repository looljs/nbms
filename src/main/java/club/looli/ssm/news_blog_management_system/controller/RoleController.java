package club.looli.ssm.news_blog_management_system.controller;

import club.looli.ssm.news_blog_management_system.entity.Authority;
import club.looli.ssm.news_blog_management_system.entity.Menu;
import club.looli.ssm.news_blog_management_system.entity.Role;
import club.looli.ssm.news_blog_management_system.page.Page;
import club.looli.ssm.news_blog_management_system.service.AuthorityService;
import club.looli.ssm.news_blog_management_system.service.MenuService;
import club.looli.ssm.news_blog_management_system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthorityService authorityService;

    /**
     * 跳转角色列表
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/roleList",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request){
        //
        Map<String,List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("role");
//        request.getSession().setAttribute("menu",menuList);
        //
        modelAndView.addObject("role",menuList);
        modelAndView.setViewName("role/role_list");
        return modelAndView;
    }

    /**
     * 获取角色信息
     * @param page
     * @param name
     * @param request
     * @return
     */
    @RequestMapping(value = "/listRoles",method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> list(
            Page page,
            @RequestParam(name = "name",defaultValue = "") String name, HttpServletRequest request
            ){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> search = new HashMap<>();
        search.put("start",page.getStart());
        search.put("size",page.getRows());
        search.put("name","%"+name+"%");
        List<Role> data = roleService.findList(search);
        int count = roleService.findCount("%" + name + "%");
        //
        Map<String,List<Menu>> map1 = (Map<String, List<Menu>>) request.getSession().getAttribute("map");
        List<Menu> menuList = map1.get("role");
        request.getSession().setAttribute("role",menuList);
        //
        map.put("type","success");
        map.put("rows",data);
        map.put("total",count);
        return map;
    }


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public List<Role> list(){
        List<Role> all = roleService.findAll();
        all.add(0,new Role(-1,"全部"));
        return all;
    }
    @RequestMapping(value = "/list2",method = RequestMethod.POST)
    @ResponseBody
    public List<Role> list2(){
        List<Role> all = roleService.findAll();
        return all;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Role role){
        Map<String,String> map = new HashMap<>();
        if (role == null){
            map.put("type","error");
            map.put("msg","系统错误");
            return map;
        }
        if (StringUtils.equals("",role.getName())){
            map.put("type","error");
            map.put("msg","系统错误");
            return map;
        }
        //判断角色名是否可用
        Role role1 = roleService.findRoleByRoleName(role.getName());
        if (role1 != null){
            map.put("type","error");
            map.put("msg","角色名已经存在");
            return map;
        }
        roleService.add(role);
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(Role role){
        Map<String,Object> map = new HashMap<>();
        if (role == null){
            map.put("type","error");
            map.put("msg","系统错误");
            return map;
        }
        if (StringUtils.equals("",role.getName())){
            map.put("type","error");
            map.put("msg","系统错误");
            return map;
        }
        //判断角色名是否可用
        Role role1 = roleService.findRoleByRoleName(role.getName());
        if (role1 != null && !role.getId().equals(role1.getId())){
            map.put("type","error");
            map.put("msg","角色名已经存在");
            return map;
        }
        roleService.edit(role);
        map.put("type","success");
        map.put("msg","修改成功");
        return map;
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(@RequestParam(name = "id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (roleService.hasPermission(id)>0){
            map.put("type","error");
            map.put("msg","该角色存在权限，无法直接删除");
            return map;
        }
        roleService.delete(id);
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }


    /**
     * 获取所有菜单信息
     * @return
     */
    @RequestMapping(value = "/get_all_menu" ,method = RequestMethod.POST)
    @ResponseBody
    List<Menu> getAllMenu(){
        return menuService.getAll();
    }

    /**
     * 获取某个角色的所有权限
     * @param roleId
     * @return
     */
    @RequestMapping(value="/get_role_authority",method=RequestMethod.POST)
    @ResponseBody
    public List<Authority> getRoleAuthority(
            @RequestParam(name="roleId",required=true) Integer roleId
    ){
        return authorityService.findAuthorityByRoleId(roleId);
    }


    /**
     * 添加权限
     * @param ids
     * @param roleId
     * @return
     */
    @RequestMapping(value="/add_authority",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAuthority(
            @RequestParam(name="ids",required=true) String ids,
            @RequestParam(name="roleId",required=true) Integer roleId
    ){
        Map<String,Object> map = new HashMap<String, Object>();
        if(StringUtils.isEmpty(ids)){
            authorityService.deleteAuthorityByRoleId(roleId);
            map.put("type", "success");
            map.put("msg", "权限清空");
            return map;
        }
        if(roleId == null){
            map.put("type", "error");
            map.put("msg", "请选择相应的角色！");
            return map;
        }
        //装换为数组
        if(ids.contains(",")){
            ids = ids.substring(0,ids.length()-1);
        }
        String[] idArr = ids.split(",");
        //删除角色之前的权限
        if(idArr.length > 0){
            authorityService.deleteAuthorityByRoleId(roleId);
        }
        List<Authority> authorityList = new ArrayList<>();
        for(String id:idArr){
            Authority authority = new Authority();
            authority.setMenuId(Integer.valueOf(id));
            authority.setRoleId(roleId);
            authorityList.add(authority);
        }
        //重新批量添加
        authorityService.addAuthority(authorityList);
        map.put("type", "success");
        map.put("msg", "权限编辑成功！");
        return map;
    }

}
