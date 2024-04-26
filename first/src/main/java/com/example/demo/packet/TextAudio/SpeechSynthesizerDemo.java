package com.example.demo.packet.TextAudio;

public interface SpeechSynthesizerDemo {

    void process(String text);

    byte[] getSynthesizedSpeech();

    void shutdown();
}
