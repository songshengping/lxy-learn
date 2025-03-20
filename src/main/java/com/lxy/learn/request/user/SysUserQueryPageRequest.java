package com.lxy.learn.request.user;

import lombok.Data;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/20 19:27
 * @Version 1.0
 */
@Data
public class SysUserQueryPageRequest {
    // 用户名
    private String username;

    // 真实姓名
    private String realname;

    // 密码（实际使用时通常不会通过API暴露）
    private String password;

    // 密码盐值
    private String salt;

    // 头像URL
    private String avatar;

    // 生日，前端传输时使用字符串格式（例如：yyyy-MM-dd）
    private String birthday;

    // 性别（0: 男，1: 女）
    private Integer sex;

    // 邮箱
    private String email;

    // 手机号
    private String phone;

    // 组织代码
    private String orgCode;
    // 用户状态（0: 禁用，1: 启用）
    private Integer status;

    // 删除标志（0: 未删除，1: 已删除）
    private Integer delFlag;

    // 工号
    private String workNo;

    // 职位
    private String post;

    // 固定电话
    private String telephone;

    // 创建人
    private String createBy;
    // 创建时间，前端传输时使用字符串格式（例如：yyyy-MM-dd HH:mm:ss）
    private String createTime;
    // 更新人
    private String updateBy;
    // 更新时间，前端传输时使用字符串格式（例如：yyyy-MM-dd HH:mm:ss）
    private String updateTime;
}
