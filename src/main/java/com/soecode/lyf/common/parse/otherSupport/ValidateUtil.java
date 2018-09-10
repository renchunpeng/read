package com.soecode.lyf.common.parse.otherSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.soecode.lyf.common.parse.*;
import com.soecode.lyf.common.parse.IValidateParse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


/**
 * Created by ZXL on 2015/11/9.
 */
public class ValidateUtil {
    private static Map<String, IValidateParse> parse = null;
    static {
        initParse();
    }
    private static void initParse() {
        if (parse == null) {
            parse = new HashMap<String, IValidateParse>();

            parse.put(Email.class.getName(), new EmailParse());
            parse.put(Length.class.getName(), new LengthParse());
            parse.put(Max.class.getName(), new MaxParse());
            parse.put(Min.class.getName(), new MinParse());
            parse.put(NotEmpty.class.getName(), new NotEmptyParse());
            parse.put(Pattern.class.getName(), new PatternParse());
            parse.put(Range.class.getName(), new RangeParse());
            parse.put(Size.class.getName(), new SizeParse());
            parse.put(NotNull.class.getName(), new NotNullParse());
            parse.put(Digits.class.getName(), new DigitsParse());
            parse.put(AnnExtist.class.getName(), new ExtistParse());
        }
    }

    public static Validate getValidate(Class formClazz) {
        Validate validate = new Validate();
        for (Field field : formClazz.getDeclaredFields()) {
            Annotation[] fAnn = field.getAnnotations();
            if (fAnn.length > 0) {
                for (Annotation ann : fAnn) {
                    IValidateParse parseObj = parse.get(ann.annotationType().getName());
                    if (parseObj != null) {
                        parseObj.parse(validate, field, ann);
                    }
                }
            }
        }

        return validate;
    }

    public static void main(String[] args) {
//        System.out.println(ValidateUtil.getValidate(BbsEntryForm.class));
        String a = "0";
        boolean b = checkStatus(a);
        System.out.println(b);
    }

    /**
     * 验证发票号
     *
     * @Title: checkInvoiceId
     * @param invoiceId
     * @return boolean
     */
    public static boolean checkInvoiceId(String invoiceId) {
        String regex = "^[A-Za-z0-9]+$";
        boolean isRight = match(regex, invoiceId);
        return isRight;
    }
    /**
     * 验证配送数量
     *
     * @Title: checkDistributeCount
     * @param distributeCount
     * @return boolean
     */
    public static boolean checkDistributeCount(String distributeCount) {
        String regex = "^[1-9][0-9]*$";
        return match(regex, distributeCount);
    }

    /**
     * 判断数字
     *
     * @Title: checkStatus
     * @param state
     * @return boolean
     */
    public static boolean checkStatus(String state) {
        String regex = "^\\d+$";
        return match(regex, state);
    }

    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}