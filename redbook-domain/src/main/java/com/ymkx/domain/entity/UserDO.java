package com.ymkx.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user")
public class UserDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * redbook(唯一凭证)
     */
    private String userId;

    private String password;

    private String nickname;

    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    private String backgroundImg;

    private String phone;

    /**
     * 性别(0：女 1：男)
     */
    private Integer sex;

    /**
     * 状态(0：启用 1：禁用)
     */
    private Integer status;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除(0：未删除 1：已删除)
     */
    private boolean isDeleted;
}

