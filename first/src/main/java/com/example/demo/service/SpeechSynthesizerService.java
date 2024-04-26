package com.example.demo.service;


import com.example.demo.packet.TextAudio.SpeechSynthesizerDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpeechSynthesizerService {
    @Autowired
    private SpeechSynthesizerDemo SpeechSynthesizerDemo;
    public byte[] synthesizeSpeech(String text) throws IOException {

        SpeechSynthesizerDemo.process(text);
        byte[] synthesizedSpeech = SpeechSynthesizerDemo.getSynthesizedSpeech();
        SpeechSynthesizerDemo.shutdown();
        return synthesizedSpeech;
    }
}