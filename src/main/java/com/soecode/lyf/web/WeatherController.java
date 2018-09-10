package com.soecode.lyf.web;

import com.soecode.lyf.common.Constants;
import com.soecode.lyf.common.HttpClientUtil;
import com.soecode.lyf.dto.Result;
import com.soecode.lyf.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rcp on 2018/9/4.
 */
@Controller
@RequestMapping("/weather") // url:/模块/资源/{id}/细分 /seckill/list
public class WeatherController {

    @RequestMapping("/getArea")
    @ResponseBody
    public Result getArea(String id){
        String url = Constants.GAODE_GETAREA;
        Map<String,String> map = new HashMap();
        map.put("key",Constants.GAODE_KEY);
        if("" != id){
            map.put("keywords",id);
        }
        String s = HttpClientUtil.doGet(url,map);
        JSONObject jsonObject = JSONObject.fromObject(s);
        JSONArray districts = JSONArray.fromObject(jsonObject.get("districts"));
        return new Result(true,districts);
    }

    /**
     * 获取天气信息，base返回当前信息，all返回所有信息
     */
    @RequestMapping("/getWeatherNow")
    @ResponseBody
    public Result getWeatherNow(String extensions, HttpSession session){
        User loginUser = (User) session.getAttribute(Constants.SESSION_ID);
        String url = "https://restapi.amap.com/v3/weather/weatherInfo";
        Map map = new HashMap();
        map.put("key",Constants.GAODE_KEY);
        map.put("city",loginUser.getQu());
        map.put("extensions",extensions);
        String s = HttpClientUtil.doGet(url, map);
        JSONObject jsonObject = JSONObject.fromObject(s);
        return new Result(true,jsonObject);
    }
}
