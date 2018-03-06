package com.lnet.ums.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/2/7.
 */
public class GetPrimaryKey {
    /*生产String类型的时间加随机数的值*/
    public static String getStringPrimaryKey(){
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
        int  random= (int)(Math.random()*900)+100;
        String randomDate = sdf.format(new Date())+random;
        String primary =randomDate.trim().replace(" ","");
        System.out.print("###############生成数为"+primary+"#######################");
        return primary;
    }
}
