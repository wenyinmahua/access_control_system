package com.tyut.accesscontrol.service.impl;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyut.accesscontrol.common.DeleteRequest;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.dto.AccessQueryDTO;
import com.tyut.accesscontrol.model.entity.Access;
import com.tyut.accesscontrol.model.vo.AccessVO;
import com.tyut.accesscontrol.service.AccessService;
import com.tyut.accesscontrol.mapper.AccessMapper;
import com.tyut.accesscontrol.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author mahua
* @description 针对表【access(出入表)】的数据库操作Service实现
* @createDate 2024-07-03 01:25:43
*/
@Service
@Slf4j
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access>
    implements AccessService{

	@Resource
	private UserService userService;

	@Override
	public Page<AccessVO> getPageAccess(AccessQueryDTO accessQueryDTO) {
		Long id = accessQueryDTO.getId();
		String username = accessQueryDTO.getUsername();
		Date checkInTime = accessQueryDTO.getCheckInTime();
		Integer checkInStatus = accessQueryDTO.getCheckInStatus();
		Date checkOutTime = accessQueryDTO.getCheckOutTime();
		Integer checkOutStatus = accessQueryDTO.getCheckOutStatus();
		long current = accessQueryDTO.getCurrent();
		long pageSize = accessQueryDTO.getPageSize();
		QueryWrapper<Access> queryWrapper = new QueryWrapper<>();
		if (id != null) {
			queryWrapper.eq("id", id);
		}
		if (username != null) {
			List<Long> userIds = userService.getUserIdsByName(username);
			if (!userIds.isEmpty()){
				queryWrapper.in("userId", userIds);
			}
		}
		if (checkInTime != null) {
			LocalDate nextDay = checkInTime.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().plusDays(1);
			queryWrapper.gt("checkInTime", checkInTime);
			log.error(nextDay.toString());
			queryWrapper.lt("thisDay", nextDay);
		}else if (StringUtils.isEmpty(username)){
			LocalDate localDate = LocalDate.now();
			queryWrapper.ge("thisDay",localDate);
		}
		if (checkInStatus != null) {
			queryWrapper.eq("checkInStatus", checkInStatus);
		}
		if (checkOutTime != null) {
			queryWrapper.eq("checkOutTime", checkOutTime);
		}
		if (checkOutStatus != null) {
			queryWrapper.eq("checkOutStatus", checkOutStatus);
		}
		Page<Access> accessPage = this.page(new Page<>(current, pageSize), queryWrapper);
		List<AccessVO> accessVOList = accessPage.getRecords().stream().map(
				access -> {
					AccessVO accessVO = new AccessVO();
					BeanUtils.copyProperties(access, accessVO);
					accessVO.setUsername(userService.getUserNameById(access.getUserId()));
					return accessVO;
				}
		).collect(Collectors.toList());
		Page<AccessVO> accessVOPage = new Page<>();
		accessVOPage.setTotal(accessPage.getTotal());
		accessVOPage.setRecords(accessVOList);
		return accessVOPage;

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




