import com.soecode.lyf.common.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rcp on 2018/9/3.
 */
public class WeatherApi {
    public static void main(String[] args) throws Exception{
        String url = "https://restapi.amap.com/v3/config/district";
        Map<String,String> map = new HashMap();
        map.put("key","737055e086e3381f3f1a5f188f72bcab");
        map.put("keywords","南京");
        String s = HttpClientUtil.doGet(url,map);
    }
}
