package com.soecode.lyf.common;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 任春鹏 on 2019-07-27.
 */
public class EncodeUtils {

    @Test
    public void rcp() throws UnsupportedEncodingException {
        String str = "美女";
        System.out.println(URLEncoder.encode(str, "utf-8"));
        System.out.println(URLEncoder.encode(str, "GBK"));
        System.out.println(URLEncoder.encode(str, "gb2312"));
        System.out.println(URLEncoder.encode(str, "utf-8"));

    }
}
