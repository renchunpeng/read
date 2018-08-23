package com.soecode.lyf.businessUtils;

import com.soecode.lyf.entity.Book;
import com.soecode.lyf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by rcp on 2018/8/13.
 */
@Component
public class BookUtils {
    @Autowired
    private BookService bookService;

    /**
     * 获取当前用户书籍列表
     * @return
     */
    public List<Book> getBookList(){
        List<Book> bookList = bookService.getBookList();

        return  bookList;
    }

    public String getBookListToString() {
        List<Book> bookList = bookService.getBookList();

        String books = "";
        for (Book book : bookList) {
            //拼接书架url
            books += book.getBookUrl() + "|";
        }

        return  books;
    }

}
