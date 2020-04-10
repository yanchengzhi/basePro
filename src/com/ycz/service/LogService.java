package com.ycz.service;

import java.util.List;
import java.util.Map;

import com.ycz.pojo.Log;

public interface LogService {

    void add(Log log);
    
    void addLog(String roleName,String username,String str);

    List<Log> findLogList(Map<String, Object> map);
    
    int getTotal(Map<String, Object> map);

    void deleteLog(String ids);
    
}
