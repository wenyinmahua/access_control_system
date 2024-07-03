package com.tyut.accesscontrol.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 异常记录表
 * @TableName exception_record
 */
@TableName(value ="exception_record")
@Data
public class ExceptionRecord implements Serializable {
    /**
     * 异常记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 识别时间
     */
    private Date recognitionTime;

    /**
     * 识别图片
     */
    private String recognitionImage;

    /**
     * 1-报警 | 2-记录
     */
    private Integer isAlarm;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}