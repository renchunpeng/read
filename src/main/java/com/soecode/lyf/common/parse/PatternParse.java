package com.soecode.lyf.common.parse;




import com.soecode.lyf.common.parse.otherSupport.Validate;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by ZXL on 2015/11/9.
 */
public class PatternParse implements IValidateParse {

    @Override
    public void parse(Validate validate, Field field, Annotation anno) {
        Pattern v = (Pattern) anno;
        validate.add(field.getName(), "regexp", v.regexp(), v.message());
    }

}
