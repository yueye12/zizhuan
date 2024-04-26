package com.example.demo.controller.yue;

import com.example.demo.packet.JsonResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import com.example.demo.service.SpeechTranscriberService;

@RestController
@CrossOrigin(origins = "*")
public class SpeechTranscriberController {
    @Autowired
    private SpeechTranscriberService speechTranscriberService;

//    @PostMapping(value = "/transcribeAudio")
//    public JsonResult transcribeAudio(@RequestBody byte[] audioBytes, HttpServletResponse response) {
//        try {
//            System.out.println(1);
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Credentials", "true");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
//            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//            response.setCharacterEncoding("UTF-8");
//            String transcription = speechTranscriberService.transcribeAudio(audioBytes);
//
//            ResponseEntity<Object> data = ResponseEntity.ok(transcription);
//            return new JsonResult<>(data,"200","识别成功");
//
//        } catch (IOException e) {
//            return new JsonResult<>(null, "500", "处理音频文件时出错");
//        }
//        }


}
