package com.kevindai.webSpider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Created by daiwenkai on 2017/1/16.
 */
public class Starter {
    public static void main(String[] args) throws Exception{

        String cur_dir = System.getProperty("user.dir");//获取当前目录
        String confPath = cur_dir + File.separator + "src" + File.separator + "main"
                + File.separator + "java" + File.separator + "com" + File.separator
                + "kevindai" + File.separator + "webSpider" + File.separator + "url.conf";
        List<String> list = readFileByLine(confPath);
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        for (int i = 0; i < list.size(); i++) {
            new Thread(new WebSpider(list.get(i),countDownLatch)).start();
        }
        countDownLatch.await();
        System.out.println("-------------------------------");
        System.out.println("获取完毕,just enjoy it!");
        System.out.println("-------------------------------");
    }

    //读取文件,一行行读取
    //这里使用此方式,简单
    public static List<String> readFileByLine(String filePath) throws Exception{
        List<String> urlList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
        String lineStr = null;
        while ((lineStr = reader.readLine()) != null) {
            urlList.add(lineStr);
        }
        return  urlList;
    }

    //读取配置文件prop
    public static List<String> readProp(String filePath) throws Exception{
        List<String> urlList = new ArrayList<String>();
        Properties properties = new Properties();
        properties.load(new FileInputStream(filePath));
        Enumeration enum1 = properties.propertyNames();
        while (enum1.hasMoreElements()){
            String key = (String) enum1.nextElement();
            String strValue = properties.getProperty(key);
            urlList.add(strValue);
        }
        return urlList;
    }
}
