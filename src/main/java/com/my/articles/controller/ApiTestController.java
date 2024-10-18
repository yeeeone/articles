package com.my.articles.controller;

import com.my.articles.dto.LoginDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "getResponse", method = RequestMethod.POST)
    @ResponseBody
    public LoginDTO getResponse() {
        return new LoginDTO("aaaa", "1111");
    }

    @PostMapping("/apiPostArrayTest")
    @ResponseBody
    public Map<String, String> apiPostArrayTest(@RequestBody List<LoginDTO> dtos) {
        System.out.println(dtos.toString());
        Map<String, String> map = new HashMap<>();
        map.put("userid", dtos.get(0).getUserid());
        map.put("password", dtos.get(0).getPassword());
        return map;
    }

//    @DeleteMapping("/delete")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public String delete() {
        return "Delete Mapping";
    }
}
