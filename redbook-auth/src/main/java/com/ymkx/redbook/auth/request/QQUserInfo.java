package com.ymkx.redbook.auth.request;

import lombok.Data;

/**
 * QQ用户信息实体类
 * @author ymkx
 */
@Data
public class QQUserInfo {

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 个性签名
     */
    private String long_nick;

    /**
     * 头像URL
     */
    private String avatar_url;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * QID
     */
    private String qid;

    /**
     * QQ等级
     */
    private Integer qq_level;

    /**
     * 所在地
     */
    private String location;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否VIP
     */
    private Boolean is_vip;

    /**
     * VIP等级
     */
    private Integer vip_level;

    /**
     * 注册时间
     */
    private String reg_time;

    /**
     * 最后更新时间
     */
    private String last_updated;
}