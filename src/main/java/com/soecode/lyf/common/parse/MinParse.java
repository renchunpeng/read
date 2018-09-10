package com.soecode.lyf.common.parse;




import com.soecode.lyf.common.parse.otherSupport.Validate;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by ZXL on 2015/11/9.
 */
public class MinParse implements IValidateParse {

    @Override
    public void parse(Validate validate, Field field, Annotation anno) {
        Min v = (Min) anno;
        validate.add(field.getName(), "min", v.value(), v.message());
    }

}
