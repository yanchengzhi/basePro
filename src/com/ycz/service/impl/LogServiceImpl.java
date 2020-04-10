package com.ycz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycz.dao.LogDao;
import com.ycz.pojo.Log;
import com.ycz.service.LogService;

@Service
public class LogServiceImpl implements LogService {
    
    @Autowired
    private LogDao lDao;

    @Override
    public void add(Log log) {
        lDao.add(log);
    }

    @Override
    public List<Log> findLogList(Map<String, Object> map) {
        return lDao.findLogList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map) {
        return lDao.getTotal(map);
    }

    @Override
    public void deleteLog(String ids) {
        lDao.deleteLog(ids);
    }

    @Override
    public void addLog(String roleName,String username,String str) {
        StringBuilder sb = new StringBuilder("");
        sb.append(roleName).append("¡¾").append(username).append("¡¿").append(str);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log log = new Log();
        log.setContent(sb.toString());
        log.setCreateTimeStr(sdf.format(new Date()));
        lDao.add(log);
    }

}
