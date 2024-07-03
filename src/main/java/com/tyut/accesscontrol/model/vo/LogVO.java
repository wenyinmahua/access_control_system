package com.tyut.accesscontrol.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

@Data
public class LogVO implements Serializable {
	private Long normalAccessNum;
	private Long abnormalNum;
	private Map<LocalDate,Long> CheckInNum;
	private Map<LocalDate,Long> CheckOutNum;
	private static final long serialVersionUID = 1L;
}
