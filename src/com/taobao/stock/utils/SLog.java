package com.taobao.stock.utils;


import java.util.logging.Logger;

/**
 * Created by geyubin on 2018/1/15.
 */
public class SLog {

    public static final Logger log =  Logger.getLogger("stock");


    public static void i(Object msg){

        System.out.println(msg);

//        log.info(msg + "");
    }

    public static void w(Object msg){
        System.out.println(msg);

//        log.warning(msg + "");
    }

}
