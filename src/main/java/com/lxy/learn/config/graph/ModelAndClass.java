package com.lxy.learn.config.graph;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 14:10
 * @Version 1.0
 */
@Data
@ClassAutoMapping("modelandclass")
public class ModelAndClass implements Serializable {

    /**
     * id vid
     */
    @FieldAutoMapping(method = "getId", type = "Long")
    private Long id;

    /**
     * 父级ID
     */
    @FieldAutoMapping(method = "getPid", type = "Long")
    private Long pid;/**
     * 名称
     */
    @FieldAutoMapping(method = "getName", type = "String")
    private String name;

    /**
     * 描述
     */
    @FieldAutoMapping(method = "getDescription", type = "String")
    private String description;

}
