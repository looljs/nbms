package club.looli.ssm.news_blog_management_system.controller;

import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ServerController extends HttpServlet {

    /**
     * 多文本编辑器我只能集成到这一步了，进一步没有能力完善，见谅
     * exec = exec.replaceAll("G:/projects/nbms/target/classes/static/", "http://localhost:8082/");
     * 这里不懂可以debug一下
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/admin/ueditor/jsp";
        try {
            response.setCharacterEncoding("UTF-8");
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            if(action != null && ("listfile".equals(action) || "listimage".equals(action))){
//                rootPath = rootPath.replace("G:/projects/nbms/target/classes/static/", "http://localhost:8082/");
                exec = exec.replaceAll("G:/projects/nbms/target/classes/static/", "http://localhost:8082/");
            }
            writer.write(exec);
//            writer.write(new ActionEnter(request,realPath).exec());
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
