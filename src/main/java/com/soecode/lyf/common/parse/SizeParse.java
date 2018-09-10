package com.soecode.lyf.common.parse;




import com.soecode.lyf.common.parse.otherSupport.Validate;

import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by ZXL on 2015/11/9.
 */
public class SizeParse implements IValidateParse {

    @Override
    public void parse(Validate validate, Field field, Annotation anno) {
        Size v = (Size) anno;
        validate.add(field.getName(), "rangelength", new int[]{v.min(), v.max()}, v.message());
    }

}
