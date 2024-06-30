package com.tyut.accesscontrol.aop;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.tyut.accesscontrol.annotation.AuthCheck;
import com.tyut.accesscontrol.common.ErrorCode;
import com.tyut.accesscontrol.constant.UserConstant;
import com.tyut.accesscontrol.exception.BusinessException;
import com.tyut.accesscontrol.model.entity.UserDemo;
import com.tyut.accesscontrol.service.UserDemoService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 检测用户身份的切面类
 * @author mahua
 */
@Component
@Aspect
public class AuthInterceptor {

	@Resource
	private UserDemoService userDemoService;

	@Around("@annotation(authCheck)")
	public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
		List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
		String mustRole = authCheck.mustRole();
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

		//当前登录的用户
		UserDemo userDemo = userDemoService.getLoginUser(request);
		// 如果 userRole 是 String 类型的，需要修改第 50 行和第 55 行的 userRoleString 为 userRole；同时删除第 47 行代码 ，取消第 45 行代码的注释，请按步执行；
//		String userRole = user.getUserRole();
		// 如果用户的 userRole 是 Integer 类型的
		Integer userRole = userDemo.getUserRole();
		String userRoleString = userRole.equals(UserConstant.DEFAULT_ROLE_CODE) ? UserConstant.DEFAULT_ROLE : UserConstant.ADMIN_ROLE;
		if(CollectionUtils.isNotEmpty(anyRole)){
			if (!anyRole.contains(userRoleString)){
				throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
			}
		}
		if (StringUtils.isNotBlank(mustRole)){
			if (!mustRole.equals(userRoleString)){
				throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
			}
		}
		//通过权限校验，放行
		return joinPoint.proceed();
	}
}