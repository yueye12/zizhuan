package com.example.demo.controller.yue;


import com.example.demo.service.SpeechSynthesizerService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.packet.TextAudio.SpeechSynthesizerDemo;

import java.io.IOException;
import java.io.OutputStream;


@RestController
@CrossOrigin(origins = "*")
public class SpeechSynthesizerController {
    @Autowired
    private SpeechSynthesizerDemo SpeechSynthesizerDemo;

    @Autowired
    private SpeechSynthesizerService speechSynthesizerService;

    @GetMapping({"/synthesize"})
    public byte[] synthesizeSpeech(@RequestParam("text") String text, HttpServletResponse response) throws IOException {

        SpeechSynthesizerDemo.process(text);
        byte[] synthesizedSpeech = SpeechSynthesizerDemo.getSynthesizedSpeech();
        SpeechSynthesizerDemo.shutdown();
        response.reset();
        response.setContentType("audio/wav");
        response.setHeader("Content-Disposition", "attachment; filename=sample.wav");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(synthesizedSpeech);
        outputStream.flush();
        outputStream.close();
        System.out.println("Wav file downloaded successfully!");
        return synthesizedSpeech;
    }

    }


