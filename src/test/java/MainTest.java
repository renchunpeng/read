import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.soecode.lyf.common.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) throws Exception{
        String url = getText("c:/log/read.txt").split(",")[0];
        while (true){
            Map content = getContent(url);
            if(null != content.get("content") && "" != content.get("content")){
                read(content.get("content").toString());
            }

            if(null != content.get("next") && "" != content.get("next")){
                url = content.get("next").toString();
            }else{
                break;
            }
        }
        read("没有下一章了");
    }

    /**
     * 读取文档的方法
     * @param value
     */
    public static void read(String value){
        // TODO Auto-generated method stub
        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");

        Dispatch sapo = sap.getObject();
        try {
            // 音量 0-100
            sap.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            sap.setProperty("Rate", new Variant(2));

            // 执行朗读
            Dispatch.call(sapo, "Speak", new Variant(value));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sapo.safeRelease();
            sap.safeRelease();
        }
    }

    /**
     * 读取网页小说
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map getContent(String url) throws Exception {
        Map model = new HashMap();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("#content");//文章主体
        System.out.println(links.text());
        String pageName = doc.select(".content").select("h1").text();//章节名
        Elements chapters = doc.select(".page_chapter").select("a");

        model.put("content", links.text());
        model.put("title", pageName);
        model.put("pre", Constants.BASEURL + chapters.get(0).attr("href"));
        model.put("next", Constants.BASEURL + chapters.get(2).attr("href"));
        model.put("thisUrl", url);

        AddBookMark(model.get("next").toString() + "," + "上一章节:" + pageName);
        return model;
    }

    /**
     * 读取本地TXT文档
     * @throws Exception
     */
    public static String getText(String filePath) throws Exception{
        BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"));//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }

    /**
     * 保存听书开始章节
     * @param word
     * @throws IOException
     */
    public static void AddBookMark(String word) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File("c:/log/read.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(word.getBytes("gbk"));
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}