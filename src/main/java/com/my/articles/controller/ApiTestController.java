package com.my.articles.controller;

import com.my.articles.dto.LoginDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ApiTestController {
    @GetMapping("/api")
    public String apiForm() {
        return "/test/api";
    }

    @PostMapping("/apiPostTest")
    public ResponseEntity<String> apiPostTest(@RequestBody LoginDTO login) {
        //RequestBody: json 타입으로 받음 --> object 타입으로 변경해야 함.
        //string 외에 타입으로 보내면 json 타입으로 변환해줌.
        String info = login.getUserid() + login.getPassword();
        return ResponseEntity.status(HttpStatus.OK).body(info);
    }
}
