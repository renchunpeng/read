package com.soecode.lyf.common.parse.otherSupport;



import com.soecode.lyf.common.parse.PhoneParse;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 电话验证规则 13位
 *
 * @author 郭一行
 * @date 2017/8/31
 */
@Documented
@Constraint(validatedBy = PhoneParse.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "{电话格式如：0371-68787027}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
