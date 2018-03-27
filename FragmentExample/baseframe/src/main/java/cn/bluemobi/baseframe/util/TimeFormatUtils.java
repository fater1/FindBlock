package cn.bluemobi.baseframe.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatUtils {

    public static String dateFormat(Date date, String pattern){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }catch (Exception e){
            return "";
        }
    }

    public static Date getDate(String format, String pattern){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(format);
        }catch (Exception e){
            return new Date(0);
        }
    }
}
