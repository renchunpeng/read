package com.soecode.lyf.web;

import com.soecode.lyf.common.DesUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 执行bean文件的测试方法
	 */
	@Test
	public void rcp(){
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		
//		BookServiceImpl bean = context.getBean("bookServiceImpl",BookServiceImpl.class);
//		Book book = new Book();
//		book.setName("jquery");
//		book.setNumber(123);
//		bean.insertBook(book);
	}

	@Test
	public  void testPwd(){
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		DesUtil bean = context.getBean("desUtil",DesUtil.class);
		String data = "rcp";
		System.out.println("加密前===>"+data);
		try {
			System.err.println(bean.encrypt(data));
			System.err.println(bean.decrypt(bean.encrypt(data)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
