package com.soecode.lyf.common.parse.otherSupport;



import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by dde on 2015/11/9.
 */
public interface IValidateParse {
    void parse(Validate validate, Field field, Annotation anno);
}
