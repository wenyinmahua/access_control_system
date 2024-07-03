package com.tyut.accesscontrol.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyut.accesscontrol.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出入表
 * @TableName access
 * DTO是数据传输对象，Service向外传输的对象
 */
@TableName(value ="access")
@Data   // Lombok 的注解,用于自动生成 getter/setter 方法，toString 方法
public class AccessQueryDTO extends PageRequest implements Serializable {
	/**
	 * 出入表ID
	 */
	@TableId(type = IdType.AUTO)  //主键，自增
	private Long id;

	/**
	 * 用户姓名
	 */
	private String username;

	/**
	 * 签到时间
	 *  通过注解指定时间格式和时区，使用 @JsonFormat 注解,可以确保在 JSON 序列化/反序列化时,
	 *  日期属性能够正确地被格式化
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-DD", timezone="GMT+8")
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

	@TableField(exist = false)   //指定 serialVersionUID 字段不需要映射到数据库表中，减小程序开销，提高性能
	private static final long serialVersionUID = 1L;
}