package com.nguyen.master.NguyenMaster.core.util;


import com.nguyen.master.NguyenMaster.core.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static boolean isDateValid(String text, String format) {
        try {
            DateUtils.parseDateStrictly(text, format);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String dateToString(Date date, String format) {
        if (date == null || format == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    public static Date stringToDate(String date, String format) {
        if(StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Integer compareWithCurrentDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.YYYYMMDD);
        Date inputDate = null;
        try {
            inputDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date current = new Date();
        if (inputDate.before(current)) {
            return -1;
        }
        if (inputDate.after(current)) {
            return 1;
        }
        return 0;
    }
}
