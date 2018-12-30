package com.soecode.system;

import com.soecode.lyf.common.Constants;
import com.soecode.lyf.common.DesUtil;
import com.soecode.lyf.entity.User;
import com.soecode.lyf.service.LoginService;
import com.soecode.lyf.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对未登录用户进行拦截
 *
 * @author rcp
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoginService loginService;

    private String redirectUrl;

    @Autowired
    private DesUtil desUtil;

    /**
     * 不拦截的url
     */
    private List<String> excludes;

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * 可做权限校验
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String urlPath = request.getRequestURI();

        urlPath =  urlPath.replace("//","/").replace(request.getContextPath(), "");

        if (InterceptorUtil.lookupUrl(urlPath, excludes)) {
            return true;
        }

        User loginUser = (User) WebUtils.getSessionAttribute(request,Constants.SESSION_ID);
        //从cookie恢复
        if(loginUser == null){
            autoLogin(request,response);
            loginUser = (User) WebUtils.getSessionAttribute(request,Constants.SESSION_ID);
        }

        if(loginUser == null){
            response.sendRedirect(request.getContextPath() + redirectUrl );
            return false;
        }

        return true;
    }

    /**
     * 根据cookie自动登录
     * @param request
     * @param response
     * @return
     */
    public User autoLogin(HttpServletRequest request,HttpServletResponse response)  {
        try {
            Cookie[] cookie = request.getCookies();
            if(null == cookie){
                return null;
            }
            String nameValue = null;
            String pwdValue = null;
            for (int i = 0; i < cookie.length; i++) {
                Cookie cook = cookie[i];
                //获取用戶名鍵
                if(cook.getName().equalsIgnoreCase(Constants.COOKIE_NAME)){
                    nameValue = cook.getValue().toString();
                    //获取值
                    System.out.println("解密前name:"+nameValue);
                    nameValue = desUtil.decrypt(nameValue);
                    System.out.println("解密后name:"+nameValue);
                    //获取密码键
                }else if(cook.getName().equalsIgnoreCase(Constants.COOKIE_PWD)){
                    pwdValue = cook.getValue().toString();
                    //获取值
                    System.out.println("解密前pwd:"+pwdValue);
                    pwdValue = desUtil.decrypt(pwdValue);
                    System.out.println("解密后pwd:"+pwdValue);
                }
            }

            Map<String, String> map = new HashMap<>();
            map.put("name", nameValue);
            map.put("pwd", pwdValue);

            User user = null;
            if (nameValue != null && pwdValue != null) {
                user = loginService.doLogin(map);
            }

            if (null != user) {
                request.getSession().setAttribute(Constants.SESSION_ID,user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
