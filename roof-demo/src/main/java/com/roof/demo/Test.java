package com.roof.demo;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by zhenglt on 2017/11/19.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        System.out.println("目前时间：" + calendar.getTime());
        System.out.println("Calendar时区：：" + calendar.getTimeZone().getID());
        System.out.println("user.timezone：" + System.getProperty("user.timezone"));
        System.out.println("user.country：" + System.getProperty("user.country"));
        System.out.println("默认时区：" + TimeZone.getDefault().getID());
    }
}
