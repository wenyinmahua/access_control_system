package com.tyut.accesscontrol.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.model.dto.AccessQueryDTO;
import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.model.vo.AccessVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author mahua
* @description 针对表【access(出入表)】的数据库操作Service
* @createDate 2024-07-03 01:25:43
*/
public interface AccessService extends IService<Access> {

	Page<AccessVO> getPageAccess(AccessQueryDTO accessQueryDTO);

	Boolean updateAccess(Access access);

	Boolean deleteAccessById(DeleteRequest deleteRequest);

	BaseResponse<Boolean> userSignInOrOut(MultipartFile file) throws IOException;
}
