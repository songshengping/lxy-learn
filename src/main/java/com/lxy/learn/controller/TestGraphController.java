package com.lxy.learn.controller;

import com.lxy.learn.config.graph.NebulaTemplate;
import com.lxy.learn.response.NebulaResult;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sound.midi.MidiDevice;

/**
 * @Description TODO
 * @User JeremySong
 * @Date 2025/3/21 14:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/test/graph")
public class TestGraphController {

    @Resource
    NebulaTemplate nebulaTemplate;

    @GetMapping("/addVertex")
    public Object addJSON() throws IOErrorException {
        String sql = "insert vertex team(team_name, persion_num) values \"team_2\":(\"team_2\", 43);";
        NebulaResult nebulaResult = nebulaTemplate.executeObject(sql);
        return nebulaResult;
    }

    @GetMapping("/findVertex")
    public Object findJson2() throws IOErrorException {
        String sql = "lookup on team  yield id(vertex) AS id,properties(vertex).persion_num AS persion_num,properties(vertex).team_name AS team_name;";
        NebulaResult<MidiDevice.Info> infoNebulaResult = nebulaTemplate.queryObject(sql, MidiDevice.Info.class);
        return infoNebulaResult;
    }
}
