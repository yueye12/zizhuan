package com.example.demo.service;



import com.example.demo.packet.Audiotext.SpeechTranscriberDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class SpeechTranscriberService {
    @Autowired
    private SpeechTranscriberDemo SpeechTranscriberDemo;
    public String transcribeAudio(byte[] audioBytes) throws IOException {
        String audioFilePath = saveAudioFile(audioBytes);

        return String.valueOf(SpeechTranscriberDemo.process(audioFilePath));
    }

    private String saveAudioFile(byte[] audioBytes) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        String audioFilePath = tempDir + "/temp_audio.wav";
        FileOutputStream fos = new FileOutputStream(audioFilePath);

        try {
            fos.write(audioBytes);
        } catch (Throwable var8) {
            try {
                fos.close();
            } catch (Throwable var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }

        fos.close();
        return audioFilePath;
    }
}