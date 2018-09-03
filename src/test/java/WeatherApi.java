import com.soecode.lyf.common.HttpClientUtil;
import net.sf.json.JSONObject;

/**
 * Created by rcp on 2018/9/3.
 */
public class WeatherApi {
    public static void main(String[] args) throws Exception{
        //获取省级地区编号
//        String s = HttpClientUtil.doGet("http://www.weather.com.cn/data/city3jdata/china.html");
//        System.out.println(s);

        //获取市级地区编号
//        String s = HttpClientUtil.doGet("http://www.weather.com.cn/data/city3jdata/provshi/10119.html");
//        System.out.println(s);

        //获取区县地区编号
//        String s = HttpClientUtil.doGet("http://www.weather.com.cn/data/city3jdata/station/1011901.html");
//        System.out.println(s);

        //获取区县最近五天的天气
//        String s = HttpClientUtil.doGet("http://m.weather.com.cn/data/101190101.html");
//        System.out.println(s);

        //实时天气预报
        String s1 = HttpClientUtil.doGet("http://www.weather.com.cn/data/sk/101190101.html");
        System.out.println(s1);

        //实时天气预报
        String s2 = HttpClientUtil.doGet("http://www.weather.com.cn/data/cityinfo/101190101.html");
        System.out.println(s2);
    }
}
