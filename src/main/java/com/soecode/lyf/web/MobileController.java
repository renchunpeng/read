package com.soecode.lyf.web;

import com.github.pagehelper.PageHelper;
import com.soecode.lyf.businessUtils.BookListUtils;
import com.soecode.lyf.common.Common;
import com.soecode.lyf.common.Constants;
import com.soecode.lyf.dto.Result;
import com.soecode.lyf.entity.*;
import com.soecode.lyf.service.MobileService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author rcp
 */
@Controller
@RequestMapping(value = "/mobile")
public class MobileController {
    @Autowired
    private MobileService mobileService;

    @Autowired
    private BookListUtils bookListUtils;

    /**
     * 用户书籍列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "bookList")
    public String bookList(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute(Constants.SESSION_ID);

        List<MyBook> lists = new ArrayList<>();
        PageHelper.startPage(1, 20);
        lists = mobileService.getList(loginUser.getId());

//		用PageInfo对结果进行包装 page对象里可以获得很多关于分页的信息
//		PageInfo page = new PageInfo(lists);
        model.addAttribute("lists", lists);

        //获取最后更新时间和最新章节
        for (MyBook myBook : lists) {
            Document doc = null;
            try {
                doc = Jsoup.connect(myBook.getBookUrl()).get();
                Elements lastUpdateDate = doc.select(".small").select("span:eq(4)");
                Elements lastUpdatePage = doc.select(".small").select("span:eq(5)");
                myBook.setLastUpdateDate(Common.getLastDate(lastUpdateDate.text()));

                //假如获取到的最后章节名和数据库存储的最后章节名不一致，认为网站已经更新
                if (lastUpdatePage.select("a").text().equals(myBook.getLastPageName())) {
                    myBook.setLastPageName(lastUpdatePage.select("a").text());
                    myBook.setUpdate(false);
                } else {
                    myBook.setLastPageName(lastUpdatePage.select("a").text());
                    myBook.setUpdate(true);

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", myBook.getId());
                    map.put("value", lastUpdatePage.select("a").text());
                    mobileService.updatePageName(map);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "/mobile/bookList";
    }

    /**
     * 最新章节列表&所有章节列表
     *
     * @param model
     * @param url
     * @return
     */
    @RequestMapping(value = "/test")
    public String test(Model model, String url, String isNewList) {
        List<Page> lists = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links;
        if (null != isNewList && !Objects.equals("", isNewList)) {
            links = doc.select(".listmain").select("dd:lt(13)");
            model.addAttribute("isNewList", true);
        } else {
            links = doc.select(".listmain").select("dd:gt(13)");
            model.addAttribute("isAll", true);
        }

        String bookName = doc.select(".info h2").text();
        String picUrl = doc.select(".cover").select("img").attr("src");

        for (Element element : links) {
            Page page = new Page();
            String pageUrl = url.substring(0, 22)
                    + String.valueOf(element.select("a").attr("href"));
            String pageTitle = element.select("a").text();
            page.setUrl(pageUrl);
            page.setTitle(pageTitle);
            lists.add(page);
        }
        //判断是否需要显示添加到书架按钮
        String bookList = bookListUtils.getBookListToString();
        if (bookList.contains(url)){
            model.addAttribute("display", false);
        }else {
            model.addAttribute("display", true);
        }

        model.addAttribute("lists", lists);
        model.addAttribute("bookUrl", url);
        model.addAttribute("bookName", bookName);
        model.addAttribute("picUrl", Constants.BASEURL + picUrl);
        return "/mobile/pageList";
    }


