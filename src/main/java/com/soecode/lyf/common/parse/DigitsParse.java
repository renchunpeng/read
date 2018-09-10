package com.soecode.lyf.common.parse;




import com.soecode.lyf.common.parse.otherSupport.Validate;

import javax.validation.constraints.Digits;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by ZXL on 2015/11/9.
 */
public class DigitsParse implements IValidateParse {

    @Override
    public void parse(Validate validate, Field field, Annotation anno) {
    	Digits v = (Digits) anno;
        validate.add(field.getName(), "decimal", new int[]{v.integer(), v.fraction()}, v.message());
    }

}
