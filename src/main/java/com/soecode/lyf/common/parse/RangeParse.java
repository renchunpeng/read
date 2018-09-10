package com.soecode.lyf.common.parse;


import com.soecode.lyf.common.parse.otherSupport.Validate;
import org.hibernate.validator.constraints.Range;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by ZXL on 2015/11/9.
 */
public class RangeParse implements IValidateParse {
	@Override
	public void parse(Validate validate, Field field, Annotation anno) {
		Range r = (Range) anno;
		validate.add(field.getName(), "range", new Long[] { r.min(), r.max() }, r.message());
		validate.add(field.getName(), "digits", true, r.message());
	}
}
