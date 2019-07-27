package com.soecode.lyf.businessUtils;

import com.soecode.lyf.common.Common;
import com.soecode.lyf.common.Constants;
import com.soecode.lyf.entity.MyBook;
import com.soecode.lyf.entity.User;
import com.soecode.lyf.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by rcp on 2018/8/13.
 */
@Component
public class BookListUtils {
    @Autowired
    private MobileService mobileService;

    /**
     * 获取书籍列表url拼接字符串
     * @return
     */
    public String getBookListToString(){
        User user = (User) Common.getSession().getAttribute(Constants.SESSION_ID);
        List<MyBook> lists = mobileService.getList(user.getId());
        String bookList = "";
        for (MyBook myBook : lists) {
            //拼接书架url
            bookList += myBook.getBookUrl() + "|";
        }

        return  bookList;
    }
}
