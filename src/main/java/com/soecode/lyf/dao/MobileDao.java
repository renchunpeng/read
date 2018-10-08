package com.soecode.lyf.dao;

import com.soecode.lyf.entity.MyBook;
import com.soecode.lyf.entity.UserAndBook;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author rcp
 */
@Repository
public interface MobileDao {

	/**
	 * 查询所有图书
	 * @param userId
	 * @return
	 */
	List<MyBook> queryAll(String userId);

	/**
	 * @author rcp
	 * 修改用户最后看到的章节名
	 * @param map
	 */
	void updatePageName(Map<String, String> map);

    /**
     * 将书籍添加到书架
     * @param book
     */
	void addBook(UserAndBook book);

	/**
	 * 保存书签
	 * @param params
	 */
	int saveBookMark(Map params);

	/**
	 * 移除书籍列表
	 * @param params
	 * @return
	 */
	int removeBookList(Map params);
}
