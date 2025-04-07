package com.lxy.learn.config.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 14:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlBuild {

    private Long id;

    private String name;

    private String field;

    private String values;

}
