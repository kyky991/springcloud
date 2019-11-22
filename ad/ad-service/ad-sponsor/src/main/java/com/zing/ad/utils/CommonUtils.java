package com.zing.ad.utils;

import com.zing.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author Zing
 * @date 2019-11-22
 */
public class CommonUtils {

    private static final String[] PATTERNS = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"};

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseDate(String date) throws AdException {
        try {
            return DateUtils.parseDate(date, PATTERNS);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }
}