    /**
     * 内容主体
     *
     * @param url
     * @param session
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "getContent", produces = "text/html; charset=utf-8")
    public String getContent(String url, String isNewList, HttpSession session, Model model) throws UnsupportedEncodingException {
        //TODO 获取章节信息的时候更新用户该书籍最后浏览章节
        User loginUser = (User) session.getAttribute(Constants.SESSION_ID);
        Map<Object, Object> params = new HashMap<>(16);
        params.put("user_id",loginUser.getId());
        params.put("book_mark",url);
        int end = url.lastIndexOf("/");
        params.put("book_url",url.substring(0,end + 1));
        mobileService.saveBookMark(params);

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文章主体
        assert doc != null;
        Elements links = doc.select("#content");
        //章节名
        String pageName = doc.select(".content").select("h1").text();
        Elements chapters = doc.select(".page_chapter").select("a");

        if (null != isNewList && !Objects.equals("", isNewList)) {
            model.addAttribute("isNewList", true);
        }
        model.addAttribute("content", links.toString());
        model.addAttribute("title", pageName);
        model.addAttribute("pre", url.substring(0, 22) + chapters.get(0).attr("href"));
        model.addAttribute("next", url.substring(0, 22) + chapters.get(2).attr("href"));
        model.addAttribute("thisUrl", url);
        return "/mobile/pageDetail";
    }

    @RequestMapping(value = "/tosearch")
    public String tosearch() {

        return "/mobile/search";
    }

    /**
     * 书籍搜索
     * @param bookname
     * @return
     */
    @RequestMapping(value = "search")
    @ResponseBody
    public List<SearchBook> search(String bookname) {
        List<SearchBook> lists = new ArrayList<>();
        Document doc = null;
        try {
            String url = Constants.SEARCH_BASEURL  + URLEncoder.encode(bookname,"GBK");
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //查询到的书籍
        Elements links = doc.select("li");

        if (links.size() == 1) {
            // 说明只有title标题,没有查询结果
            return lists;
        }

        // 去掉title
        links.remove(0);
        for (Element element : links) {
            String bookName = element.select(".s2").select("a").text();
            String bookUrl = element.select(".s2").select("a").attr("href");
            String auth = element.select(".s4").text();

            SearchBook sBook = new SearchBook();
            sBook.setBookName(bookName);
            sBook.setBookUrl(bookUrl);
            // 新版搜索结果页面没有图片地址了,先默认,后面添加到书架的时候会更新
            sBook.setBookPicUrl(Constants.BASE_PIC_URL);
            sBook.setAuth(auth);

            lists.add(sBook);
        }
        return lists;
    }

    /**
     * 添加书籍到我的书架
     *
     * @param bookName
     * @param bookUrl
     * @param picUrl
     * @param session
     */
    @RequestMapping(value = "/addBook")
    @ResponseBody
    public void addBook(String bookName, String bookUrl, String picUrl, HttpSession session) throws UnsupportedEncodingException {
        UserAndBook userAndBook = new UserAndBook();
        String replace = bookUrl.replace("book/goto/id/", "");
        String temp = replace.substring(replace.lastIndexOf("/")+1,replace.length());
        String temp2 = temp.substring(0,2);
        userAndBook.setBookUrl(Constants.BASEURL + "/" + temp2 + "_" + temp + "/");
        userAndBook.setName(bookName);
        userAndBook.setImgUrl(picUrl);
        userAndBook.setUserId(Common.getUser().getId());
        userAndBook.setId(UUID.randomUUID().toString().replace("-", ""));
        mobileService.addBook(userAndBook);
    }

    /**
     * 保存书签
     * @param bookMark
     * @param session
     */
    @RequestMapping(value = "/saveBookMarkList")
    @ResponseBody
    public Result saveBookMarkList(String bookMark, HttpSession session){
        try {
            int i = 0;
            String Msg = "功能正在开发中";
            if (i > 0 ){
                return new Result(true,null);
            }else {
                return new Result(false,Msg);
            }
        }catch (Exception e){
            return new Result(false,null);
        }
    }

    /**
     * 从书籍列表移除书籍
     * @param bookUrl
     * @return
     */
    @RequestMapping(value = "/removeBookList")
    @ResponseBody
    public Result removeBookList(String bookUrl, HttpSession session){
        try {
            User loginUser = (User) session.getAttribute(Constants.SESSION_ID);
            Map map = new HashMap(16);
            map.put("bookUrl",bookUrl);
            map.put("user",loginUser.getId());
            int i = mobileService.removeBookList(map);
            if (i > 0 ){
                return new Result(true,null);
            }else {
                return new Result(false,null);
            }
        }catch (Exception e){
            return new Result(false,null);
        }
    }
}