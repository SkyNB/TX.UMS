package com.lnet.ums.publisher;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by LH on 2016/12/29.
 */
public class Startup {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:publisher.xml");
        context.start();

        System.out.println("ums services started...");

        System.in.read();//按任意键退出
    }
}
