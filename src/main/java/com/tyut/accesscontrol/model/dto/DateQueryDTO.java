package com.tyut.accesscontrol.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期查询
 */

@Data
public class DateQueryDTO implements Serializable {
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	private Date thisDay;
	private static final long serialVersionUID = 1L;
}
