package com.soecode.lyf.dao;

import com.soecode.lyf.entity.Book;
import com.soecode.lyf.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author rcp
 */
@Repository
public interface BookDao {

	/**
	 * 获取book列表
	 * @return
	 */
	List<Book> getBookList();

	/**
	 * 保存书籍
	 * @param book
	 * @return
	 */
	int saveBook(Book book);
}
