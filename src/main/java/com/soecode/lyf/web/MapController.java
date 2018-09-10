package com.soecode.lyf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rcp on 2018/9/4.
 */
@Controller
@RequestMapping("/map") // url:/模块/资源/{id}/细分 /seckill/list
public class MapController {

    @RequestMapping("/goMap")
    public String goMap(){

        return "/map/myLocation";
    }

}
