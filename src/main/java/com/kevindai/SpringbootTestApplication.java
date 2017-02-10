package com.kevindai;

import com.kevindai.utils.HttpClientsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@SpringBootApplication
public class SpringbootTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTestApplication.class, args);
	}

    @RequestMapping("/")
    @ResponseBody
    String hello(String name) {
        return "成功!";
    }

    public  String regexStr(String targetStr,String regex){
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile(regex);;
        Matcher matcher = pattern.matcher(targetStr);
        while (matcher.find()){
            result.append(matcher.group(2)).append(",");
        }

        return result.toString();
    }

    @RequestMapping("/test")
    public String testIndex(){
        System.out.println("------------------start up------------------");
        String result = HttpClientsUtils.httpClientSendUrl("http://hz.58.com/xihuqu/ershoufang/");
        //获取答案总数
        String total = regexStr(result,"class=\"t\"  infoid=\"1486656005000\">[\\s\\S]*(.+)</a>");
        System.out.println(total);
        return "test";
    }
}
