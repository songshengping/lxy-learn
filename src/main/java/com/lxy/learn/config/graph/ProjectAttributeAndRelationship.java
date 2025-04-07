package com.lxy.learn.config.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 14:11
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ClassAutoMapping("project_attributeandrelationship")
public class ProjectAttributeAndRelationship extends Edge implements Serializable {

    @FieldAutoMapping(method = "getStartId",type = "Long")
    private Long startId;

    @FieldAutoMapping(method = "getEndId",type = "Long")
    private Long endId;

}
