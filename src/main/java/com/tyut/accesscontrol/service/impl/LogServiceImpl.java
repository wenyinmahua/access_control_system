package com.tyut.accesscontrol.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.model.entity.Log;
import com.tyut.accesscontrol.service.LogService;
import com.tyut.accesscontrol.mapper.LogMapper;
import org.springframework.stereotype.Service;

/**
* @author mahua
* @description 针对表【log(日志表)】的数据库操作Service实现
* @createDate 2024-07-01 00:14:34
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{

}




