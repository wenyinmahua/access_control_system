package com.tyut.accesscontrol.mapper;

import com.tyut.accesscontrol.model.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
* @author mahua
* @description 针对表【log(日志表)】的数据库操作Mapper
* @createDate 2024-07-03 12:21:12
* @Entity com.tyut.accesscontrol.model.entity.Log
*/
public interface LogMapper extends BaseMapper<Log> {

	@Select("SELECT * FROM access_control.log "
			+ "WHERE logDate BETWEEN DATE_FORMAT(#{localDate}, '%Y-%m-01') AND LAST_DAY(#{localDate})")
	List<Log> getLogByMonth(LocalDate localDate);
}




