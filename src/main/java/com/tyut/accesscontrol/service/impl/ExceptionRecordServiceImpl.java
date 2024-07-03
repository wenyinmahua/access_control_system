package com.tyut.accesscontrol.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.model.entity.ExceptionRecord;
import com.tyut.accesscontrol.service.ExceptionRecordService;
import com.tyut.accesscontrol.mapper.ExceptionRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author mahua
* @description 针对表【exception_record(异常记录表)】的数据库操作Service实现
* @createDate 2024-07-03 12:21:26
*/
@Service
public class ExceptionRecordServiceImpl extends ServiceImpl<ExceptionRecordMapper, ExceptionRecord>
    implements ExceptionRecordService{

}




