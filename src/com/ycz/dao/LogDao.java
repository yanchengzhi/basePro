package com.ycz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.ycz.pojo.Log;

public interface LogDao {

    @Select("insert into log(content,create_time) values(#{content},#{createTimeStr})")
    void add(Log log);

    List<Log> findLogList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);

    void deleteLog(String ids);

}
