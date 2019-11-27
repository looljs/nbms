package club.looli.ssm.news_blog_management_system.admin.service.impl;

import club.looli.ssm.news_blog_management_system.admin.dao.LogDAO;
import club.looli.ssm.news_blog_management_system.admin.entity.Log;
import club.looli.ssm.news_blog_management_system.admin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 日志业务接口实现类
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDAO logDAO;


    @Override
    public int add(Log log) {
        return logDAO.add(log);
    }

    @Override
    public List<Log> findList(Map<String, Object> map) {
        return logDAO.findList(map);
    }

    @Override
    public int findCount(String content) {
        return logDAO.findCount(content);
    }

    @Override
    public int delete(Integer[] logIds) {
        return logDAO.delete(logIds);
    }
}
