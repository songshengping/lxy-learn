package com.lxy.learn.response;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 13:57
 * @Version 1.0
 */
@Data
public class NebulaResult<T> {
    private Integer code;
    private String message;
    private List<T> data;

    public boolean isSuccessed(){
        return code == 0;
    }
}
