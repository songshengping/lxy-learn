package com.lxy.learn.config.graph;

import java.lang.annotation.*;

/**
 * @author 86185
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 14:06
 * @Version 1.0
 */
@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAutoMapping {
    String method() default "";
    String type();
}
