package com.soecode.lyf.common.parse;


import com.soecode.lyf.common.parse.otherSupport.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 电话验证规则 13位
 *
 * @author 郭一行
 * @date 2017/8/31
 */
public class PhoneParse implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone phone) { }

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
        return phoneField == null||phoneField.matches("^0\\d{2,3}-\\d{7,8}$");
    }
}