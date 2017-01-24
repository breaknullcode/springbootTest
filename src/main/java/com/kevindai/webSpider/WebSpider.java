package com.kevindai.webSpider;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevindai on 2017/1/12.
 */
public class WebSpider implements Runnable{
    private String url;
    private CountDownLatch countDownLatch;
    public WebSpider(String url,CountDownLatch countDownLatch){
        this.url = url;
        this.countDownLatch = countDownLatch;
    }

    public  String sendUrl(String url){
        // 定义一个字符串用来存储网页内容
        StringBuffer sb = new StringBuffer();
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                sb.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }

    public  String regexStr(String targetStr,String regex){
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile(regex);;
        Matcher matcher = pattern.matcher(targetStr);
        while (matcher.find()){
            result.append(matcher.group(1)).append(",");
        }

        return result.toString();
    }

    public  String httpClientSendUrl(String url){
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

    public String httpClientPostUrl(String url,int offset,int pageSize) {
        String result = null;
        try {
            //初始化一个httpClient链接
            HttpClient httpClient = HttpClients.createDefault();
            //设置header参数,在浏览器console中查看
            HttpPost post = new HttpPost("https://www.zhihu.com/node/QuestionAnswerListV2");//知乎翻页url,创建post请求
            post.setHeader("Host","www.zhihu.com");
            post.setHeader("Origin","https://www.zhihu.com");
            post.setHeader("Referer",url);
            post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader("Accept-Language","zh-CN,zh;q=0.8");
            //设置分页参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("method","next"));
            //url_token即问题id
            list.add(new BasicNameValuePair("params","{\"url_token\":" + url.substring(url.lastIndexOf("/") + 1) + ",\""+ pageSize +"\":10,\"offset\":"+ offset +"}"));
            StringEntity stringEntity = new UrlEncodedFormEntity(list);
            post.setEntity(stringEntity);//把请求参数设置到请求中

            //获取返回结果
            HttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        }catch (Exception e){
            System.out.println("知乎翻页请求异常");
        }
        return  result.replace("\\","");//知乎翻页结果是经过格式化的字符串,因此做一个替换
    }

    public File downLoadFile(String URL,String filePath){
        File f = new File(filePath);
        URL httpurl = null;
        try {
            httpurl = new URL(URL);
            FileUtils.copyURLToFile(httpurl, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public void run() {
        String url = this.url;

        //用于获取当前问题下总共有多少回答
        String content = httpClientSendUrl(url);//得到当前问题返回数据
        //获取答案总数
        String total = regexStr(content,"id=\"zh-question-answer-num\">([0-9]{1,}.+?).+?");
        total = total.substring(0,total.indexOf(",")).trim();
        //获取问题名,为方便建文件夹
        String title = regexStr(content,"class=\"zm-editable-content\">(.*).+?</span>");
        title = title.substring(0,title.indexOf(",")).trim();
        File dir = new File("D:" + File.separator + title);
        if(!dir.exists()){
            dir.mkdir();
        }


        //循环获取问题下的回答
        int pageSize = 10;
        int count = Integer.valueOf(total)/pageSize + 1;
        String result = null;
        for (int i = 0; i < count ; i++) {
            result = httpClientPostUrl(url,pageSize * i,pageSize);
            //获取图片url
            result = regexStr(result,"</noscript><img.+?src=\"(https.+?)\".+?");
            List<String> zhihuPicUrls = Arrays.asList(result.split(","));
            for (String picUrl : zhihuPicUrls) {
                    //通过url获取图片
                    downLoadFile(picUrl,dir.getPath() + File.separator + picUrl.substring(picUrl.lastIndexOf("/") + 1));
            }
        }
        countDownLatch.countDown();
    }

    public static void main(String[] args) {
        String str = "https://www.zhihu.com/question/38635485";
        System.out.println();
    }
}