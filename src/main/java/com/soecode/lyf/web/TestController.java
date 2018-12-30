package com.soecode.lyf.web;

import com.soecode.lyf.common.Constants;
import com.soecode.lyf.common.DesUtil;
import com.soecode.lyf.dto.Result;
import com.soecode.lyf.entity.User;
import com.soecode.lyf.task.sendemail.BookUpdate;
import com.soecode.system.annotation.TestLog;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/test")
public class TestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @TestLog(description = "登录测试",clazz = User.class)
	@RequestMapping("/index")
	public String index(HttpSession session, Model model) {
        User item = new User();
        item.setName("rcp");
		JSONArray list = JSONArray.fromObject(Constants.TEST_MENU);
		JSONObject user = JSONObject.fromObject(Constants.TEST_USER);
		model.addAttribute("list",list);
		model.addAttribute("user", user);
		model.addAttribute("systemHostUnit", "rcp测试系统");
		model.addAttribute("systemTitle", "rcp测试系统");
		return "/test/sysindex";
	}

	/**
	 * 执行bean文件的测试方法
	 */
	@Test
	public void rcp(){
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		WeatherController bean = context.getBean("weatherController",WeatherController.class);
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

	/**
	 * 定时器测试
	 */
	@Test
	public  void testDSQ(){
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		BookUpdate bean = context.getBean("bookUpdate",BookUpdate.class);
		try {
			bean.task();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
    public void qwe(){
        String doc = null;
        System.out.println(doc.toString());
    }

}
