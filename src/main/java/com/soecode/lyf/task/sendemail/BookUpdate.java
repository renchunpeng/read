package com.soecode.lyf.task.sendemail;

import com.github.pagehelper.PageHelper;
import com.soecode.lyf.common.Common;
import com.soecode.lyf.entity.MyBook;
import com.soecode.lyf.entity.User;
import com.soecode.lyf.service.LoginService;
import com.soecode.lyf.service.MobileService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Component
public class BookUpdate {
    private final static Logger log = LoggerFactory.getLogger(BookUpdate.class);

    @Autowired
    private CreateEmailBody createEmailBody;

    @Autowired
    private MobileService mobileService;

    @Autowired
    private LoginService loginService;

    @Value("${settings.emailStatus}")
    public boolean emailStatus;

    @Scheduled(cron="${settings.checkTime}")
    public void task() throws GeneralSecurityException{
        log.info("邮件定时器开启！");
        //验证是否可以发送邮件
        if(!checkSendEmailOk()){
            return;
        }

        try {
            List<User> allUser = loginService.getAllUser();
            for(User item : allUser){
                //更新提醒开关打开并且有邮箱账号才能发送
                if(!((null != item.getRemindupdate()) && item.getRemindupdate().equals("1") && (null != item.getEmail())) ){
                    log.info(item.getName()+"--未打开更新提醒开关或没有邮箱地址");
                    continue;
                }

                //判断是否有更新
                Map updateBook = getUpdateBookInfo(item.getId());
                if (null == updateBook){
                    log.info(item.getName()+"书籍列表无书籍更新！");
                    continue;
                }
                String html = "<html><head></head><body><table>";
                html += "<tr><td>书名</td><td>最新章节</td></tr>";

                for(Object key:updateBook.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
                    String value = updateBook.get(key).toString();
                    html += "<tr><td>"+ key +"</td><td>"+ value +"</td></tr>";
                }

                html += "</table></body></html>";
                log.info(html);
                createEmailBody.getContent(html,item.getEmail());
            }

        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 验证是否可以发送邮件
     * @return
     */
    public boolean checkSendEmailOk(){
        boolean flag = false;
        if(!emailStatus){
            log.info("邮件提示功能已关闭！");
        }else {
            //这里是定期器开启状态,主要判断时间是否合适,早上7点之前，晚上十点之后不发送邮件
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);

            if (hour>=22||hour<=7){
                log.info("免打扰时间,禁止发送邮件！");
            }else {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判斷该用户的书籍列表是否有书籍更新，如果有返回一个map
     * @return
     */
    public Map getUpdateBookInfo(String user_id){
        List<MyBook> lists = mobileService.getList(user_id);
        int noUpdateBookNum = 0;
        Map updateBook = new HashMap();

        //获取最后更新时间和最新章节
        for (MyBook myBook : lists) {
            Document doc = null;
            try {
                doc = Jsoup.connect(myBook.getBookUrl()).get();
                Elements lastUpdatePage = doc.select(".small").select("span:eq(5)");

                //假如获取到的最后章节名和数据库存储的最后章节名不一致，认为网站已经更新
                if (lastUpdatePage.select("a").text().equals(myBook.getLastPageName())) {
                    noUpdateBookNum++;
                    log.info(myBook.getName()+"沒有更新");
                } else {
                    updateBook.put(myBook.getName(),lastUpdatePage.select("a").text());

                    Map<String, String> map = new HashMap<>();
                    map.put("id", myBook.getId());
                    map.put("value", lastUpdatePage.select("a").text());
                    mobileService.updatePageName(map);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (noUpdateBookNum == lists.size()){
            return  null;
        }else {
            return updateBook;
        }
    }
}