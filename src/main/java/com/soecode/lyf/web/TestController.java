package com.soecode.lyf.web;

import com.soecode.lyf.common.DesUtil;
import com.soecode.lyf.entity.SearchBook;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
		MobileController bean = context.getBean("mobileController",MobileController.class);
		List<SearchBook> lists = bean.search("欲望");
		String s = JSONArray.fromObject(lists).toString();
		System.out.println(s);
	}

	/**
	 * 加减密测试
	 */
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
			e.printStackTrace();
		}
	}

}
