package com.tyut.accesscontrol.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.mapper.AccessMapper;
import org.springframework.stereotype.Service;

/**
* @author mahua
* @description 针对表【access(出入表)】的数据库操作Service实现
* @createDate 2024-07-01 00:13:26
*/
@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access>
    implements AccessService{

}




