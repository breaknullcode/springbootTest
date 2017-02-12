package com.kevindai;

import com.kevindai.utils.HttpClientsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @RequestMapping("/test")
    public String testIndex(ModelMap model){
        System.out.println("------------------start up------------------");
        String result = HttpClientsUtils.httpClientSendUrl("http://hz.58.com/xihuqu/ershoufang/");
        //获取当前页房子的位置信息
        String location = HttpClientsUtils.regexStr(result,"-.<a.*? class=\"a_xq1\">([\\s\\S]*?)</a>");
        location = location.replace("\r\n","").replace(" ","");
        System.out.println(location);
        model.put("location",location);
        return "test";
    }
}
