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
     * 日志id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 
     */
    private Date timestamp;

    /**
     * 0-签到失败 | 1-签到成功
     */
    private Integer checkInStatus;

    /**
     * 0-签退失败 | 1-签退成功
     */
    private Integer checkOutStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}