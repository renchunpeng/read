package com.soecode.system.aop;

import com.soecode.system.annotation.TestLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author rcp
 */
@Component
@Aspect
public class TestAspect {
    /**
     * 切入点表达式按需配置
     */
    @Pointcut("execution(* com.soecode.lyf.web.TestController.*(..))")
    private void myPointcut() {
    }

    @Before("myPointcut()")
    public void before(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    private void handleLog(JoinPoint joinPoint,Exception e) {
        try {
            //获得注解
            TestLog logger = giveController(joinPoint);
            if(logger == null){
                return;
            }

            // 获取目标方法签名
            String signature = joinPoint.getSignature().toString();
            //请求的方法
            String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
//请求的类
            String classType = joinPoint.getTarget().getClass().getName();
//利用反射机制加载这个类
            Class<?> clazz = Class.forName(classType);
//获取这个类所有的方法
            Method[] methods = clazz.getDeclaredMethods();
//请求的方法名被打印出来
            System.out.println("methodName: " + methodName);

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                System.out.println(paraName+": "+request.getParameter(paraName));
            }

            for (Method method : methods) {
                if (method.isAnnotationPresent(TestLog.class) && method.getName().equals(methodName)) {
                    String annId = logger.description();
                    System.out.println(annId);
/*                    OpTypeEnum type = logger.type();
                    String operation = type.getIndex();
                    saveLog(annId, operation);*/
                }
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private static TestLog giveController(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(TestLog.class);
        }
        return null;
    }

}