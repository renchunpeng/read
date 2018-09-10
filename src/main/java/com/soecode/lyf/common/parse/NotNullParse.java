package com.soecode.lyf.common.parse;



import com.soecode.lyf.common.parse.otherSupport.Validate;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class NotNullParse implements IValidateParse {

	@Override
	public void parse(Validate validate, Field field, Annotation anno) {
		NotNull v = (NotNull) anno;
		validate.add(field.getName(), "required", true, v.message());
	}

}
