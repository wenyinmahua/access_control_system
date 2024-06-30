package com.tyut.accesscontrol.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 出入表
 * @TableName access
 */
@TableName(value ="access")
@Data
public class Access implements Serializable {
    /**
     * 出入表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 
     */
    private Date checkInTime;

    /**
     * 0-签到失败 | 1-签到成功
     */
    private Integer checkInStatus;

    /**
     * 签到状态图片
     */
    private String checkInImage;

    /**
     * 
     */
    private Date checkIutTime;

    /**
     * 0-签退失败 | 1-签退成功
     */
    private Integer checkOutStatus;

    /**
     * 签退状态图片
     */
    private String checkOutImage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}