package com.tyut.accesscontrol.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出入表
 * @TableName access
 */
@TableName(value ="access")
@Data
public class AccessVO implements Serializable {
	/**
	 * 出入表ID
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 用户姓名
	 */
	private String username;

	/**
	 * 签到时间
	 */
	private Date checkInTime;

	/**
	 * 0-未签到 | 1-已签到
	 */
	private Integer checkInStatus;

	/**
	 * 签到状态图片
	 */
	private String checkInImage;

	/**
	 * 签退时间
	 */
	private Date checkOutTime;

	/**
	 * 0-未签退 | 1-已签退
	 */
	private Integer checkOutStatus;

	/**
	 * 签退状态图片
	 */
	private String checkOutImage;

	/**
	 * 0-签退 | 1-签到
	 */
	private Integer flag;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}