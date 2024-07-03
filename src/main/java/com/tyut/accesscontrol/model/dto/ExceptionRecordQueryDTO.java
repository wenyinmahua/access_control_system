package com.tyut.accesscontrol.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyut.accesscontrol.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class ExceptionRecordQueryDTO extends PageRequest implements Serializable {
    /**
     * 异常记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 识别时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
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