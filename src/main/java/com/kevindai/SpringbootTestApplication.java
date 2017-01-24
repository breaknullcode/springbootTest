package com.kevindai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class SpringbootTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTestApplication.class, args);
	}

    @RequestMapping("/")
    @ResponseBody
    String hello(String name) {
        return "Hello, world!";
    }

    @RequestMapping("/test")
    public String testIndex(){
        System.out.println("------------------start up------------------");
        return "test";
    }
}
