package com.zing.ad.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Zing
 * @date 2019-11-25
 */
public class CommonUtils {

    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static Date parseDate(String date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            return DateUtils.addHours(dateFormat.parse(date), -8);
        } catch (ParseException e) {
            return null;
        }
    }
}
