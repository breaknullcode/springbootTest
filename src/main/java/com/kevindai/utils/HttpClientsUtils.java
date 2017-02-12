package com.kevindai.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevindai on 2017/2/10.
 */
public class HttpClientsUtils {

    /**
     * 发送http请求并获取响应
     * @param url
     * @return
     */
    public static String httpClientSendUrl(String url){
        String result = null;
        try{
            HttpClient httpClient = HttpClients.createDefault();//初始化一个httpClient链接
            HttpGet httpGet = new HttpGet(url);//创建一个get请求
            HttpResponse response = httpClient.execute(httpGet);//获取请求的返回值
            HttpEntity entity = response.getEntity();//得到请求返回值的数据
            result = EntityUtils.toString(entity);//格式化结果
        }catch (Exception e){
            System.out.println("httpClientSend请求异常");
        }
        return result;
    }

    /**
     * 正则表达式匹配第二个括号内的内容,以","分割
     * @param targetStr
     * @param regex
     * @return
     */
    public static String regexStr(String targetStr,String regex){
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile(regex);;
        Matcher matcher = pattern.matcher(targetStr);
        while (matcher.find()){
            result.append(matcher.group(1)).append(",");
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String str = "\n" +
                "                        翠苑东二区二手房,\n" +
                "                        古荡新村西二手房,\n" +
                "                        新世纪花苑二手房,";
        System.out.println(str.replace("\n","").replace(" ",""));
    }
}
