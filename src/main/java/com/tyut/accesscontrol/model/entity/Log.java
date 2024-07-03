package com.tyut.accesscontrol.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 日志表
 * @TableName log
 */
@TableName(value ="log")
@Data
public class Log implements Serializable {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日志日期
     */
    private Date logDate;

    /**
     * 签到总次数
     */
    private Integer totalCheckedIn;

    /**
     * 签退总次数
     */
    private Integer totalCheckedOut;

    /**
     * 识别失败总次数
     */
    private Integer totalRecognitionFailures;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}