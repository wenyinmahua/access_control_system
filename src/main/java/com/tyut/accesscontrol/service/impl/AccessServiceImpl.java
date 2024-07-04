package com.tyut.accesscontrol.service.impl;
import com.tyut.accesscontrol.common.BaseResponse;
import com.tyut.accesscontrol.common.ResultUtils;
import com.tyut.accesscontrol.constant.AlarmConstant;
import com.tyut.accesscontrol.constant.SignInOrOutConstant;
import com.tyut.accesscontrol.model.entity.ExceptionRecord;
import com.tyut.accesscontrol.model.entity.Log;
import com.tyut.accesscontrol.service.ExceptionRecordService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import com.tyut.accesscontrol.service.LogService;
import com.tyut.accesscontrol.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

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

	@Resource
	private ExceptionRecordService exceptionRecordService;

	@Resource
	private LogService logService;

	/**
	 * 条件查询
	 * @param accessQueryDTO
	 * @return
	 */
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
		if (!StringUtils.isEmpty(username)) {
			List<Long> userIds = userService.getUserIdsByName(username);  //模糊查询
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
		List<AccessVO> accessVOList = accessPage.getRecords().stream().map(  //Java 8 流式处理
				access -> {
					AccessVO accessVO = new AccessVO();
					BeanUtils.copyProperties(access, accessVO);  //将 将 Access 实体的属性复制到 AccessVO 对象
					accessVO.setUsername(userService.getUserNameById(access.getUserId()));
					return accessVO;
				}
		).collect(Collectors.toList());  //将转换得到的 AccessVO 对象集合存储在 accessVOList 中
		Page<AccessVO> accessVOPage = new Page<>();
		accessVOPage.setTotal(accessPage.getTotal());  //设置总记录数
		accessVOPage.setRecords(accessVOList);   //Records存的是数据库里面查询出来的 实体对象 的集合
		return accessVOPage;
	}

	/**
	 * 更新出入表
	 * @param access
	 * @return
	 */
	@Override
	public Boolean updateAccess(Access access) {
		if (access == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UpdateWrapper<Access> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id",access.getId());
		return this.update(access,updateWrapper);
	}

	/**
	 * 根据id删除记录
	 * @param deleteRequest
	 * @return
	 */
	@Override
	public Boolean deleteAccessById(DeleteRequest deleteRequest) {
		Long id = deleteRequest.getId();
		if (id < 0 ){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return this.removeById(id);
	}

	/**
	 * 签到签退图片传输
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@Override
	public BaseResponse<Boolean> userSignInOrOut(MultipartFile file) throws IOException {
		//获取Base64编码
		String imageBase64 = "data:image/jpg;base64," + Base64Utils.encodeToString(file.getBytes());
		Long signUserId = Long.valueOf(file.getOriginalFilename().matches("-?\\d+") ? Long.valueOf(file.getOriginalFilename()) : -1);
		LocalDate thisDay = LocalDate.now();
		String userNameById = userService.getUserNameById(signUserId);
		if (StringUtils.isEmpty(imageBase64)){
		throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		if (signUserId == null || signUserId < 0){  //对未录入的人脸异常识别进行记录
			ExceptionRecord exceptionRecord = new ExceptionRecord();
			exceptionRecord.setRecognitionTime(new Date());
			exceptionRecord.setRecognitionImage(imageBase64);
			exceptionRecord.setIsAlarm(AlarmConstant.ALARM);
			UpdateWrapper<Log> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("logDate",thisDay).setSql("totalRecognitionFailures = totalRecognitionFailures + 1");
			logService.update(updateWrapper);
			exceptionRecordService.save(exceptionRecord);
			return ResultUtils.error(ErrorCode.FORBIDDEN_ERROR,"签到失败，error person!");
		}
		LocalTime now = LocalTime.now();
		// 判断当前时间是否在0点到6点之间
		boolean isNotSignInTime = now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.of(0, 30));
		if (isNotSignInTime) {
			ExceptionRecord exceptionRecord = new ExceptionRecord();
			exceptionRecord.setRecognitionTime(new Date());
			exceptionRecord.setRecognitionImage(imageBase64);
			exceptionRecord.setIsAlarm(AlarmConstant.RECORD);
			UpdateWrapper<Log> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("logDate",thisDay).setSql("totalRecognitionFailures = totalRecognitionFailures + 1");
			logService.update(updateWrapper);
			exceptionRecordService.save(exceptionRecord);
			return ResultUtils.error(ErrorCode.FORBIDDEN_ERROR,"签到失败，请在指定时间内签到！");
		}

		QueryWrapper<Access> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userId",signUserId);
		queryWrapper.eq("thisDay",thisDay);
		Access access = this.getOne(queryWrapper);
		if (access == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		Access updateAccess = new Access();
		updateAccess.setId(access.getId());
		updateAccess.setUserId(signUserId);
		Integer flag = access.getFlag();  //签到签退标志
		// 之前状态是签到，这次识别为签退
		if (Objects.equals(flag, SignInOrOutConstant.SIGN_IN)){
			if(StringUtils.isNotEmpty(access.getCheckOutImage())){  //如果已有签退记录
				return ResultUtils.error(ErrorCode.OPERATION_ERROR,userNameById + "已签退");
			}
			updateAccess.setCheckOutImage(imageBase64);
			updateAccess.setCheckOutTime(new Date());
			// 这个 SIGN_IN 指的是成功签退
			updateAccess.setCheckOutStatus(SignInOrOutConstant.SIGN_IN);
			UpdateWrapper<Log> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("logDate",thisDay).setSql("totalCheckedOut = totalCheckedOut + 1");
			logService.update(updateWrapper);
			return ResultUtils.success(this.updateById(updateAccess),userNameById+"签退成功！");
		} else {
				// 之前的状态是未签到，这里是签到操作
				updateAccess.setCheckInImage(imageBase64);
				updateAccess.setCheckInTime(new Date());
				updateAccess.setCheckInStatus(SignInOrOutConstant.SIGN_IN);
				updateAccess.setFlag(SignInOrOutConstant.SIGN_IN);  // 将标志设置为签到
				UpdateWrapper<Log> updateWrapper = new UpdateWrapper<>();
				updateWrapper.eq("logDate",thisDay).setSql("totalCheckedIn = totalCheckedIn + 1");
				logService.update(updateWrapper);
				return ResultUtils.success(this.updateById(updateAccess),userNameById+"签到成功！");

		}
	}
}




