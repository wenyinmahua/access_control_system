package com.tyut.accesscontrol.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.AccessQueryDTO;
import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.mapper.AccessMapper;
import org.springframework.stereotype.Service;

/**
* @author mahua
* @description 针对表【access(出入表)】的数据库操作Service实现
* @createDate 2024-07-03 01:25:43
*/
@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access>
    implements AccessService{

	@Override
	public Page<Access> getPageAccess(AccessQueryDTO accessQueryDTO) {
		Long id = accessQueryDTO.getId();
		Long userId = accessQueryDTO.getUserId();
		Date checkInTime = accessQueryDTO.getCheckInTime();
		Integer checkInStatus = accessQueryDTO.getCheckInStatus();
		Date checkOutTime = accessQueryDTO.getCheckOutTime();
		Integer checkOutStatus = accessQueryDTO.getCheckOutStatus();
		Integer flag = accessQueryDTO.getFlag();
		long current = accessQueryDTO.getCurrent();
		long pageSize = accessQueryDTO.getPageSize();
		QueryWrapper<Access> queryWrapper = new QueryWrapper<>();
		if (id != null) {
			queryWrapper.eq("id", id);
		}
		if (userId != null) {
			queryWrapper.eq("user_id", userId);
		}
		if (checkInTime != null) {
			queryWrapper.eq("check_in_time", checkInTime);
		}
		if (checkInStatus != null) {
			queryWrapper.eq("check_in_status", checkInStatus);
		}
		if (checkOutTime != null) {
			queryWrapper.eq("check_out_time", checkOutTime);
		}
		if (checkOutStatus != null) {
			queryWrapper.eq("check_out_status", checkOutStatus);
		}
		if (flag != null) {
			queryWrapper.eq("flag", flag);
		}
		return this.page(new Page<>(current, pageSize), queryWrapper);

	}

	@Override
	public Boolean updateAccess(Access access) {
		if (access == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UpdateWrapper<Access> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id",access.getId());
		return this.update(access,updateWrapper);
	}

	@Override
	public Boolean deleteAccessById(DeleteRequest deleteRequest) {
		Long id = deleteRequest.getId();
		if (id < 0 ){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return this.removeById(id);
	}
}




