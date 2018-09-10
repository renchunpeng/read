package com.soecode.lyf.common.parse;


import com.soecode.lyf.common.parse.otherSupport.Validate;
import org.hibernate.validator.constraints.NotEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by ZXL on 2015/11/9.
 */
public class NotEmptyParse implements IValidateParse{

    @Override
    public void parse(Validate validate, Field field, Annotation anno) {
        NotEmpty v = (NotEmpty) anno;

        validate.add(field.getName(),"required",true,v.message());
    }
}
