package com.kevindai;

import com.kevindai.utils.HttpClientsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
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
        //String location = HttpClientsUtils.regexStr(result,"-.<a.*? class=\"a_xq1\">([\\s\\S]*?)</a>");
        //这里的正则表达式一定要解释一下要不然以后自己都看不懂
        //-.<a.*? class="a_xq1">([\s\S]*?[^\n])</a> 这里匹配-.<a.*? class="a_xq1">开头的</a>结束的所有a标签 这里指的是二手房坐在位置信息
        //[\s\S]* 这里匹配后面的所有字符,包括换行符
        //class='pri'>(\d{0,}) 匹配以class='pri'>开头后面跟着数字的字符 这里指二手房总金额
        //[\s\S]* 这里匹配后面的所有字符,包括换行符
        //(\d{0,})元/㎡ 匹配数字开头,元/㎡结尾的所有字符 这里指二手房单价
        //[\s\S]* 这里匹配后面的所有字符,包括换行符
        //class="showroom">[\s\S]*(.*?)</span> 匹配以class="showroom">开头,以</span>结束的span标签的字符 这里指二手房格局
        List<HouseMsgPojo> houseMsgPojos = regexStr(result,"-.<a.*? class=\"a_xq1\">([\\s\\S]*?[^\\n])</a>[\\s\\S]*?class='pri'>(\\d{0,})[\\s\\S]*?(\\d{0,}元/㎡)[\\s\\S]*?class=\"showroom\">[\\s\\S]*?(.+?)</span>([\\s\\S]*?)<br/>");
        model.put("houseMsgs",houseMsgPojos);
        return "test";
    }

    public List<HouseMsgPojo> regexStr(String targetStr,String regex){
        Pattern pattern = Pattern.compile(regex);;
        Matcher matcher = pattern.matcher(targetStr);
        List<HouseMsgPojo> houseMsgPojos = new ArrayList<>();
        HouseMsgPojo houseMsg;
        while (matcher.find()){
            houseMsg = new HouseMsgPojo();
            houseMsg.setLocation(matcher.group(1).replace("\r\n","").replace(" ",""));
            houseMsg.setTotalMoney(matcher.group(2).replace("\r\n","").replace(" ",""));
            houseMsg.setUnitPrice(matcher.group(3).replace("\r\n","").replace(" ",""));
            houseMsg.setLayout(matcher.group(4).replace("\r\n","").replace(" ",""));
            houseMsg.setSize(matcher.group(5).replace("\r\n","").replace(" ",""));
            houseMsgPojos.add(houseMsg);
        }

        return houseMsgPojos;
    }
}
