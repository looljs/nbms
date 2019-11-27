package club.looli.ssm.news_blog_management_system.admin.dao;

import club.looli.ssm.news_blog_management_system.admin.entity.Log;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 日志持久化接口
 */
@Mapper
@Repository
public interface LogDAO {

    /**
     * 添加日志信息
     * @param log
     * @return返回影响的行数
     */
    @Insert("insert into log (id,content,createTime) values (#{id},#{content},#{createTime})")
    int add(Log log);

    /**
     * 查询日志信息
     * @param map
     * @return 返回日志信息
     */
    @Select("select id,content,createTime from log where content like #{content} limit #{start} , #{size} ")
    List<Log> findList(Map<String, Object> map);

    /**
     * 获取要显示的日志总数
     * @param content
     * @return 返回符合条件的日志总数
     */
    @Select("select count(id) from log where content like #{content}")
    int findCount(String content);

    /**
     * 批量删除日志信息
     * @param logIds
     * @return 返回影响的行数
     */
    @Delete({
            "<script>",
            "delete from log where id in (",
            "<foreach item='id' collection='ids' separator=','>",
            "#{id}",
            "</foreach>",
            ")",
            "</script>"
    })
    int delete(@Param("ids") Integer[] logIds);
}
