package com.lxy.learn.enums;

import lombok.Getter;

/**
 * 错误码
 * 错误来源于用户: 参数错误 A0001
 * 错误来源于系统: 业务逻辑出错 B0001
 * 错误来源于第三方服务: 网络错误,或者第三方服务异常 C0001
 *
 * @author 86185
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    /* 通用异常 10000001-10000100 */
    PARAM_ERROR(10000001, "参数错误"),
    ILLEGAL_STATE(10000002, "状态错误"),
    ENCRYPT_ERROR(10000003, "加密失败"),
    DECRYPT_ERROR(10000004, "解密失败"),
    CAPTCHA_ERROR(10000005, "验证码错误"),
    CAPTCHA_INVALID(10000006, "验证码无效"),
    CREATE_CAPTCHA_ERROR(10000007, "验证码创建失败"),
    DICT_NOT_EXIST_ERROR(10000008, "字典数据不存在"),
    LOG_IMPORT_ERROR(10000009, "日志导入失败"),
    FILE_DELETE_ERROR(10000010, "文件删除错误"),
    DATA_CHANGE_ERROR(100000011, "数据发生变化，请重试！"),
    FILE_SIZE_UPLOAD_ERROR(10000012, "文件上传大小错误"),

    UNKNOWN_ERROR(99999999, "未知错误"),
    ;
    private final Integer code;

    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
