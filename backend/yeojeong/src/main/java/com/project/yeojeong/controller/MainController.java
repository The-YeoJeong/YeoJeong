package com.project.yeojeong.controller;

import com.google.gson.JsonObject;
import com.project.yeojeong.dto.TestDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController // 하위에 있는 메소드들은 모두 @ResponseBody 가짐
@Controller
public class MainController {

//    @GetMapping(value = "/test")
//    @ResponseBody // @Controller를 가지고 있지만, 텍스트를 반환하고 싶을 때 사용
//    public TestDto main(){
//        TestDto testDto = new TestDto();
//
//        testDto.setTitle("yeojeong testTitle");
//        testDto.setContent("yeojeong testContent");
//
//        return testDto;
//    }

    @GetMapping(value = "/test")
    @ResponseBody // @Controller를 가지고 있지만, 텍스트를 반환하고 싶을 때 사용
    public String main(){
        JsonObject obj =new JsonObject();

        obj.addProperty("title", "yeojeong testTitle");
        obj.addProperty("content", "yeojeong testContent");

        JsonObject data = new JsonObject();

        data.addProperty("time", "12:00");

        obj.add("data", data);

        return obj.toString();
    }
}